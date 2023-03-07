pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven(url="https://androidx.dev/storage/compose-compiler/repository/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven(url="https://androidx.dev/storage/compose-compiler/repository/")
    }
}
rootProject.name = "KnightsFootball"
include (":app")
include (":core-ui")
include (":feature-headlines")
include (":feature-rosters")
include (":feature-schedules")
