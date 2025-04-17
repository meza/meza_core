plugins {
    id("dev.kikugie.stonecutter")
    id("gg.meza.stonecraft")
}

stonecutter active "1.19.4-fabric" /* [SC] DO NOT EDIT */

stonecutter.registerChiseled(
    project.tasks.register("chiseledMavenPublish", stonecutter.chiseled) {
        group = "modsall"
        description = "Executes publish for all versions and loaders"
        ofTask("publish")
    }
)

stonecutter.registerChiseled(
    project.tasks.register("chiseledMavenPublishLocal", stonecutter.chiseled) {
        group = "modsall"
        description = "Executes publish for all versions and loaders"
        ofTask("publishToMavenLocal")
    }
)
