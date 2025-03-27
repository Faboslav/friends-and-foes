pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.kikugie.dev/snapshots")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.6-alpha.13"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

stonecutter {
    create(rootProject) {
        val neoforgeVersions = mutableListOf<Pair<String, String>>()
        val forgeVersions = mutableListOf<Pair<String, String>>()
        fun mc(version: String, name: String = version, neoforge: Boolean = false, forge: Boolean = false) {
            vers(name, version)
            if (neoforge) neoforgeVersions.add(version to name)
            //if (forge) forgeVersions.add(version to name)
        }

        // Configure targets here. Fabric is applied to all.
        mc("1.21.4", neoforge = true)
        mc("1.21.1", neoforge = true)


        branch("fabric") // empty block copies common
        branch("neoforge") {
            neoforgeVersions.forEach { (version, name) -> vers(name, version) }
        }
		/*
        branch("forge") {
            forgeVersions.forEach { (version, name) -> vers(name, version) }
        }*/

        mapBuilds { branch, meta ->
            if (branch == "" && stonecutter.eval(meta.version, "<1.20.4"))
                "legacyforge.gradle.kts"
            else "build.gradle.kts"
        }
    }
}

rootProject.name = "friendsandfoes"

