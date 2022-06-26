plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.jonathansteele.core.ui"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-rc02"
    }
}

dependencies {
    implementation (libs.androidx.core.ktx)
    api (libs.androidx.compose.ui)
    api (libs.androidx.compose.material3)
    debugApi (libs.androidx.compose.ui.tooling)
    api (libs.androidx.compose.ui.tooling.preview)
}
