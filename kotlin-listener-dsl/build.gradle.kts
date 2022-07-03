apply(plugin = "org.jetbrains.kotlin.jvm")

dependencies {
    implementation(project(":api"))
    Dependencies.kotlinModules.forEach {
        implementation("org.jetbrains.kotlin:kotlin-$it:${Dependencies.KOTLIN}")
    }
    testImplementation(project(":optimized-bus"))
}
