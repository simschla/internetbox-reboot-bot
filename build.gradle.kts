plugins {
    id("internetrebootbot.spotless-convention")
}

allprojects {
    repositories {
        // Use Maven Central for resolving dependencies.
        mavenCentral()
    }
}
