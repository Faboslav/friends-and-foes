plugins {
	id("java")
	id("idea")
	id("java-library")
}

version = "${loader}-${commonMod.version}+mc${stonecutterBuild.current.version}"

base {
	archivesName = commonMod.id
}

java {
	toolchain.languageVersion = JavaLanguageVersion.of(commonProject.prop("java.version")!!)
	// withSourcesJar()
	// withJavadocJar()
}

repositories {
    mavenCentral()
    exclusiveContent {
        forRepository {
            maven("https://repo.spongepowered.org/repository/maven-public") { name = "Sponge" }
        }
        filter { includeGroupAndSubgroups("org.spongepowered") }
    }
    exclusiveContent {
        forRepositories(
            maven("https://maven.parchmentmc.org") { name = "ParchmentMC" },
            maven("https://maven.neoforged.net/releases") { name = "NeoForge" },
        )
        filter { includeGroup("org.parchmentmc.data") }
    }
	maven("https://www.cursemaven.com")
	maven("https://api.modrinth.com/maven") {
		name = "Modrinth"
		content {
			includeGroup("maven.modrinth")
		}
	}
	maven("https://maven.resourcefulbees.com/repository/maven-public/") { name = "ResourcefulBees" }
	maven("https://maven.terraformersmc.com/releases/") { name = "TerraformersMC" }
	maven("https://maven.isxander.dev/releases")
	maven("https://maven.isxander.dev/snapshots")
	maven("https://maven.quiltmc.org/repository/release")
	maven("https://oss.sonatype.org/content/repositories/snapshots")
	maven("https://maven.ladysnake.org/releases") { name = "Ladysnake Libs" }
	maven("https://maven.theillusivec4.top/")
	maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")

	maven("https://maven.jamieswhiteshirt.com/libs-release") {
		content {
			includeGroup("com.jamieswhitefshirt")
		}
	}

	maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
}

tasks {

	processResources {
		val expandProps = mapOf(
			"javaVersion" to commonMod.propOrNull("java.version"),
			"modId" to commonMod.id,
			"modName" to commonMod.name,
			"modVersion" to commonMod.version,
			"modGroup" to commonMod.group,
			"modAuthor" to commonMod.author,
			"modDescription" to commonMod.description,
			"modLicense" to commonMod.license,
			"modGitHub" to commonMod.github,
			"modDiscord" to commonMod.discord,
			"minecraftVersion" to commonMod.propOrNull("minecraft_version"),
			"minMinecraftVersion" to commonMod.propOrNull("min_minecraft_version"),
			"fabricLoaderVersion" to commonMod.depOrNull("fabric-loader"),
			"fabricApiVersion" to commonMod.depOrNull("fabric-api"),
			"neoForgeVersion" to commonMod.depOrNull("neoforge"),
			"yaclVersion" to commonMod.depOrNull("yacl"),
			"resourcefulLibMcVersion" to commonMod.depOrNull("resourceful-lib.mc"),
			"resourcefulLibLibVersion" to commonMod.depOrNull("resourceful-lib.lib"),
		).filterValues { it?.isNotEmpty() == true }.mapValues { (_, v) -> v!! }

		val jsonExpandProps = expandProps.mapValues { (_, v) -> v.replace("\n", "\\\\n") }

			filesMatching(listOf("META-INF/mods.toml", "META-INF/neoforge.mods.toml")) {
				expand(expandProps)
			}

		filesMatching(listOf("pack.mcmeta", "fabric.mod.json", "*.mixins.json")) {
			expand(jsonExpandProps)
		}

		inputs.properties(expandProps)
	}
}

tasks.named("processResources") {
	dependsOn(":common:${commonMod.propOrNull("minecraft_version")}:stonecutterGenerate")
}
