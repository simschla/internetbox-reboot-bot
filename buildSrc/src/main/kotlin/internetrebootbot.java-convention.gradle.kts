import org.gradle.jvm.toolchain.JavaLanguageVersion

plugins {
    id("java-library")
}

dependencies {
    // testing
    testImplementation("org.junit.jupiter:junit-jupiter:6.0.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
