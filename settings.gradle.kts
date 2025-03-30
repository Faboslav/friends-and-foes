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
    id("dev.kikugie.stonecutter") version "0.6-beta.1"
}

val commonVersions = providers.gradleProperty("stonecutter_enabled_common_versions").orNull?.split(",")?.map { it.trim() } ?: emptyList()
val fabricVersions = providers.gradleProperty("stonecutter_enabled_fabric_versions").orNull?.split(",")?.map { it.trim() } ?: emptyList()
val neoforgeVersions = providers.gradleProperty("stonecutter_enabled_neoforge_versions").orNull?.split(",")?.map { it.trim() } ?: emptyList()
val dists = mapOf(
	"common" to commonVersions,
	"fabric" to fabricVersions,
	"neoforge" to neoforgeVersions
)
val uniqueVersions = dists.values.flatten().distinct()

stonecutter {
	create(rootProject) {
		versions(*uniqueVersions.toTypedArray())
		dists.forEach { (branchName, branchVersions) ->
			branch(branchName) {
				versions(*branchVersions.toTypedArray())
			}
		}
	}
}

rootProject.name = "friendsandfoes"

