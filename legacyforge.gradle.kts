plugins {
    `multiloader-common`
    id("net.neoforged.moddev.legacyforge")
}

legacyForge {
    mcpVersion = mod.mc

    val at = file("src/main/resources/META-INF/accesstransformer.cfg")
    if (at.exists()) {
        accessTransformers.from(at.absolutePath)
    }

    parchment {
        mod.depOrNull("parchment")?.let {
            mappingsVersion = it
            minecraftVersion = mod.depOrNull("parchment.mc") ?: mod.mc
        }
    }
}

dependencies {
    compileOnly("org.spongepowered:mixin:0.8.5")

    "io.github.llamalad7:mixinextras-common:0.3.5".let {
        compileOnly(it)
        annotationProcessor(it)
    }
}

val commonJava: Configuration by configurations.creating {
    isCanBeResolved = false
    isCanBeConsumed = true
}
val commonResources: Configuration by configurations.creating {
    isCanBeResolved = false
    isCanBeConsumed = true
}
artifacts {
    afterEvaluate {
        val mainSourceSet = sourceSets.main.get()
        mainSourceSet.java.sourceDirectories.files.forEach {
            add(commonJava.name, it)
        }
        mainSourceSet.resources.sourceDirectories.files.forEach {
            add(commonResources.name, it)
        }
    }
}
