plugins { `kotlin-dsl` }

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:7.0.3")
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.21")
//    implementation("gradle.plugin.org.jetbrains.gradle.plugin.idea-ext:gradle-idea-ext:1.1.8")
    implementation("com.bmuschko:gradle-docker-plugin:9.4.0")
}
