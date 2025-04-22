pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.kikugie.dev/releases")
        maven("https://maven.kikugie.dev/snapshots")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.neoforged.net/releases/")
    }
}
plugins {
    id("gg.meza.stonecraft") version "1.2.0"
    id("dev.kikugie.stonecutter") version "0.6-beta.2"
}

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true
    shared {
        fun mc(version: String, vararg loaders: String) {
            // Make the relevant version directories named "1.20.2-fabric", "1.20.2-forge", etc.
            for (it in loaders) vers("$version-$it", version)
        }

        mc("1.19.2", "fabric")
        mc("1.19.4", "fabric")
        mc("1.21", "fabric", "neoforge")
        mc("1.21.1", "fabric", "neoforge")
        mc("1.21.4", "fabric", "neoforge")
        mc("1.21.5", "fabric", "neoforge")
        mc("25w16a", "fabric")

        vcsVersion = "1.21.5-fabric"
    }
    create(rootProject)
}

rootProject.name = "supporters_core"
