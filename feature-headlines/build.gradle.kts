import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
    id("dev.shreyaspatil.compose-compiler-report-generator")
}

android {
    namespace = "com.jonathansteele.feature.headlines"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33
    }
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
    implementation(project(":core-common"))
    implementation(project(":core-network"))
    implementation(project(":core-ui"))

    implementation(libs.coil.compose)
    implementation("dev.olshevski.navigation:reimagined-hilt:1.3.1")

    implementation(libs.moshi)
    kapt(libs.moshi.kotlin.codegen)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
