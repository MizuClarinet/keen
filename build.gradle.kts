plugins {
    `java-library`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.0.0"
}

group = "wtf.mizu.keen"
version = "1.0-SNAPSHOT"

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "signing")
    apply(plugin = "java-library")

    group = parent!!.group
    version = parent!!.version

    repositories {
        mavenCentral()
    }

    java {
        withSourcesJar()
        withJavadocJar()
    }

    publishing.publications {
        create("mavenJava", MavenPublication::class.java) {
            from(components["java"])
            groupId = project.group.toString()

            signing {
                isRequired = project.properties["signing.keyId"] != null
                sign(this@create)
            }
        }
    }
}

// Configure publishing to Maven Central
nexusPublishing.repositories.sonatype {
    nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
    snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))

    // Skip this step if environment variables NEXUS_USERNAME or NEXUS_PASSWORD aren't set.
    username.set(properties["NEXUS_USERNAME"] as? String ?: return@sonatype)
    password.set(properties["NEXUS_PASSWORD"] as? String ?: return@sonatype)
}