// Root settings for MeBoard multi-module Kotlin/Android project

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MeBoard"

// Production modules
include(":app")
include(":engine:api")
include(":engine:dictionary")
include(":engine:learning")
include(":engine:prediction")
include(":engine:algorithms")
include(":data:android")