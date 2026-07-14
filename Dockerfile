# Stage 1: Build - Compile Kotlin to JavaScript and export static site
#---------------------------------------------------------------------
FROM eclipse-temurin:21-jdk-jammy AS builder

# Install Kobweb CLI from GitHub releases
# Unlike Node.js (npm) or Python (pip), Kobweb doesn't hava a package maneger installer. We must:
# 1. Manually download the CLI from Github releases
# 2. Extract it to a standard location
# 3. Add it to PATH via symlink (so now kobweb command works from anywhere)
RUN apt-get update && apt-get install -y curl unzip && \
    curl -L https://github.com/varabyte/kobweb-cli/releases/download/v0.9.21/kobweb-0.9.21.zip -o kobweb.zip && \
    unzip kobweb.zip -d /opt && \
    ln -s /opt/kobweb-0.9.21/bin/kobweb /usr/local/bin/kobweb && \
    rm kobweb.zip && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# API_BASE_URL is passed as a build arg (see docker-compose.yml `args`) rather than
# copied from local.properties, since that file is gitignored and unavailable to CI/Dokploy builds.
ARG API_BASE_URL
RUN test -n "$API_BASE_URL" || (echo "ERROR: API_BASE_URL build arg is required" && exit 1)

# Copy gradle wrapper and dependency files first (for better layer caching latter)
# FYI: Docker builds images in layers. Each instruction (COPY, RUN, etc.) creates a layer that Docker caches.
COPY gradle gradle
COPY gradlew gradlew.bat settings.gradle.kts gradle.properties ./
RUN echo "API_BASE_URL=$API_BASE_URL" > local.properties

# Copy site directory with build configuration
COPY site/build.gradle.kts site/
COPY site/.kobweb site/.kobweb

# Download dependencies (cached layer if dependencies don't change)
RUN ./gradlew dependencies --no-daemon

# Copy remaining source code
COPY site/src site/src

# Build the project
RUN ./gradlew clean build --no-daemon

# Export static site
WORKDIR /app/site
RUN kobweb export --layout static --notty

# Stage 2: Runtime - Serve with nginx
#---------------------------------------------------------------------
FROM nginx:alpine

# Copy HTML and assets from public/ to nginx root
COPY --from=builder /app/site/build/dist/js/productionExecutable/public/ /usr/share/nginx/html/

# Copy page.js to nginx root (so /page.js path works)
COPY --from=builder /app/site/build/dist/js/productionExecutable/page.js /usr/share/nginx/html/page.js

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Add health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD wget --quiet --tries=1 --spider http://127.0.0.1:80/ || exit 1

# Expose port 80
EXPOSE 80

# Labels for metadata
LABEL version="1.0"
LABEL description="ThePrice Lading Page - Tester Registration"
LABEL maintainer="NoArtCode"

# Start nginx
CMD ["nginx", "-g", "daemon off;"]


