plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.jonathansteele.feature.rosters"
    compileSdk = 33

    defaultConfig.minSdk = 26

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-network"))
    implementation(project(":core-ui"))
    implementation(project(":core-navigation"))

    implementation(libs.jsoup)
    implementation(libs.accompanist.pager)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
