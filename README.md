# Kawa 🚀 [![CI][badge-ci]][ci] [![badge-mvnc-api] ![badge-mvnc-optimized-bus] ![badge-mvnc-dsl]][project-mvnc]

Open-source, powerful and modular [Kotlin][kotlin]-enhanced event bus for the [JVM][jvm].

## Importing

You can import [Kawa][project-url] from [Maven Central][mvnc] by adding it to your dependencies block.

### Gradle

```kt
repositories {
    mavenCentral()
}

dependencies {
    implementation("wtf.mizu.kawa:api:0.1.0")
    implementation("wtf.mizu.kawa:optimized-bus:0.1.0")

    // If you're using Kotlin:
    implementation("wtf.mizu.kawa:kotlin-listener-dsl:0.1.0")
}
```

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>wtf.mizu.kawa</groupId>
        <artifactId>api</artifactId>
        <version>0.1.0</version>
    </dependency>

    <dependency>
        <groupId>wtf.mizu.kawa</groupId>
        <artifactId>optimized-bus</artifactId>
        <version>0.1.0</version>
    </dependency>

    <!-- If you're using Kotlin: -->
    <dependency>
        <groupId>wtf.mizu.kawa</groupId>
        <artifactId>kotlin-listener-dsl</artifactId>
        <version>0.1.0</version>
    </dependency>
</dependencies>
```

## Usage

To get a deep understanding of the library, check out the [wiki].


## Troubleshooting

If you encounter any kind of problem **related to this library**, you can [open an issue][new-issue] describing what's
up. We ask you to be as precise as you can, so that our developers can help you as fast as possible.

Non-project-related issues will most likely be closed without further ado.


## Contributing

You can contribute to this project by [forking it][fork], making your changes, and
[creating a new pull request][new-pr].

You have to be as precise as possible while doing it though, describing in the commits (or PR description) what you're
changing, why and how.


## Licensing

This project is licensed under [the AGPLv3-only License][license].


<!-- Links -->

[jvm]: https://adoptium.net "Adoptium website"

[kotlin]: https://kotlinlang.org "Kotlin website"

[mvnc]: https://repo1.maven.org/maven2/ "Maven Central website"


<!-- Project Links -->

[project-url]: https://github.com/MizuSoftware/kawa "Project homepage"

[fork]: https://github.com/MizuSoftware/kawa/fork "Fork this repository"

[new-pr]: https://github.com/MizuSoftware/kawa/pulls/new "Create a new pull request"

[new-issue]: https://github.com/MizuSoftware/kawa/issues/new "Create a new issue"

[wiki]: https://github.com/MizuSoftware/kawa/wiki "Project wiki"

[project-mvnc]: https://search.maven.org/search?g:wtf.mizu.kawa "Project Maven Central search"

[ci]: https://github.com/MizuSoftware/kawa/actions/workflows/build.yml "Continuous integration"

[license]: https://github.com/MizuSoftware/kawa/blob/main/LICENSE "LICENSE source file"


<!-- Badges -->

[badge-mvnc-api]: https://maven-badges.herokuapp.com/maven-central/wtf.mizu.kawa/api/badge.svg "API Maven Central badge"

[badge-mvnc-optimized-bus]: https://maven-badges.herokuapp.com/maven-central/wtf.mizu.kawa/optimized-bus/badge.svg "optimized-bus Maven Central badge"

[badge-mvnc-dsl]: https://maven-badges.herokuapp.com/maven-central/wtf.mizu.kawa/kotlin-listener-dsl/badge.svg "DSL Maven Central badge"

[badge-ci]: https://github.com/MizuSoftware/kawa/actions/workflows/build.yml/badge.svg?branch=main "CI badge"
