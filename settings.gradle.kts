pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "KnightsFootball"
include (":app")
include (":core-navigation")
include (":core-network")
include (":core-ui")
include (":core-common")
include (":feature-headlines")
include (":feature-rosters")
include (":feature-schedules")
