plugins {
    `multiloader-common`
	id("fabric-loom")
    //id("net.neoforged.moddev")
}


/*
loom {
	// Adds AW automatically if it's in the common resources.
	val aw = common.project.file("../../src/main/resources/${mod.id}.accesswidener")
	print(aw);
	println(aw);
	if (aw.exists()) {
		accessWidenerPath = aw
	}
}*/

/*
neoForge {
    neoFormVersion = "${mod.mc}-${mod.dep("neoform")}"

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
}*/

dependencies {
	minecraft("com.mojang:minecraft:${commonMod.mc}")
	mappings(loom.layered {
		officialMojangMappings()
		commonMod.depOrNull("parchment")?.let { parchmentVersion ->
			parchment("org.parchmentmc.data:parchment-${commonMod.depOrNull("parchment.mc") ?: commonMod.mc}:$parchmentVersion@zip")
		}
	})

    compileOnly("org.spongepowered:mixin:0.8.5")

    "io.github.llamalad7:mixinextras-common:0.3.5".let {
        compileOnly(it)
        annotationProcessor(it)
    }

    compileOnly("com.teamresourceful.resourcefullib:resourcefullib-common-${commonMod.dep("resourceful-lib.mc")}:${commonMod.dep("resourceful-lib.lib")}")
	compileOnly("dev.isxander:yet-another-config-lib:${commonMod.dep("yacl")}-fabric")
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
