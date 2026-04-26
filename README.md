# ThePrice Landing Page

Tester registration landing page for **ThePrice**, a cost-of-living tracking application.

## Features

- Tester registration form (name, email, platform selection: Android/iOS/Both)
- Internationalization support (English and Portuguese BR)
- Email validation with regex
- API integration for signup submissions
- Dark-themed responsive UI with custom color palette
- Loading states and success/error feedback

## Tech Stack

- **Kobweb 0.24.0** - Kotlin Multiplatform web framework using Compose HTML
- **Kotlin 2.3.10**
- Frontend-only application (static export)

## Development

Start the development server:

```bash
cd site
kobweb run
```

Open [http://localhost:8080](http://localhost:8080) in your browser. Press `Q` in the terminal to stop the server.

## Production

Export for static hosting:

```bash
kobweb export --layout static
```

Test the production build locally:

```bash
kobweb run --env prod --layout static
```

## Project Structure

```
site/src/jsMain/kotlin/io/noartcode/theprice/page/
├── pages/              # Page components (@Page)
├── components/         # Reusable UI components
│   ├── sections/       # NavHeader, Footer
│   └── widget/         # LoadingButton, Icons, etc.
├── i18n/               # Language support (English/Portuguese BR)
├── api/                # API client and DTOs
├── AppEntry.kt         # Root @App composable
├── AppStyles.kt        # Global CSS styles and animations
└── SiteTheme.kt        # Color palette and theme config
```

## Resources

- See [CLAUDE.md](CLAUDE.md) for detailed development guidelines
- [Kobweb Documentation](https://github.com/varabyte/kobweb)
