import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.util.Properties
import java.io.File

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildkonfig)
}

group = "io.noartcode.theprice.page"
version = "1.0-SNAPSHOT"

// Read local.properties from project root
val localPropertiesFile = rootProject.file("local.properties")

if (!localPropertiesFile.exists()) {
    throw GradleException(
        """
        Missing local.properties file!

        Create local.properties in project root with:
        API_BASE_URL=https://your-api-url.com

        See local.properties.example for template.
        """.trimIndent()
    )
}

// Parse properties file manually
val localProperties = localPropertiesFile.readLines()
    .filter { it.isNotBlank() && !it.trim().startsWith("#") }
    .associate { line ->
        val parts = line.split("=", limit = 2)
        if (parts.size == 2) {
            parts[0].trim() to parts[1].trim()
        } else {
            "" to ""
        }
    }

val apiBaseUrl = localProperties["API_BASE_URL"]
    ?: throw GradleException("API_BASE_URL not found in local.properties")

if (apiBaseUrl.isBlank()) {
    throw GradleException("API_BASE_URL in local.properties cannot be blank")
}

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
        }
    }
}

kotlin {
    // This example is frontend only. However, for a fullstack app, you can uncomment the includeServer parameter
    // and the `jvmMain` source set below.
    configAsKobwebApplication("page" /*, includeServer = true*/)

    sourceSets {
//        commonMain.dependencies {
//          // Add shared dependencies between JS and JVM here if building a fullstack app
//        }

        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            // This default template uses built-in SVG icons, but what's available is limited.
            // Uncomment the following if you want access to a large set of font-awesome icons:
            implementation(libs.silk.icons.fa)

            // Kotlinx Serialization for JSON parsing
            implementation(libs.kotlinx.serialization.json)

        }

        // Uncomment the following if you pass `includeServer = true` into the `configAsKobwebApplication` call.
//        jvmMain.dependencies {
//            compileOnly(libs.kobweb.api) // Provided by Kobweb backend at runtime
//        }
    }
}

buildkonfig {
    packageName = "io.noartcode.theprice.page"

    defaultConfigs {
        buildConfigField(STRING, "API_BASE_URL", apiBaseUrl)
    }
}
