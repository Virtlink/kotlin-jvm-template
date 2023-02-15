import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.adarshr.gradle.testlogger.theme.ThemeType

// Workaround for IntelliJ issue where `libs` is errored: https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    java
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gitversion)
    alias(libs.plugins.benmanesVersions)
    alias(libs.plugins.testlogger)
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "java-library")
    apply(plugin = "com.palantir.git-version")      // Set gitVersion() from last Git repository tag
    apply(plugin = "com.github.ben-manes.versions") // Check for dependency updates
    apply(plugin = "com.adarshr.test-logger")       // Pretty-print test results live to console

    val gitVersion: groovy.lang.Closure<String> by extra

    group = "com.example"
    version = gitVersion()
    description = "myapp"

    repositories {
        mavenCentral()
    }

    tasks.test {
        useJUnitPlatform()
        testlogger {
            theme = ThemeType.MOCHA
        }
    }

    configure<JavaPluginExtension> {
        toolchain.languageVersion.set(JavaLanguageVersion.of(11))
        withSourcesJar()
        withJavadocJar()
    }
}
