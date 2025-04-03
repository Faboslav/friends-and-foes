plugins {
	`multiloader-loader`
	id("net.neoforged.moddev")
	id("dev.kikugie.j52j") version "2.0"
}

neoForge {
	enable {
		version = commonMod.dep("neoforge")
	}
}

dependencies {
	// Required dependencies
	implementation(
		"com.teamresourceful.resourcefullib:resourcefullib-neoforge-${commonMod.dep("resourceful-lib.mc")}:${
			commonMod.dep(
				"resourceful-lib.lib"
			)
		}"
	)
	implementation("dev.isxander:yet-another-config-lib:${commonMod.dep("yacl")}-neoforge")

	// Compat dependencies
	// Curios (https://www.curseforge.com/minecraft/mc-mods/curios)
	commonMod.depOrNull("curios")?.let { curiosVersion ->
		compileOnly("top.theillusivec4.curios:curios-neoforge:${curiosVersion}:api")
		runtimeOnly("top.theillusivec4.curios:curios-neoforge:${curiosVersion}")
	}

}

neoForge {
	accessTransformers.from(project.file("../../src/main/resources/META-INF/accesstransformer.cfg").absolutePath)

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
			minecraftVersion = commonMod.mc
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