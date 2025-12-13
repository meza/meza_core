import gg.meza.stonecraft.mod
import net.fabricmc.loom.task.RemapJarTask

plugins {
    id("gg.meza.stonecraft")
    `maven-publish`
}

val mcVersion = mod.prop("minecraft_version", stonecutter.current.version)
val isTesting = mod.prop("testing", "0") != "0"

repositories {
    maven("https://maven.shedaniel.me")
    exclusiveContent {
        forRepository {
            maven("https://api.modrinth.com/maven")
        }
        filter {
            includeGroup("maven.modrinth")
        }
    }
}

stonecutter {
    constants["isTesting"] = isTesting
    swaps["version"] = "private final String MC_VERSION = \"${mcVersion}\";"
    swaps["loader"] = "private final String LOADER = \"${mod.loader}\";"
    swaps["testing"] = when {
        isTesting -> "\"\${group}.supporters.SupportersTestMod\""
        else -> "\"\${group}.supporters.SupportersCore\""
    }
}

if (isTesting) {
    sourceSets["main"].java.srcDir("src/testmod/java")
}

dependencies {

    modImplementation(libs.posthog)
    include(libs.posthog)

    modApi("me.shedaniel.cloth:cloth-config-${mod.loader}:${mod.prop("cloth_version")}") {
        exclude(group = "net.fabricmc.fabric-api")
    }

    if (isTesting && mod.isFabric) {
        modApi("maven.modrinth:modmenu:17.0.0-alpha.1")
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
        "clothVersion" to mod.prop("cloth_version", "*"),
        "entrypoint" to when {
            isTesting -> "${mod.group}.supporters.SupportersTestMod"
            else -> "${mod.group}.supporters.SupportersCore"
        },
        "modmenuEntryPoint" to when {
            isTesting && mod.isFabric -> "[\"${mod.group}.supporters.supporters.ModMenuIntegration\"]"
            else -> "[]"
        }
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

