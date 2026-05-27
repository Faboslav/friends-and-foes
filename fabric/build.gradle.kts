plugins {
	id("multiloader-loader")
	id("fabric-loom-compat")
	id("dev.kikugie.fletching-table.fabric") version "0.1.0-alpha.23"
}

fletchingTable {
	j52j.register("main") {
		extension("json", "**/*.json5")
	}
}

stonecutter {
	constants["modMenu"] = commonMod.depOrNull("mod_menu") != null
	constants["trinkets"] = commonMod.depOrNull("trinkets") != null
}

dependencies {
	minecraft("com.mojang:minecraft:${commonMod.mc}")

	if (stonecutter.eval(commonMod.mc, "<=1.21.11")) {
		mappings(loom.layered {
			officialMojangMappings()
			commonMod.depOrNull("parchment")?.let { parchmentVersion ->
				parchment("org.parchmentmc.data:parchment-${commonMod.mc}:$parchmentVersion@zip")
			}
		})
	}

	modImplementation("net.fabricmc:fabric-loader:${commonMod.dep("fabric_loader")}")
	modApi("net.fabricmc.fabric-api:fabric-api:${commonMod.dep("fabric_api")}+${commonMod.mc}")

	// Required dependencies
	modImplementation("com.teamresourceful.resourcefullib:resourcefullib-fabric-${commonMod.dep("resourceful_lib.mc")}:${commonMod.dep("resourceful_lib.lib")}")

	commonMod.depOrNull("yacl")?.let { yaclVersion ->
		modImplementation("dev.isxander:yet-another-config-lib:${yaclVersion}-fabric")
	}

	// Optional dependencies
	// Mod Menu (https://www.curseforge.com/minecraft/mc-mods/modmenu)
	commonMod.depOrNull("mod_menu")?.let { modMenuVersion ->
		modImplementation("com.terraformersmc:modmenu:${modMenuVersion}")
	}

	// Compat dependencies
	// Trinkets (https://www.curseforge.com/minecraft/mc-mods/trinkets)
	commonMod.depOrNull("trinkets")?.let { trinketsVersion ->
		if (stonecutter.eval(commonMod.mc, ">=26.1")) {
			modImplementation(fletchingTable.modrinth("trinkets-updated", commonMod.mc, "fabric"))
		} else if (stonecutter.eval(commonMod.mc, ">1.21.1")) {
			modImplementation(fletchingTable.modrinth("trinkets-canary", commonMod.mc, "fabric"))

			modImplementation("org.ladysnake.cardinal-components-api:cardinal-components-base:7.3.0")
			modImplementation("org.ladysnake.cardinal-components-api:cardinal-components-entity:7.3.0")
		} else {
			modImplementation("dev.emi:trinkets:${trinketsVersion}")
		}
	}

	/*
	commonMod.depOrNull("devauth")?.let { devauthVersion ->
		modRuntimeOnly("me.djtheredstoner:DevAuth-fabric:${devauthVersion}")
	}*/
}

loom {
	accessWidenerPath = common.project.file("../../src/main/resources/accesswideners/${commonMod.mc}-${mod.id}.accesswidener")

	runs {
		getByName("client") {
			client()
			ideConfigFolder.set("Fabric")
			configName = "Fabric Client"
			ideConfigGenerated(true)
		}
		getByName("server") {
			server()
			ideConfigFolder.set("Fabric")
			configName = "Fabric Server"
			ideConfigGenerated(true)
		}
	}

	if (stonecutter.eval(commonMod.mc, "<=1.21.11")) {
		mixin {
			useLegacyMixinAp = true
			defaultRefmapName = "${mod.id}.refmap.json"
		}
	}
}

tasks.named<ProcessResources>("processResources") {
	val awFile = project(":common").file("src/main/resources/accesswideners/${commonMod.mc}-${mod.id}.accesswidener")

	from(awFile.parentFile) {
		include(awFile.name)
		rename(awFile.name, "${mod.id}.accesswidener")
		into("")
	}
}