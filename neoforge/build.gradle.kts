plugins {
	`multiloader-loader`
	id("net.neoforged.moddev")
}

dependencies {
	implementation("com.teamresourceful.resourcefullib:resourcefullib-neoforge-${commonMod.dep("resourceful-lib.mc")}:${commonMod.dep("resourceful-lib.lib")}")
	implementation("dev.isxander:yet-another-config-lib:${commonMod.dep("yacl")}-neoforge")
}

// Enables ModDevGradle as early as possible so we can use their tasks and configurations.
neoForge {
	enable {
		version = commonMod.dep("neoforge")
	}
}



// Add your dependencies here
dependencies {

}

// Configure mixins here, other parts of this buildscript will be configured for you.
val mixinConfigs = listOf(
	"${mod.id}.mixins.json",
	"${mod.id}.neoforge.mixins.json",
)


// ModDevGradle configuration is done here.
neoForge {
	// Accesss transformer is automatically applied if it is present in common.
	val at = common.project.file("src/main/resources/META-INF/accesstransformer.cfg")
	if (at.exists()) {
		accessTransformers.from(at.absolutePath)
	}

	runs {
		register("client") {
			client()
			ideName = "NeoForge Client (${project.path})"
		}
		register("server") {
			server()
			ideName = "NeoForge Server (${project.path})"
		}
	}

	parchment {
		commonMod.depOrNull("parchment")?.let {
			mappingsVersion = it
			minecraftVersion = commonMod.depOrNull("parchment.mc") ?: commonMod.mc
		}
	}

	mods {
		register(commonMod.id) {
			sourceSet(sourceSets.main.get())
		}
	}
}

sourceSets.main {
	resources.srcDir("src/generated/resources")
}

// Adds mixins programmatically into the mod manifest.
extraProcessResourceKeys["mixins"] =
	mixinConfigs.joinToString(separator = "\n") { name ->
		"""
            [[mixins]]
            config = "$name"
        """.trimIndent()
	}