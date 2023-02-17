import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.jonathansteele.core.network"
    compileSdk = 33

    defaultConfig.minSdk = 26
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.withType<KaptGenerateStubsTask>().configureEach {
    kotlinOptions.jvmTarget = "11"
}

dependencies {
    api(libs.okhttp)
    api(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
