// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.diffplug.spotless") version "5.14.3"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

subprojects {

    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            // by default the target is every '.kt' and '.kts` file in the java sourcesets
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")
            ktlint("0.42.1").userData(
                mapOf("max_line_length" to "120", "disabled_rules" to "import-ordering")
            )
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
        }
    }
}
