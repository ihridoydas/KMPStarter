# KMPStarter

Kickstart your **Kotlin Multiplatform (KMP)** development with KMPStarter. Designed by [Hridoy Chandra Das](https://github.com/ihridoydas), it provides a robust starting point for Android, iOS, Desktop, Web (Wasm), Backend, TV, Wear OS, Automotive, and XR applications.

## Why KMPStarter?

- **Multiplatform by Default:** Shared logic and UI (Compose Multiplatform) for Android, iOS, Desktop, and Web.
- **Universal Adaptive UI:** Single core UI that automatically adapts across Phones, Tablets, Foldables, Desktop, Web, TV, Wear OS, Automotive, and XR using window size constraints and `FormFactorMode`.
- **Ktor Backend & Swagger UI:** Production-ready backend server (`:apps:serverApp`) with REST API and interactive Swagger UI documentation at `/docs`.
- **Smart Setup & Form-Factor Toggles:** Custom script to rename project details and toggle libraries & form factors (`useAndroidTV`, `useWearOS`, `useAndroidAutomotive`, `useAndroidXR`) with intelligent dependency and source file cleanup.
- **Multi-Language Localization:** Built-in support for English, Japanese, Bengali, and System default.

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

## Project Structure

- `:apps:androidApp`: Android application module.
- `:apps:desktopApp`: Desktop application module.
- `:apps:webApp`: Web application module.
- `:apps:serverApp`: Ktor backend server with Swagger UI.
- `:core:designsystem`: Shared Material 3 design system, adaptive UI, and form-factor screens.
- `:core:common`: Shared utilities and language manager.
- `:core:model` & `:core:network`: Shared DTOs, models, and Ktor client.
- `:navigation`: Shared navigation configuration.
