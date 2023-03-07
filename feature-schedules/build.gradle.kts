import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask

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

tasks.withType<KaptGenerateStubsTask>().configureEach {
    kotlinOptions.jvmTarget = "11"
}

dependencies {
    implementation(project(":core-ui"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.coil.compose)
    implementation("com.github.kittinunf.fuel:fuel:3.0.0-SNAPSHOT")
}
