plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dev.shreyaspatil.compose-compiler-report-generator")
}

android {
    namespace = "com.jonathansteele.feature.schedules"
    compileSdk = 33

    defaultConfig.minSdk = 26

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(project(":core-ui"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.coil.compose)
    implementation(libs.fuel)
}
