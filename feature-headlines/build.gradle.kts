plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.jonathansteele.feature.headlines"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0-beta01"
    }
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-network"))
    implementation(project(":core-ui"))
    implementation(project(":core-navigation"))

    implementation(libs.coil.compose)
    implementation(libs.moshi)
    kapt(libs.moshi.kotlin.codegen)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
