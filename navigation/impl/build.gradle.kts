@file:Suppress("DEPRECATION")

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    jvm()
    iosArm64()
    iosSimulatorArm64()
    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { target ->
        target.binaries.framework {
            baseName = "navigationImpl"
            isStatic = true
        }
    }
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.navigation.api)
                api(projects.core.designsystem)
                api(projects.core.network)
                implementation(projects.core.model)
                implementation(projects.core.datastore)
                implementation(projects.core.common)
                implementation(projects.core.platform)
                implementation(projects.feature.home)
                implementation(projects.feature.auth)
                implementation(projects.feature.profile)
                implementation(projects.feature.settings)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                
                implementation(libs.kotlinx.serialization.json)
                api(libs.jetbrains.navigation3)
                api(libs.androidx.navigation3.runtime)
                implementation(libs.androidx.lifecycle.viewmodel.navigation3)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
            }
        }
    }
}

tasks.register("packForXcode", Sync::class) {
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdk = System.getenv("PLATFORM_NAME") ?: "iphonesimulator"
    val targetName = if (sdk.startsWith("iphonesimulator")) "iosSimulatorArm64" else "iosArm64"
    val framework = kotlin.targets.getByName<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>(targetName).binaries.getFramework(if (mode.uppercase() == "DEBUG") "DEBUG" else "RELEASE")
    inputs.dir(framework.outputFile)
    from(framework.outputFile.parentFile)
    into(layout.buildDirectory.dir("xcode-frameworks/$mode"))
}

tasks.named("build") {
    dependsOn("packForXcode")
}

android {
    namespace = "template.navigation.impl"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
