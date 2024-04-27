plugins { id("com.diffplug.spotless") }

spotless {
    kotlin {
        target(
            "src/main/kotlin/**/*.kt",
            "src/test/kotlin/**/*.kt",
            "*.gradle.kts",
            "buildSrc/*.gradle.kts",
            "buildSrc/src/main/kotlin/**/*.kts",
            "buildSrc/src/main/kotlin/**/*.kt",
        )
        ktlint("1.1.0")
            .setEditorConfigPath(rootProject.file(".editorconfig"))
    }
    yaml {
        target(
            "src/main/resources/**/*.yml",
            "src/test/resources/**/*.yml",
        )
        prettier()
    }
    flexmark {
        target("*.md")
        flexmark()
    }
}
