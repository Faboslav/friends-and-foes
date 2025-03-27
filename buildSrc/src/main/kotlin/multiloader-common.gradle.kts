plugins {
    `java-library`
}

project.properties.forEach { println("${it.key} = ${it.value}") }

version = "${commonMod.version}+${stonecutterBuild.current.version}-${loader}"

base {
	archivesName = commonMod.id
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(commonProject.prop("java.version")!!)
    withSourcesJar()
    withJavadocJar()
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
	maven("https://maven.resourcefulbees.com/repository/maven-public/") { name = "ResourcefulBees" }
	maven("https://maven.terraformersmc.com/releases/") { name = "TerraformersMC" }
	maven("https://maven.isxander.dev/releases")
	maven("https://maven.isxander.dev/snapshots")
	maven("https://maven.quiltmc.org/repository/release")
	maven("https://oss.sonatype.org/content/repositories/snapshots")
}

// Declare capabilities on the outgoing configurations.
// Read more about capabilities here: https://docs.gradle.org/current/userguide/component_capabilities.html#sec:declaring-additional-capabilities-for-a-local-component
listOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements").forEach { variant ->
    configurations[variant].outgoing {
        capability("$group:$loader:$version")
        capability("$group:${base.archivesName.get()}:$version")
        capability("$group:${mod.id}-$loader-${stonecutterBuild.current.version}:$version")
        capability("$group:${mod.id}:$version")
    }
}

tasks {
    named<Jar>("sourcesJar") {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${commonMod.id}" }
        }
    }

    jar {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${commonMod.id}" }
        }

        manifest.attributes(
			"Specification-Title" to commonMod.name,
			"Specification-Vendor" to commonMod.author,
			"Specification-Version" to commonMod.version,
        )
    }

    processResources {
        val expandProps = (mapOf(
            "mod_version" to commonMod.version,
            "mod_group" to commonMod.group,
            "mod_author" to commonMod.author,
            "minecraft" to commonMod.mc,
            "mod_name" to commonMod.name,
            "mod_id" to commonMod.id,
            "mod_description" to commonMod.description,
            "mod_license" to commonMod.license,
            "fabric_loader_version" to commonMod.depOrNull("fabric-loader"),
            "fabric_minecraft_constraint" to commonMod.propOrNull("meta.fabric.minecraft-constraint"),
            "neoforge_version" to commonMod.depOrNull("neoforge"),
            "neoforge_loader_constraint" to commonMod.propOrNull("meta.neoforge.loader-constraint"),
            "forge_version" to commonMod.depOrNull("forge"),
            "forge_loader_constraint" to commonMod.propOrNull("meta.forge.loader-constraint"),
            "forgelike_minecraft_constraint" to commonMod.propOrNull("meta.forgelike.minecraft-constraint"),
            "pack_format" to commonMod.propOrNull("meta.pack-format"),
        ) + extraProcessResourceKeys)
            .filterValues { it?.isNotEmpty() == true }
            .mapValues { (_, v) -> v!! }

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
