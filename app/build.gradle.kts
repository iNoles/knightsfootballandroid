plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("org.jetbrains.kotlin.kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.jonathansteele.knightsfootball"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.jonathansteele.knightsfootball"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation (project(":core-navigation"))
    implementation (project(":core-ui"))
    implementation (project(":feature-headlines"))
    implementation (project(":feature-rosters"))
    implementation (project(":feature-schedules"))

    implementation (libs.androidx.activity.compose)
    implementation (libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)
    testImplementation (libs.junit4)
    androidTestImplementation (libs.androidx.test.ext)
    androidTestImplementation (libs.androidx.test.espresso.core)
    androidTestImplementation (libs.androidx.compose.ui.test.junit4)
    debugImplementation (libs.androidx.compose.ui.test.manifest)
}