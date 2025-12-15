import java.nio.file.StandardOpenOption
import kotlin.io.path.writeText

plugins {
	id("fabric-loom")
	id("multiloader-loader")
	id("dev.kikugie.fletching-table.fabric") version "0.1.0-alpha.22"
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
	mappings(loom.layered {
		officialMojangMappings()
		commonMod.depOrNull("parchment")?.let { parchmentVersion ->
			parchment("org.parchmentmc.data:parchment-${commonMod.mc}:$parchmentVersion@zip")
		}
	})

	modImplementation("net.fabricmc:fabric-loader:${commonMod.dep("fabric-loader")}")
	modApi("net.fabricmc.fabric-api:fabric-api:${commonMod.dep("fabric-api")}+${commonMod.mc}")

	// Required dependencies
	modImplementation(
		"com.teamresourceful.resourcefullib:resourcefullib-fabric-${commonMod.dep("resourceful-lib.mc")}:${
			commonMod.dep(
				"resourceful-lib.lib"
			)
		}"
	)
	modImplementation("dev.isxander:yet-another-config-lib:${commonMod.dep("yacl")}-fabric")

	// Optional dependencies
	// Mod Menu (https://www.curseforge.com/minecraft/mc-mods/modmenu)
	commonMod.depOrNull("mod_menu")?.let { modMenuVersion ->
		modImplementation("com.terraformersmc:modmenu:${modMenuVersion}")
	}

	// Compat dependencies
	// Trinkets (https://www.curseforge.com/minecraft/mc-mods/trinkets)
	commonMod.depOrNull("trinkets")?.let { trinketsVersion ->
		modImplementation("dev.emi:trinkets:${trinketsVersion}")
	}

	/*
	commonMod.depOrNull("devauth")?.let { devauthVersion ->
		modRuntimeOnly("me.djtheredstoner:DevAuth-fabric:${devauthVersion}")
	}*/
}

loom {
	accessWidenerPath = common.project.file("../../src/main/resources/accesswideners/${commonMod.mc}-${mod.id}.accesswidener")

	/*
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
	}*/

	mixin {
		defaultRefmapName = "${mod.id}.refmap.json"
	}
}

// Use this, until https://github.com/FabricMC/fabric-loom/issues/1349 is fixed
val loader = "fabric"
val version = stonecutter.current.project
val text = """
<component name="ProjectRunConfigurationManager">
  <configuration default="false" factoryName="Gradle" name="$loader (:$loader:$version)" type="GradleRunConfiguration" folderName="Client" nameIsGenerated="false">
    <ExternalSystemSettings>
      <option name="executionName" />
      <option name="externalProjectPath" value="${project.projectDir}" />
      <option name="externalSystemIdString" value="GRADLE" />
      <option name="scriptParameters" value="" />
      <option name="taskDescriptions">
        <list />
      </option>
      <option name="taskNames">
        <list>
          <option value=":$loader:$version:runClient" />
        </list>
      </option>
      <option name="vmOptions" />
    </ExternalSystemSettings>
    <ExternalSystemDebugServerProcess>true</ExternalSystemDebugServerProcess>
    <ExternalSystemReattachDebugProcess>true</ExternalSystemReattachDebugProcess>
    <ExternalSystemDebugDisabled>false</ExternalSystemDebugDisabled>
    <DebugAllEnabled>false</DebugAllEnabled>
    <RunAsTest>false</RunAsTest>
    <GradleProfilingDisabled>false</GradleProfilingDisabled>
    <GradleCoverageDisabled>false</GradleCoverageDisabled>
    <method v="2" />
  </configuration>
</component>
"""
rootProject.file(".idea/runConfigurations/client_${version}_${loader}.run.xml").toPath()
	.writeText(text, Charsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)

tasks.named<ProcessResources>("processResources") {
	val awFile = project(":common").file("src/main/resources/accesswideners/${commonMod.mc}-${mod.id}.accesswidener")

	from(awFile.parentFile) {
		include(awFile.name)
		rename(awFile.name, "${mod.id}.accesswidener")
		into("")
	}
}