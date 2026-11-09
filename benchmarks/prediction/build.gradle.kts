// MeBoard benchmark module (JVM)
// Benchmark modules are NOT runtime dependencies of :app.

plugins {
    id("org.jetbrains.kotlin.jvm")
}

group = "dev.meboard"
version = "0.0.1-snapshot"

dependencies {
    implementation(project(":engine:api"))
    implementation(project(":engine:dictionary"))
    implementation(project(":engine:learning"))
    implementation(project(":engine:prediction"))
    implementation(project(":engine:algorithms"))
}