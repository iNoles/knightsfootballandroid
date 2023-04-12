plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dev.shreyaspatil.compose-compiler-report-generator")
}

android {
    namespace = "com.jonathansteele.feature.schedules"
    compileSdk = 33

    defaultConfig.minSdk = 26

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

dependencies {
    implementation(project(":core-ui"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.coil.compose)
    implementation(libs.fuel)
}
