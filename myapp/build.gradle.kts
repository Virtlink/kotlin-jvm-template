// Workaround for IntelliJ issue where `libs` is errored: https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `java-library`  // Or: java
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    // Testing
    testImplementation  (libs.kotest)
}
