plugins {
    kotlin("jvm") version "1.7.0"
}

dependencies {
    implementation(project(":api"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.0")
    testImplementation(project(":optimized-bus"))
}