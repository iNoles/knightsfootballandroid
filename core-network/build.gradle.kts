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
}

dependencies {
    api(libs.okhttp)
    api(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
