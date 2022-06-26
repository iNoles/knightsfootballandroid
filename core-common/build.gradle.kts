plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.jonathansteele.core"
    compileSdk = 33

    defaultConfig.minSdk = 26
}

 dependencies {
     implementation (libs.kotlinx.coroutines.android)
 }