plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
    id ("org.jetbrains.kotlin.kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.jonathansteele.core.navigation"
    compileSdk = 33

    defaultConfig.minSdk = 26
}

dependencies {
    api (libs.androidx.hilt.navigation.compose)
    api (libs.androidx.navigation.compose)

    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)
}
