import org.gradle.kotlin.dsl.version

private const val kotlinVersion = "1.7.0"

object Plugins {
    const val KOTLIN = kotlinVersion
    const val NEXUS_PUBLISH = "1.0.0"
}

object Dependencies {
    const val KOTLIN = kotlinVersion

    val kotlinModules = arrayOf<String>("stdlib")
}

object Repositories {
    val mavenUrls = arrayOf<String>()
}
