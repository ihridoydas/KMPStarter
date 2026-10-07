import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.detektGradlePlugin)
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.sortDependencies) apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.gradle.versions) apply false
    alias(libs.plugins.spotless) apply false
}

apply(from = "buildscripts/githooks.gradle")
apply(from = "buildscripts/setup.gradle")
apply(from = "buildscripts/versionsplugin.gradle")
apply(from = "spotless.gradle")

subprojects {
    if (!project.path.startsWith(":apps")) {
        pluginManager.apply("org.jetbrains.dokka")
        
        extensions.configure<org.jetbrains.dokka.gradle.DokkaExtension>("dokka") {
            dokkaPublications.configureEach {
                outputDirectory.set(rootProject.layout.projectDirectory.dir("docs/${project.name}"))
            }
            dokkaSourceSets.configureEach {
                enableAndroidDocumentationLink.set(true)
            }
        }
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        jvmTarget = "17"
        config.setFrom(rootProject.layout.projectDirectory.file("config/detekt/detekt.yml"))
    }

    plugins.withType<AppPlugin> {
    }

    plugins.withType<LibraryPlugin> {
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.addAll(
                listOf("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"),
            )
        }
    }
}

tasks.register<io.gitlab.arturbosch.detekt.Detekt>("detektAll") {
    parallel = true
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
    config.setFrom(project.layout.projectDirectory.file("config/detekt/detekt.yml"))
    buildUponDefaultConfig = false
}

allprojects {
    afterEvaluate {
        val wasmLockFile = layout.buildDirectory.file("wasm/package-lock.json").get().asFile
        wasmLockFile.parentFile.mkdirs()
        if (!wasmLockFile.exists()) {
            wasmLockFile.writeText("{}")
        }
    }

    tasks.matching { it.name.contains("UpgradePackageLock") || it.name.contains("LockStoreTask") }.configureEach {
        val task = this
        try {
            val inputFileProp = task.javaClass.methods.firstOrNull { it.name == "getInputFile" }
            val inputFile = inputFileProp?.invoke(task)
            val file = when (inputFile) {
                is File -> inputFile
                is org.gradle.api.file.RegularFile -> inputFile.asFile
                is org.gradle.api.provider.Provider<*> -> (inputFile.orNull as? org.gradle.api.file.RegularFile)?.asFile
                else -> null
            }
            file?.let {
                it.parentFile?.mkdirs()
                if (!it.exists()) {
                    it.writeText("{}")
                }
            }
        } catch (_: Exception) {
            // Ignore reflection errors if method signature changes
        }
    }
}
