plugins {
	`multiloader-loader`
	id("fabric-loom")
}

dependencies {
	minecraft("com.mojang:minecraft:${commonMod.mc}")
	mappings(loom.layered {
		officialMojangMappings()
		commonMod.depOrNull("parchment")?.let { parchmentVersion ->
			parchment("org.parchmentmc.data:parchment-${commonMod.mc}:$parchmentVersion@zip")
		}
	})

	modImplementation("net.fabricmc:fabric-loader:${commonMod.dep("fabric-loader")}")
	modApi("net.fabricmc.fabric-api:fabric-api:${commonMod.dep("fabric-api")}+${commonMod.mc}")

	// Required dependencies
	modImplementation("com.teamresourceful.resourcefullib:resourcefullib-fabric-${commonMod.dep("resourceful-lib.mc")}:${commonMod.dep("resourceful-lib.lib")}")
	modImplementation("dev.isxander:yet-another-config-lib:${commonMod.dep("yacl")}-fabric")

	// Optional dependencies
	modImplementation("com.terraformersmc:modmenu:${commonMod.dep("modmenu")}")

	// Compat dependencies
	// Trinkets (https://www.curseforge.com/minecraft/mc-mods/trinkets)
	commonMod.depOrNull("trinkets")?.let { trinketsVersion ->
		modImplementation("dev.emi:trinkets:${trinketsVersion}")
	}
}

loom {
	accessWidenerPath = common.project.file("../../src/main/resources/${mod.id}.accesswidener")
	//accessWidenerPath = project(":common:${stonecutter.current.project}").loom.accessWidenerPath

	runs {
		getByName("client") {
			client()
			configName = "Fabric Client"
			ideConfigGenerated(true)
		}
		getByName("server") {
			server()
			configName = "Fabric Server"
			ideConfigGenerated(true)
		}
	}

	mixin {
		defaultRefmapName = "${mod.id}.refmap.json"
	}
}