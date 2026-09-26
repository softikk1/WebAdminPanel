import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    js {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class) wasmJs {
        browser()
    }


    sourceSets {
        commonMain.dependencies {
            implementation("io.github.softikk1:Webik:1.0.0-local")

            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.8.0")
            implementation(libs.androidx.navigation.compose)

            implementation(ktorLibs.client.core)
            implementation(ktorLibs.serialization.kotlinx.json)
            implementation(ktorLibs.client.contentNegotiation)
            implementation(ktorLibs.client.auth)

            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation("androidx.datastore:datastore-preferences-core:1.3.0-alpha11")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jsMain.dependencies {
            implementation(libs.wrappers.browser)
        }
    }
}