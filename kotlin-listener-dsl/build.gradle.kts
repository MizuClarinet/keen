dependencies {
    implementation(project(":api"))
    Dependencies.kotlinModules.forEach {
        implementation("org.jetbrains.kotlin:kotlin-$it:${Dependencies.KOTLIN}")
    }
    testImplementation(project(":optimized-bus"))
}
