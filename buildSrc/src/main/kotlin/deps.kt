private const val KOTLIN_VERSION = "1.7.0"

object Plugins {
    const val KOTLIN = KOTLIN_VERSION

    /**
     * Git repo informations.
     */
    const val GRGIT = "4.1.1"  // Old version to work on Java 8

    /**
     * Documentation generation.
     */
    const val DOKKA = KOTLIN

    /**
     * Maven Central publishing plugin.
     */
    const val NEXUS = "1.0.0"
}

object Dependencies {
    const val KOTLIN = KOTLIN_VERSION

    val kotlinModules = arrayOf<String>("stdlib")

    /**
     * Annotations.
     */
    const val JETBRAINS_ANNOTATIONS = "23.0.0"

    /**
     * Testing framework.
     */
    const val JUNIT = "5.8.2"
}
