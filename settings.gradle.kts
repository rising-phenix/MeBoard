// Root settings for MeBoard multi-module Kotlin/Android project

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.2.0"
        id("com.android.library") version "8.2.0"
        id("org.jetbrains.kotlin.android") version "1.9.22"
        id("org.jetbrains.kotlin.jvm") version "1.9.22"
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
// include(":app") -- excluded for Kotlin-only build (AGP compatibility issue in this environment)
include(":engine:api")
include(":engine:dictionary")
include(":engine:learning")
include(":engine:prediction")
include(":engine:algorithms")
// include(":data:android") -- excluded for Kotlin-only build

// Test modules
include(":tests:engine")