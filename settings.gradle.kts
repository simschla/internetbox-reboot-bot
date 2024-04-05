pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "internetbox-reboot-bot"
include("app")

buildCache {
    local {
        if ((System.getenv().get("BUILDCACHE_LOCAL_DEFAULT_DIRECTORY") ?: "false") == "false") {
            directory = file(".build-cache")
        }
        removeUnusedEntriesAfterDays = 30
    }
}
