# Server, Adaptive UI & Dokka Documentation

This guide provides comprehensive developer instructions for the Ktor Backend Server, Swagger UI, Universal Dynamic Adaptive UI, Form-Factor Toggles, Multi-Language Localization, and Dokka Documentation generation.

---

## 1. Generating API Documentation with Dokka

Dokka generates rich, unified HTML API documentation from your Kotlin source code and KDoc comments.

### Commands
- **Generate unified documentation for all modules:**
  ```bash
  ./gradlew dokkaGenerate
  ```

### Output Location
Dokka automatically generates a unified multi-module documentation site in the project root:
- **`dokkaDocs/index.html`** (Unified Master Index listing all modules, search bar, and interactive theme switcher)

---

## 2. Running the Ktor Backend Server & Swagger UI

The project includes a production-ready Ktor REST API backend server located in `:apps:serverApp`.

### Starting the Server
```bash
./gradlew :apps:serverApp:run
```

### Interactive Swagger UI
Once running, open your browser at:
- **[http://localhost:8080/docs](http://localhost:8080/docs)** (Interactive Swagger UI)
- **[http://localhost:8080/openapi.json](http://localhost:8080/openapi.json)** (OpenAPI 3.0 JSON specification)
- **[http://localhost:8080/health](http://localhost:8080/health)** (Server health check)

---

## 3. Universal Dynamic Adaptive UI & Form Factors

The UI layer (`:core:designsystem`) is built with Compose Multiplatform to automatically adapt to any screen size or form factor:

- **Compact (`< 600.dp`)**: Phones and Wear OS watches.
- **Medium (`600.dp - 840.dp`)**: Tablets and Foldables.
- **Expanded (`> 840.dp`)**: Desktops, Widescreens, Android TV, and Android Automotive dashboards.
- **XR Spatial Computing**: Automatically wraps content in a 3D movable/resizable `SpatialPanel` via `SpatialWrapper`.

### Interactive Form-Factor Switcher
Both `AdaptiveMainScreen` and `ViewScreen` include a built-in form-factor preview switcher (`FormFactorMode`), allowing developers to test **TV Mode**, **Wear OS Mode**, **Automotive Mode**, and **XR Spatial Mode** instantly on any device.

---

## 4. Smart Setup & Form-Factor Toggles

In `buildscripts/setup.gradle`, you can customize your app ID, name, and toggle specific form-factor support:

```groovy
def renameConfig = [
    newTemplateName          : "MyAwesomeApp",
    newTemplateAppId         : "com.example.app",
    newMaterialThemeName     : "AppTheme",

    // KMP Libraries & Form Factors
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

When any form-factor toggle is set to `false`, running `./gradlew renameTemplate` automatically removes both its build dependencies and related source files (`TvScreen.kt`, `WearOsScreen.kt`, `AutomotiveScreen.kt`, `XrScreen.kt`).

---

## 5. Multi-Language Localization

Built-in localization is provided for:
- 🇬🇧 English (`en`)
- 🇯🇵 Japanese (`ja`)
- 🇧🇩 Bengali (`bn`)
- ⚙️ System Default

Managed dynamically via `LanguageManager` and `LanguageDataStore`, updating all screens instantly in real-time.
