import com.adarshr.gradle.testlogger.theme.ThemeType
import java.net.URI

plugins {
    `java-library`
    `maven-publish`
    signing
    // Plugins defined in buildSrc/build.gradle.kts
    id("org.jetbrains.kotlin.jvm")
    id("com.adarshr.test-logger")           // Pretty-print test results live to console
}


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
                name.set("[[project_name]]")
                description.set(project.description)
                url.set("https://github.com/[[github_owner]]/[[project_slug]]")
                inceptionYear.set("[[inception_year]]")
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
                        id.set("[[developer_id]]")
                        name.set("[[developer_name]]")
                        email.set("[[developer_email]]")
                    }
                }
                scm {
                    connection.set("scm:git@github.com:[[github_owner]]/[[project_slug]].git")
                    developerConnection.set("scm:git@github.com:[[github_owner]]/[[project_slug]].git")
                    url.set("scm:git@github.com:[[github_owner]]/[[project_slug]].git")
                }
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = URI("https", "maven.pkg.github.com", "/[[github_owner]]/[[project_slug]]", null)
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

val checkNotDirty = tasks.register("checkNotDirty") {
    doLast {
        if (version.toString().endsWith(".dirty")) {
            throw GradleException("Cannot publish a dirty version: ${project.version}")
        }
    }
}

tasks.publish { dependsOn(checkNotDirty) }
