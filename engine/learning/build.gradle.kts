// Engine learning module (pure Kotlin)

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
}

group = "dev.meboard"
version = "0.0.1-snapshot"

dependencies {
    implementation(project(":engine:api"))
}