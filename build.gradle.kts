import gg.meza.stonecraft.mod
import net.fabricmc.loom.task.RemapJarTask

plugins {
    id("gg.meza.stonecraft")
    `maven-publish`
}

val mcVersion = mod.prop("minecraft_version", stonecutter.current.version)

repositories {
    maven("https://maven.shedaniel.me")
}

stonecutter {
    swaps["version"] = "private final String MC_VERSION = \"${mcVersion}\";"
    swaps["loader"] = "private final String LOADER = \"${mod.loader}\";"
}

dependencies {

    modImplementation(libs.posthog)
    include(libs.posthog)

    modApi("me.shedaniel.cloth:cloth-config-${mod.loader}:${mod.prop("cloth_version")}") {
        exclude(group = "net.fabricmc.fabric-api")
    }
}

modSettings {
    clientOptions {
        fov = 90
        guiScale = 3
        narrator = false
        darkBackground = true
        musicVolume = 0.0
    }

    variableReplacements = mapOf(
        "minecraftVersionVirtual" to mcVersion,
    )
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "gg.meza"
            artifactId = "${mod.id}-${mod.loader}"
            version = "${mod.version}+${stonecutter.current.version}"
            val remapJar = project.tasks.named<RemapJarTask>("remapJar")
            artifact(remapJar.get())
        }
    }

    repositories {
        maven {
            val isSnapshot = listOf("next", "beta", "alpha", "snapshot").any { it in project.version.toString().lowercase() }
            url = uri(if (isSnapshot) "https://maven.meza.gg/snapshots" else "https://maven.meza.gg/releases")

            name = "mezaRepo"
            url = url

            credentials {
                username = System.getenv("MEZA_MAVEN_USER") ?: ""
                password = System.getenv("MEZA_MAVEN_PASSWORD") ?: ""
            }
        }
    }
}

