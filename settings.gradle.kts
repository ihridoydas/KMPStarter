pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
rootProject.name = "KMPStarter"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":apps:androidApp")
include(":apps:desktopApp")
include(":apps:webApp")
include(":apps:serverApp")
include(":core:common")
include(":core:model")
include(":core:network")
include(":core:database")
include(":core:datastore")
include(":core:platform")
include(":core:designsystem")
include(":core:testing")
include(":navigation:api")
include(":navigation:impl")
include(":feature:auth")
include(":feature:home")
include(":feature:profile")
include(":feature:settings")

project(":apps:androidApp").projectDir = file("apps/androidApp")
project(":apps:desktopApp").projectDir = file("apps/desktopApp")
project(":apps:webApp").projectDir = file("apps/webApp")
project(":apps:serverApp").projectDir = file("apps/serverApp")
project(":core:common").projectDir = file("core/common")
project(":core:model").projectDir = file("core/model")
project(":core:network").projectDir = file("core/network")
project(":core:database").projectDir = file("core/database")
project(":core:datastore").projectDir = file("core/datastore")
project(":core:platform").projectDir = file("core/platform")
project(":core:designsystem").projectDir = file("core/designsystem")
project(":core:testing").projectDir = file("core/testing")
project(":navigation:api").projectDir = file("navigation/api")
project(":navigation:impl").projectDir = file("navigation/impl")
project(":feature:auth").projectDir = file("feature/auth")
project(":feature:home").projectDir = file("feature/home")
project(":feature:profile").projectDir = file("feature/profile")
project(":feature:settings").projectDir = file("feature/settings")
