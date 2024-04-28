/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.4/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application

    // custom convention plugins
    id("internetrebootbot.kotlin-convention")
    id("internetrebootbot.spotless-convention")
}

dependencies {
    // playwright for running browser automation
    implementation("com.microsoft.playwright:playwright:1.43.0")

    // yaml parsing
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.0")

    // cli interface
    implementation("com.github.ajalt.clikt:clikt:4.4.0")
}

application {
    // Define the main class for the application.
    mainClass.set("ch.simschla.rebootbot.AppKt")
    applicationName = "internetbox-reboot-bot"
}

val appArchiveBaseName = "internetbox-reboot-bot"
tasks.withType<Jar>().configureEach {
    archiveBaseName.set(appArchiveBaseName)
}
