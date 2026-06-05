plugins {
    `java-library`  // Or: java
    id("kotlin-common-conventions") // see buildSrc/src/main/kotlin/kotlin-common-conventions.gradle.kts
}

dependencies {
    // Testing
    testImplementation  (libs.kotest)
}
