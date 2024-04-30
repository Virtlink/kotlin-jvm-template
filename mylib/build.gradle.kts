plugins {
    `java-library`  // Or: java
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    // Testing
    testImplementation  (libs.kotest)
}
