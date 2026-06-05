plugins {
    `java-library`
    alias(libs.plugins.nexuspublish)        // Publish on Maven Central
    alias(libs.plugins.dependencycheck)     // Gradle dependency check
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