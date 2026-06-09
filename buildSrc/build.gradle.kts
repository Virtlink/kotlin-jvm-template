plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    // Gradle plugin dependencies
    implementation(plugin(libs.plugins.kotlin.jvm))
    implementation(plugin(libs.plugins.testlogger))          // Pretty-print test results live to console
}

// Helper function (https://docs.gradle.org/current/userguide/version_catalogs.html#sec:buildsrc-version-catalog)
fun DependencyHandlerScope.plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
