dependencies {
    Dependencies.kotlinModules.forEach {
        implementation("org.jetbrains.kotlin:kotlin-$it:${Dependencies.KOTLIN}")
    }
    implementation(project(":api"))

    testImplementation(project(":optimized-bus"))
}
