plugins {
    `java-library`
    alias(libs.plugins.nexuspublish)        // Publish on Maven Central
    alias(libs.plugins.dependencycheck)     // Gradle dependency check
    alias(libs.plugins.gitVersion)          // Set gitVersion() from last Git repository tag
    alias(libs.plugins.benmanesVersions)    // Check for dependency updates
}

val gitVersion = extra["gitVersion"] as groovy.lang.Closure<*>

allprojects {
    group = "[[maven_group]]"
    version = if (rootProject.file(".git").isDirectory) gitVersion.call() else "0.1.0-SNAPSHOT"
    description = "[[project_description]]"
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://oss.sonatype.org/content/repositories/snapshots/"))
            username.set(project.findProperty("ossrh.user") as String? ?: System.getenv("OSSRH_USERNAME"))
            password.set(project.findProperty("ossrh.token") as String? ?: System.getenv("OSSRH_TOKEN"))
        }
    }
}
