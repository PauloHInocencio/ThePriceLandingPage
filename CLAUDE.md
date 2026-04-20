# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Development Commands

**Start development server:**
```bash
cd site
kobweb run
```
Open http://localhost:8080 to view the site. Press `Q` in terminal to stop the server.

**Export for production (static hosting):**
```bash
kobweb export --layout static
```

**Test production build locally:**
```bash
kobweb run --env prod --layout static
```

## Architecture Overview

### Framework: Kobweb 0.24.0

This is a Kotlin Multiplatform web application using Kobweb (a Compose HTML framework) targeting JavaScript. The project is frontend-only but supports full-stack with `includeServer = true`.

### Key Architectural Concepts

**Page Discovery:**
- Pages are defined with `@Page` annotation on composable functions
- File location in `pages/` directory determines routing (e.g., `Index.kt` → `/`)
- No manual routing configuration needed

**App Initialization:**
- `AppEntry.kt` contains the root `@App` composable called for all pages
- `@InitSilk` functions in `AppStyles.kt` configure global styles and theme
- Framework automatically discovers and calls these annotated functions

**Styling System:**
- Global styles defined as `CssStyle` objects in `AppStyles.kt`
- Variants created with `ButtonStyle.addVariant()` pattern
- Inline styling via Modifier chains (e.g., `.color()`, `.padding()`, `.fontSize()`)
- Responsive design using `Breakpoint.MD` with `.displayIfAtLeast()` and `.displayUntil()`

**Component Organization:**
```
components/
├── sections/     # Page-level sections (NavHeader, Footer)
└── widget/       # Reusable widgets (LoadingButton)
```

**Internationalization Pattern:**
- Sealed class `Strings` with language implementations (`English`, `PortugueseBR`)
- `Language` enum tracks current language
- `LanguageStorage` persists to browser localStorage with key `page:language`
- Extension function `Language.strings()` retrieves translations

**Theme System:**
- `TesterColors` object in `SiteTheme.kt` defines brand colors
- Light/dark mode via `ColorMode` (Silk framework feature)
- Theme preference persisted to localStorage automatically with key `page:colorMode`

**State Management:**
- Local component state using `remember` and `mutableStateOf`
- No global state management library
- Parent composables hold state and pass callbacks to children

## Project Structure

```
site/src/jsMain/kotlin/io/noartcode/theprice/page/
├── AppEntry.kt              # Root composable (@App)
├── AppStyles.kt             # Global CSS styles and keyframes
├── SiteTheme.kt            # Color palette and theme config
├── components/
│   ├── sections/           # NavHeader, Footer
│   └── widget/            # LoadingButton
├── i18n/                  # Language.kt, Strings.kt, LanguageStorage.kt
└── pages/
    └── Index.kt           # Homepage (@Page)
```

**Static assets:** `site/src/jsMain/resources/public/`

**Build output:**
- Dev: `build/kotlin-webpack/js/developmentExecutable/page.js`
- Prod: `build/kotlin-webpack/js/productionExecutable/page.js`

## Key Dependencies (gradle/libs.versions.toml)

- Kobweb: 0.24.0
- Kotlin: 2.3.10
- Compose HTML: 1.10.0
- Silk UI: Bundled with Kobweb (buttons, icons, layout)
- Font Awesome icons: Via `silk-icons-fa`

## Important Patterns

### Adding CSS Animations

Define keyframes in `AppStyles.kt`:
```kotlin
val SpinKeyframes = Keyframes {
    from { Modifier.rotate(0.deg) }
    to { Modifier.rotate(360.deg) }
}
```

Apply with:
```kotlin
.animation(
    SpinKeyframes.toAnimation(
        duration = 1.s,
        iterationCount = AnimationIterationCount.Infinite,
        timingFunction = AnimationTimingFunction.Linear
    )
)
```

### Creating Reusable Components

Place in `components/widget/` or `components/sections/`:
```kotlin
@Composable
fun MyComponent(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        // Component content
    }
}
```

### Adding New Pages

Create in `pages/` directory with `@Page` annotation:
```kotlin
@Page
@Composable
fun AboutPage() {
    // Page content
}
```
File `pages/About.kt` creates route `/about`

### Form Validation

Current pattern uses local validation with regex (see email validation in `Index.kt`):
```kotlin
val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
val isValidEmail = email.matches(emailRegex)
```

## Current Application State

**ThePrice Landing Page**: Tester registration form for a cost-of-living tracking app.

**Implemented:**
- Name, email, platform selection form
- Email validation with regex
- Language switcher (English/Portuguese BR)
- Platform selector (Android/iOS/Both)
- Dark-themed UI with custom color palette

**Not Yet Implemented:**
- Backend integration (form submission onClick is empty)
- LoadingButton.kt has empty implementation

## Configuration Files

- `.kobweb/conf.yaml` - Server port (8080), build paths, site title
- `gradle/libs.versions.toml` - Centralized dependency versions
- `site/build.gradle.kts` - Application configuration and plugin setup