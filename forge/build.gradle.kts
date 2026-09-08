val IS_CI = System.getenv("CI") == "true"

plugins {
	`multiloader-loader`
	id("net.neoforged.moddev.legacyforge")
	id("dev.kikugie.fletching-table.lexforge") version "0.1.0-alpha.23"
}

fletchingTable {
	j52j.register("main") {
		extension("json", "**/*.json5")
	}
}

stonecutter {
	constants["curios"] = commonMod.depOrNull("curios") != null
}

mixin {
	add(sourceSets.main.get(), "${mod.id}.refmap.json")

	config("${mod.id}-common.mixins.json")
	config("${mod.id}-forge.mixins.json")
}

legacyForge {
	val at = project.file("build/resources/main/META-INF/accesstransformer.cfg")

	accessTransformers.from(at.absolutePath)
	validateAccessTransformers = true

	enable {
		forgeVersion = "${commonMod.mc}-${commonMod.dep("forge")}"
	}
}

dependencies {
	compileOnly("org.jetbrains:annotations:24.1.0")
	annotationProcessor("org.spongepowered:mixin:0.8.5-SNAPSHOT:processor")

	"io.github.llamalad7:mixinextras-common:0.4.1".let {
		compileOnly(it)
		annotationProcessor(it)
	}

	"io.github.llamalad7:mixinextras-forge:0.4.1".let {
		implementation(it)
		jarJar(it)
	}

	// Required dependencies
	modImplementation("com.teamresourceful.resourcefullib:resourcefullib-forge-${commonMod.dep("resourceful_lib.mc")}:${commonMod.dep("resourceful_lib.lib")}")
	commonMod.depOrNull("yacl")?.let { yaclVersion ->
		modImplementation("dev.isxander:yet-another-config-lib:${yaclVersion}-forge")
	}

	// Compat dependencies
	// Curios (https://www.curseforge.com/minecraft/mc-mods/curios)
	commonMod.depOrNull("curios")?.let { curiosVersion ->
		compileOnly("top.theillusivec4.curios:curios-forge:${curiosVersion}:api")
		runtimeOnly("top.theillusivec4.curios:curios-forge:${curiosVersion}")
	}
}

legacyForge {
	runs {
		register("client") {
			client()
			ideFolderName = "Forge"
			ideName = "Forge Client (${project.path})"
		}
		register("server") {
			server()
			ideFolderName = "Forge"
			ideName = "Forge Server (${project.path})"
		}
	}

	if(!IS_CI) {
		parchment {
			commonMod.depOrNull("parchment")?.let {
				mappingsVersion = it
				minecraftVersion = commonMod.mc
			}
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
	jar {
		finalizedBy("reobfJar")
		manifest {
			attributes(
				mapOf(
					"MixinConfigs" to "${mod.id}-common.mixins.json,${mod.id}-forge.mixins.json"
				)
			)
		}
	}
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(":forge:${commonMod.propOrNull("minecraft_version")}:processResources")
}
