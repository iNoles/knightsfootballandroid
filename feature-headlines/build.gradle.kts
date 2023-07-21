plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dev.shreyaspatil.compose-compiler-report-generator")
}

android {
    namespace = "com.jonathansteele.feature.headlines"
    compileSdk = 33

    defaultConfig.minSdk = 26

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(project(":core-ui"))

    implementation(libs.coil.compose)
    implementation(libs.fuel.moshi.jvm)
    implementation(libs.androidx.core.ktx)

    ksp(libs.moshi.kotlin.codegen)
}
