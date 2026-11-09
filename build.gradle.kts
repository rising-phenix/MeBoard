plugins {
    kotlin("jvm") version "1.9.22"
}

repositories {
    google()
    mavenCentral()
}

tasks.register("verifyAll") {
    dependsOn("test", ":app:assembleDebug")
}