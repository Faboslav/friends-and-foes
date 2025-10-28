plugins {
	`multiloader-loader`
	id("net.neoforged.moddev")
	id("dev.kikugie.fletching-table.neoforge") version "0.1.0-alpha.22"
}

fletchingTable {
	j52j.register("main") {
		extension("json", "**/*.json5")
	}

	accessConverter.register("main") {
		add("accesswideners/${commonMod.mc}-friendsandfoes.accesswidener")
	}
}

stonecutter {
	constants["curios"] = commonMod.depOrNull("curios") != null
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
	val at = project.file("build/resources/main/META-INF/accesstransformer.cfg");

	accessTransformers.from(at.absolutePath)
	validateAccessTransformers = true

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

tasks {
	processResources {
		exclude("${mod.id}.accesswidener")
	}
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(":neoforge:${commonMod.propOrNull("minecraft_version")}:processResources")
}