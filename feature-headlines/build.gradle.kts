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
    kotlin {
        jvmToolchain(11)
    }
}

dependencies {
    implementation(project(":core-ui"))

    implementation(libs.coil.compose)
    implementation(libs.fuel.moshi.jvm)
    implementation(libs.androidx.core.ktx)

    ksp(libs.moshi.kotlin.codegen)
}
