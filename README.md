# KMPStarter

Kickstart your **Kotlin Multiplatform (KMP)** development with KMPStarter. Designed by [Hridoy Chandra Das](https://github.com/ihridoydas), it provides a robust starting point for Android, iOS, Desktop, Web (Wasm), Backend, TV, Wear OS, Automotive, and XR applications.

## Why KMPStarter?

- **Multiplatform by Default:** Shared logic and UI (Compose Multiplatform) for Android, iOS, Desktop, and Web.
- **Universal Adaptive UI:** Single core UI that automatically adapts across Phones, Tablets, Foldables, Desktop, Web, TV, Wear OS, Automotive, and XR using window size constraints and `FormFactorMode`.
- **Ktor Backend & Swagger UI:** Production-ready backend server (`:apps:serverApp`) with REST API and interactive Swagger UI documentation at `/docs`.
- **Smart Setup & Form-Factor Toggles:** Custom script to rename project details and toggle libraries & form factors (`useAndroidTV`, `useWearOS`, `useAndroidAutomotive`, `useAndroidXR`) with intelligent dependency and source file cleanup.
- **Multi-Language Localization:** Built-in support for English, Japanese, Bengali, and System default.

## Architecture

This project follows **Clean Architecture** principles and a **Modular Multiplatform Layering** strategy:

```text
├── :apps               # Platform-specific entry points (androidApp, desktopApp, webApp, serverApp)
├── iosApp              # iOS Swift application wrapper (Xcode project & Compose Multiplatform)
├── :feature            # Isolated feature modules (auth, home, profile, settings)
├── :navigation         # Type-safe Navigation3 API and implementation
└── :core               # Foundation modules
    ├── :core:designsystem # Material 3 theme, components, and adaptive form-factor UIs
    ├── :core:common       # Utilities, LanguageManager, and constants
    ├── :core:model        # Shared domain models & DTOs
    ├── :core:network      # Ktor HTTP client & API services
    ├── :core:database     # Room KMP database & DAOs
    ├── :core:datastore    # Type-safe DataStore preferences
    ├── :core:platform     # Platform-specific expect/actual abstractions
    └── :core:testing      # Test harnesses & mocks
```

- **Dependency Injection:** Powered by [Koin](https://insert-koin.io/).
- **Navigation:** Powered by Jetpack [Navigation3](https://developer.android.com/guide/navigation).
- **Local Storage & Database:** Powered by Room KMP and DataStore.
- **Networking:** Powered by Ktor Client & Server.

For deep architectural guidelines and large-team configuration, read the full [Production Architecture Guide](documentation/architecture/KmpTemplate_Production_Architecture.md).

## Getting Started

1. Click **"Use this template"** to create a repository under your account.
2. Open `buildscripts/setup.gradle` and configure your project details and form factors:
    ```groovy
    def renameConfig = [
        newTemplateName          : "MyAwesomeApp",    // Your project name
        newTemplateAppId         : "com.example.app", // Your package ID
        newMaterialThemeName     : "AppTheme",        // Your Material Theme name

        // Toggle libraries & form factors
        useKoin                  : true,
        useKtor                  : true,
        useRoomKmp               : true,
        useComposeMultiplatform  : true,
        useAndroidXR             : true,
        useAndroidTV             : true,
        useWearOS                : true,
        useAndroidAutomotive     : true,
    ]
    ```
3. Run the setup command in your terminal:
    ```bash
    ./gradlew renameTemplate
    ```
4. **Restart Android Studio**, re-sync Gradle, and you are ready to build!

- Japanese [Readme here](README_jp.md) 🇯🇵.
- Bangla [Readme here](README_bd.md) 🇧🇩.
- Hindi [Readme here](README_in.md) 🇮🇳.

## Running the Ktor Backend & Swagger UI

Start the backend server:
```bash
./gradlew :apps:serverApp:run
```
Open **[http://localhost:8080/docs](http://localhost:8080/docs)** in your browser for the interactive Swagger UI API documentation.

## Generating API Documentation (Dokka)

Generate unified multi-module API documentation for all shared libraries and core modules:
```bash
./gradlew dokkaGenerate
```
View the generated documentation site in **`dokkaDocs/index.html`**.
