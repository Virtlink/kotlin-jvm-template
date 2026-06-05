import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
    `java-library`
    `maven-publish`
    signing
    // Plugins defined in buildSrc/build.gradle.kts
    id("org.jetbrains.kotlin.jvm")
    id("com.palantir.git-version")          // Set gitVersion() from last Git repository tag
    id("com.github.ben-manes.versions")     // Check for dependency updates
    id("com.adarshr.test-logger")           // Pretty-print test results live to console
}

val gitVersion: groovy.lang.Closure<String> by extra

group = "com.example"
version = gitVersion()
description = "A library."

extra["isSnapshotVersion"] = version.toString().endsWith("-SNAPSHOT")
extra["isDirtyVersion"] = version.toString().endsWith(".dirty")
extra["isCI"] = !System.getenv("CI").isNullOrEmpty()

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
    testlogger {
        theme = ThemeType.MOCHA
    }
}

kotlin {
    jvmToolchain(21) // LTS
}

configure<JavaPluginExtension> {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("My Library")
                description.set(project.description)
                url.set("https://github.com/Virtlink/mylib")
                inceptionYear.set("2023")
                licenses {
                    // From: https://spdx.org/licenses/
                    license {
                        name.set("CC0-1.0")
                        url.set("https://creativecommons.org/publicdomain/zero/1.0/legalcode.txt")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("virtlink")
                        name.set("Daniel A. A. Pelsmaeker")
                        email.set("d.a.a.pelsmaeker@tudelft.nl")
                    }
                }
                scm {
                    connection.set("scm:git@github.com:Virtlink/mylib.git")
                    developerConnection.set("scm:git@github.com:Virtlink/mylib.git")
                    url.set("scm:git@github.com:Virtlink/mylib.git")
                }
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Virtlink/mylib")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.publishKey") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
    if (!project.hasProperty("signing.secretKeyRingFile")) {
        // If no secretKeyRingFile was set, we assume an in-memory key in the SIGNING_KEY environment variable (used in CI)
        useInMemoryPgpKeys(
            project.findProperty("signing.keyId") as String? ?: System.getenv("SIGNING_KEY_ID"),
            System.getenv("SIGNING_KEY"),
            project.findProperty("signing.password") as String? ?: System.getenv("SIGNING_KEY_PASSWORD"),
        )
    }
}

val checkNotDirty by tasks.registering {
    doLast {
        if (project.extra["isDirtyVersion"] as Boolean) {
            throw GradleException("Cannot publish a dirty version: ${project.version}")
        }
    }
}

tasks.publish { dependsOn(checkNotDirty) }