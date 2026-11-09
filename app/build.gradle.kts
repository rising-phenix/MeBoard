// MeBoard application module (Android)
// Stub build file for M0 skeleton; production Android code is added in later milestones.

plugins {
    id("com.android.application") version "8.0.0"
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "dev.meboard.app"
    compileSdk = 36

    defaultConfig {
        minSdk = 28
        targetSdk = 36
    }
}

dependencies {
    implementation(project(":engine:api"))
    implementation(project(":engine:dictionary"))
    implementation(project(":engine:learning"))
    implementation(project(":engine:prediction"))
    implementation(project(":engine:algorithms"))
    implementation(project(":data:android"))
}