/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.4/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.9.22"

    // add spotless for formatting
    id("com.diffplug.spotless") version "6.23.3"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("io.strikt:strikt-core:0.34.1")

    // playwright for running browser automation
    implementation("com.microsoft.playwright:playwright:1.40.0")

    // logging
    implementation ("io.github.microutils:kotlin-logging-jvm:3.0.5")
    runtimeOnly("ch.qos.logback:logback-classic:1.4.14")

    // yaml parsing
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.16.1")

    // cli interface
    implementation("com.github.ajalt.clikt:clikt:4.2.2")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

spotless {
    kotlin {
        target("**/*.kt")
        ktlint("1.1.0")
            .setEditorConfigPath(project.file("../.editorconfig"))
    }
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

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
