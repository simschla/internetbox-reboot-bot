import org.gradle.jvm.toolchain.JavaLanguageVersion

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("internetrebootbot.java-convention")
}

dependencies {
    // testing
    testImplementation("io.strikt:strikt-core:0.34.1")

    // logging
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.3")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
