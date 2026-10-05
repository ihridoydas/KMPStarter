# KMPStarter — Production-Oriented KMP Architecture

## 1. Goals

This template is designed for:

- Kotlin Multiplatform (KMP)
- Compose Multiplatform (CMP)
- Android
- iOS
- Desktop/JVM
- Web/Wasm
- Kotlin/Ktor Server
- Multi-module architecture
- Large-team development
- Easy onboarding and maintenance
- Strong module boundaries
- Parallel feature development
- Good Gradle/build performance
- Automated architecture and code-quality enforcement

The guiding principle is:

> **Simple by default, strict boundaries, feature ownership, platform independence, and automation instead of relying only on developer discipline.**

---

## 2. Recommended Project Structure

```text
KMPStarter/
│
├── apps/
│   │
│   ├── androidApp/                    # Android application entry point
│   ├── iosApp/                        # iOS / Xcode application entry point
│   ├── desktopApp/                    # Desktop / JVM application entry point
│   ├── webApp/                        # Web / Wasm application entry point
│   └── serverApp/                     # Ktor Server application entry point
│
├── core/
│   │
│   ├── common/                        # Truly common utilities
│   ├── model/                         # Shared models / value objects
│   ├── network/                       # Ktor client + networking infrastructure
│   ├── database/                      # Room KMP / SQLite
│   ├── datastore/                     # DataStore / preferences
│   ├── platform/                      # Platform abstractions
│   ├── designsystem/                  # Compose Multiplatform design system
│   └── testing/                       # Shared test utilities / fakes
│
├── feature/
│   │
│   ├── auth/
│   │   ├── data/
│   │   ├── domain/
│   │   └── presentation/
│   │
│   ├── home/
│   │   ├── data/
│   │   ├── domain/
│   │   └── presentation/
│   │
│   ├── profile/
│   │   ├── data/
│   │   ├── domain/
│   │   └── presentation/
│   │
│   └── settings/
│       ├── data/
│       ├── domain/
│       └── presentation/
│
├── navigation/
│   ├── api/
│   └── impl/
│
├── server/
│   ├── core/
│   │   ├── config/
│   │   ├── database/
│   │   ├── security/
│   │   └── logging/
│   │
│   └── feature/
│       ├── auth/
│       ├── user/
│       └── health/
│
├── build-logic/
│   └── convention/
│       ├── kmp/
│       ├── compose/
│       ├── android/
│       ├── desktop/
│       ├── web/
│       ├── server/
│       └── testing/
│
├── buildscripts/
│   └── setup.gradle                   # Template rename/setup
│
├── gradle/
│   └── libs.versions.toml
│
├── docs/
│   ├── architecture/
│   ├── development/
│   ├── contributing/
│   └── decisions/
│
├── config/
│   └── detekt/
│
├── .github/
│   ├── workflows/
│   └── CODEOWNERS
│
└── settings.gradle.kts
```

---

## 3. Architecture Principles

### 3.1 Simple dependency direction

The primary dependency direction is:

```text
Apps
  ↓
Features
  ↓
Core
```

Never allow:

```text
Core → Feature
Core → App
Feature → another Feature implementation
Domain → UI
UI → Database
UI → Network
```

Allowed:

```text
UI → ViewModel
ViewModel → UseCase
UseCase → Repository
Repository → DataSource
Feature → Core
Feature A → Feature B API
App → Feature
```

---

## 4. Feature-First Architecture

Each business feature owns its implementation.

Example:

```text
feature/auth/

├── data/
│   ├── remote/
│   ├── local/
│   ├── repository/
│   └── mapper/
│
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
│
└── presentation/
    ├── AuthScreen.kt
    ├── AuthViewModel.kt
    ├── AuthUiState.kt
    └── AuthUiEvent.kt
```

### Presentation

Responsible for:

- Compose UI
- ViewModel
- UI state
- UI events
- UI-specific mapping

### Domain

Responsible for:

- Business rules
- Use cases
- Repository interfaces
- Feature domain models

### Data

Responsible for:

- Repository implementations
- API calls
- Local database
- DataStore
- DTOs
- Mappers
- Data sources

---

## 5. Feature Ownership

A large team can divide ownership by feature:

```text
Platform Team
    └── core/*

Design System Team
    └── core/designsystem

Auth Team
    └── feature/auth

Home Team
    └── feature/home

Profile Team
    └── feature/profile

Backend Team
    └── server/

Architecture / Developer Productivity Team
    ├── build-logic/
    ├── CI
    └── architecture rules
```

This reduces merge conflicts and allows teams to work in parallel.

---

## 6. Core Modules

### 6.1 `core/common`

Only genuinely generic functionality:

```text
Result
Error
Coroutine utilities
Extensions
Date/time helpers
Validation helpers
Common utilities
```

Do not put feature business logic here.

---

### 6.2 `core/model`

Models that are genuinely shared across multiple modules:

```text
User
UserId
Pagination
Page
CommonError
```

Do not turn this into a dumping ground for every model.

Feature-specific models should remain inside the feature.

---

### 6.3 `core/network`

Responsibilities:

```text
Ktor HttpClient
HTTP configuration
Serialization
Authentication interceptor
Network error handling
Request/response infrastructure
```

Recommended flow:

```text
ViewModel
    ↓
UseCase
    ↓
Repository
    ↓
RemoteDataSource / API
    ↓
Ktor Client
```

Never:

```text
Compose UI → Ktor
ViewModel → HttpClient
```

---

### 6.4 `core/database`

Responsibilities:

```text
Room KMP
Database
DAO
Entities
Migrations
Database configuration
```

Features should access the database through repositories/data sources rather than directly from UI.

---

### 6.5 `core/datastore`

Use DataStore for small persistent preferences:

```text
Theme
Language
Application settings
User preferences
Small session-related values
```

Do not use DataStore as a replacement for a relational database.

---

### 6.6 `core/platform`

Platform-specific functionality should be isolated here.

Example:

```text
core/platform/

src/
├── commonMain/
├── androidMain/
├── iosMain/
├── jvmMain/
└── wasmJsMain/
```

Use interfaces first:

```kotlin
interface Platform {
    val name: String
}
```

Use `expect/actual` only when it is genuinely appropriate for platform APIs.

---

### 6.7 `core/designsystem`

The shared Compose Multiplatform design system:

```text
core/designsystem/

├── theme/
├── color/
├── typography/
├── shape/
├── component/
└── preview/
```

Example components:

```text
AppButton
AppTextField
AppTopBar
AppDialog
AppCard
AppLoading
```

The design system should provide consistency across Android, iOS, Desktop, and Web.

---

### 6.8 `core/testing`

Shared testing infrastructure:

```text
Fake repositories
Test data
Coroutine test utilities
Fixtures
Common test helpers
```

This avoids every feature creating its own duplicate testing utilities.

---

# 7. Platform Architecture

## Android

```text
Android
├── Kotlin
├── Compose Multiplatform
├── Ktor Client
├── Room KMP
├── DataStore
└── Koin
```

Android-specific APIs remain in Android source sets.

---

## iOS

```text
iOS
├── Kotlin
├── Compose Multiplatform
├── Ktor Client
├── Room KMP
├── Koin
└── Swift/Xcode integration
```

Keep Apple-specific APIs inside iOS-specific source sets or platform modules.

---

## Desktop

```text
Desktop
├── Kotlin/JVM
├── Compose Desktop
├── Ktor Client
├── Local persistence where appropriate
└── Koin
```

---

## Web

```text
Web
├── Kotlin/Wasm
├── Compose Multiplatform
├── Ktor Client
└── Browser/platform APIs
```

Web-specific browser APIs should not leak into common business logic.

---

## Server

```text
Server
├── Kotlin/JVM
├── Ktor Server
├── Database
├── Authentication
├── Serialization
└── Logging
```

The server is a separate application boundary.

---

# 8. Server Architecture

Recommended structure:

```text
serverApp/
└── server/

    ├── core/
    │   ├── config/
    │   ├── database/
    │   ├── security/
    │   └── logging/
    │
    └── feature/
        ├── auth/
        │   ├── route/
        │   ├── service/
        │   ├── repository/
        │   └── model/
        │
        ├── user/
        │   ├── route/
        │   ├── service/
        │   ├── repository/
        │   └── model/
        │
        └── health/
            └── route/
```

The server should never depend on:

```text
core/designsystem
client presentation
Android UI
iOS UI
client navigation
```

---

# 9. Client and Server Sharing

Share only what provides real value.

Good candidates:

```text
Shared models
Shared serialization models
Validation rules
Business rules where appropriate
Result/Error abstractions
API contracts
Date/time logic
```

Keep platform/application-specific code separate:

```text
Android APIs
iOS APIs
Desktop APIs
Browser APIs
Server routing
Server database
Server authentication
Server configuration
```

The goal is not maximum code sharing.

The goal is:

> **Maximum useful sharing with minimum coupling.**

---

# 10. Navigation

Navigation is isolated from feature implementation:

```text
navigation/
├── api/
│   ├── AppRoute.kt
│   └── Navigator.kt
│
└── impl/
    ├── AppNavHost.kt
    └── NavigationImpl.kt
```

Features depend on navigation contracts rather than the internal navigation implementation.

Only create additional navigation modules when the project actually needs them.

---

# 11. Build Logic

Large multi-module projects should not duplicate Gradle configuration.

Use convention plugins:

```text
build-logic/
└── convention/

    ├── kmp/
    ├── compose/
    ├── android/
    ├── desktop/
    ├── web/
    ├── server/
    └── testing/
```

Examples:

```text
kmp-library
kmp-compose
android-application
android-library
desktop-application
web-application
server-application
testing
```

This keeps module build files small and consistent.

---

# 12. Version Catalog

Use:

```text
gradle/libs.versions.toml
```

Centralize:

```text
Kotlin
Compose
Ktor
Koin
Room
DataStore
Serialization
Coroutines
Testing libraries
Gradle plugins
```

All teams should consume the approved versions.

---

# 13. Multi-Module Rules

### Good

```text
app
 ↓
feature
 ↓
core
```

### Good

```text
feature/auth
 ↓
core/network
```

### Good

```text
feature/home
 ↓
feature/auth/api
```

when Auth exposes a genuinely required public contract.

### Bad

```text
feature/home
 ↓
feature/auth/internal/RepositoryImpl
```

### Bad

```text
core/network
 ↓
feature/auth
```

### Bad

```text
presentation
 ↓
Room DAO
```

---

# 14. Avoid Over-Modularization

Multi-module does not mean creating hundreds of modules.

Do not automatically create:

```text
feature/auth/api
feature/auth/impl
feature/auth/domain
feature/auth/data
feature/auth/presentation
feature/auth/testing
```

as separate Gradle modules.

Start with:

```text
feature/auth
```

containing:

```text
data/
domain/
presentation/
```

Split a feature into additional Gradle modules only when there is a clear reason:

- Team ownership
- Build performance
- Dependency isolation
- Reuse
- API stability
- Independent release/versioning requirements

This keeps the project easy to understand.

---

# 15. Testing Strategy

Use a test pyramid:

```text
                 UI Tests
                    ▲
                   / \
                  /   \
           Integration
                /       \
               /         \
        Unit / Domain / Repository
```

Most tests should be fast unit tests.

Recommended coverage:

```text
Domain
 ├── UseCase tests
 └── Business rule tests

Data
 ├── Repository tests
 └── Mapper tests
 └── Data source tests

Presentation
 ├── ViewModel tests
 └── UI tests where valuable

Platform
 └── Platform-specific tests
```

---

# 16. Large-Team CI

Every Pull Request should run automated checks:

```text
Pull Request
     │
     ├── Compile
     ├── Unit Tests
     ├── KMP Tests
     ├── Detekt
     ├── Ktlint
     ├── Spotless
     ├── Architecture Checks
     ├── API Compatibility
     └── Coverage
              │
              ▼
            Merge
```

Architecture rules should be enforced by CI rather than existing only in documentation.

---

# 17. CODEOWNERS

Use:

```text
.github/CODEOWNERS
```

Example:

```text
/core/                  @platform-team
/core/designsystem/     @design-system-team
/core/network/          @platform-team
/feature/auth/          @auth-team
/feature/home/          @home-team
/server/                @backend-team
/build-logic/           @architecture-team
```

This allows large teams to have clear ownership.

---

# 18. Documentation

Keep documentation focused:

```text
docs/
├── architecture/
│   ├── overview.md
│   ├── module-boundaries.md
│   └── dependency-rules.md
│
├── development/
│   ├── setup.md
│   └── testing.md
│
└── contributing/
    └── pull-request.md
```

A new developer should be able to understand the basic architecture quickly.

---

# 19. Architecture Overview

```text
                         KMP STARTER
                              │
          ┌───────────────────┼───────────────────┐
          │                   │                   │
       CLIENTS              SHARED             SERVER
          │                   │                   │
   ┌──────┼──────┐      ┌─────┼─────┐      ┌─────┴─────┐
   │      │      │      │     │     │      │           │
Android  iOS  Desktop  Model Core Network  Ktor      Database
   │      │      │      │     │     │      │
   └──────┼──────┘      └─────┼─────┘      └───────────┘
          │                   │
          └─────────┬─────────┘
                    │
             Compose Multiplatform
                    │
              Feature Modules
                    │
        ┌───────────┼───────────┐
        │           │           │
       Auth        Home       Profile
        │           │           │
        └───────────┼───────────┘
                    │
             Strict Boundaries
                    │
             Automated CI/CD
```

---

# 20. Recommended Technology Stack

| Area | Technology |
|---|---|
| Language | Kotlin |
| Multiplatform | Kotlin Multiplatform |
| UI | Compose Multiplatform |
| Android | Kotlin / Android |
| iOS | Kotlin / Swift / Xcode |
| Desktop | Kotlin/JVM + Compose |
| Web | Kotlin/Wasm + Compose |
| Server | Kotlin + Ktor |
| DI | Koin |
| Network | Ktor Client |
| Serialization | Kotlinx Serialization |
| Database | Room KMP |
| Preferences | DataStore |
| Async | Kotlin Coroutines |
| State | StateFlow |
| Navigation | Navigation abstraction |
| Testing | kotlin.test + platform tests |
| Static analysis | Detekt |
| Formatting | Ktlint + Spotless |
| Coverage | Kover |
| Documentation | Dokka |
| Dependencies | Version Catalog |
| Build | Gradle Convention Plugins |
| CI/CD | GitHub Actions |
| Ownership | CODEOWNERS |

---

# 21. Large-Team Readiness Checklist

### Architecture

- [ ] Feature-first modules
- [ ] Clear dependency direction
- [ ] Core does not depend on features
- [ ] Features do not depend on implementation details of other features
- [ ] Platform-specific code isolated
- [ ] Server separated from client applications

### Build

- [ ] Convention plugins
- [ ] Version Catalog
- [ ] Gradle build cache
- [ ] Configuration cache
- [ ] Parallel builds
- [ ] Minimal duplicated Gradle configuration

### Quality

- [ ] Detekt
- [ ] Ktlint
- [ ] Spotless
- [ ] Unit tests
- [ ] Integration tests
- [ ] UI tests where appropriate
- [ ] Architecture checks
- [ ] API compatibility checks
- [ ] Coverage reporting

### Team

- [ ] CODEOWNERS
- [ ] Feature ownership
- [ ] Pull Request rules
- [ ] Architecture documentation
- [ ] Contribution guide

### Platforms

- [ ] Android
- [ ] iOS
- [ ] Desktop
- [ ] Web/Wasm
- [ ] Server

---

# 22. Final Architecture Philosophy

The purpose of this template is not to maximize the number of modules or maximize code sharing.

The target is:

```text
                    SIMPLE
                      │
                      ▼
              CLEAR BOUNDARIES
                      │
                      ▼
              FEATURE OWNERSHIP
                      │
                      ▼
             LOW COUPLING
                      │
                      ▼
          PLATFORM INDEPENDENCE
                      │
                      ▼
            AUTOMATED QUALITY
                      │
                      ▼
             LARGE-TEAM READY
```

### Core rule

> **Keep the default architecture simple. Add a module or abstraction only when it solves a real scalability, ownership, reuse, build-performance, or dependency problem.**

This makes the template suitable for both a solo developer and a large engineering organization.
