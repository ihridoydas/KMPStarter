plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "template.apps.androidapp"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "template.app.id"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions += "version"
    productFlavors {
        create("develop") {
            dimension = "version"
            applicationIdSuffix = ".develop"
            versionNameSuffix = "-develop"
        }
        create("staging") {
            dimension = "version"
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
        }
        create("production") {
            dimension = "version"
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isDebuggable = true
            buildConfigField("String", "Template_HOST", "\"192.168.10.34\"")
        }
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "Template_HOST", "\"not given\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

androidComponents {
    onVariants { variant ->
        val flavorName = variant.flavorName ?: ""
        val buildType = variant.buildType ?: ""

        val appLabel = when (buildType) {
            "debug" -> when (flavorName) {
                "develop" -> "${rootProject.name} devDebug"
                "staging" -> "${rootProject.name} stgDebug"
                "production" -> "${rootProject.name} proDebug"
                else -> "${rootProject.name} devDebug"
            }
            else -> when (flavorName) {
                "develop" -> "${rootProject.name} Develop"
                "staging" -> "${rootProject.name} Staging"
                "production" -> rootProject.name
                else -> rootProject.name
            }
        }
        variant.manifestPlaceholders.put("appLabel", appLabel)
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.platform)
    implementation(projects.core.testing)
    implementation(projects.feature.auth)
    implementation(projects.feature.home)
    implementation(projects.feature.profile)
    implementation(projects.feature.settings)
    implementation(projects.navigation.api)
    implementation(projects.navigation.impl)
    implementation(compose.components.resources)
    implementation(compose.foundation)
    implementation(compose.material3)
    implementation(compose.runtime)
    implementation(compose.ui)
    implementation(libs.android.material)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.timber)

    debugImplementation(compose.uiTooling)
    debugImplementation(libs.square.leakcanary)

    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.compose.ui.test.junit)
    androidTestImplementation(libs.junit)
}
