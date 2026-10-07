plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(projects.navigation.impl)
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlin.coroutines)
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "template.desktop.MainKt"
        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb
            )
            packageName = "desktopApp"
            packageVersion = "1.0.0"
        }
    }
}
