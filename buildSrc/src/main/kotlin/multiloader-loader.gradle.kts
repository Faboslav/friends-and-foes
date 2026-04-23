import java.nio.charset.StandardCharsets
import java.nio.file.Files

plugins {
    id("multiloader-common")
}

val commonJava: Configuration by configurations.creating {
    isCanBeResolved = true
}
val commonResources: Configuration by configurations.creating {
    isCanBeResolved = true
}

dependencies {
    val commonPath = common.hierarchy.toString()
	compileOnly(project(path = commonPath))
    commonJava(project(path = commonPath, configuration = "commonJava"))
    commonResources(project(path = commonPath, configuration = "commonResources"))
}

tasks {
    compileJava {
        dependsOn(commonJava)
        source(commonJava)
    }

    processResources {
        dependsOn(commonResources)
        from(commonResources)

		if (project.stonecutterBuild.eval(commonMod.mc, "<=1.21.11")) {
			// Beekeeper trades
			eachFile {
				if (relativePath.pathString.contains("villager_trade") || relativePath.pathString.contains("trade_set")) {
					exclude()
				}
			}
		}

		if (project.stonecutterBuild.eval(commonMod.mc, ">=1.21.9")) {
			// Copper Golem directory (for example sounds)
			eachFile {
				if (relativePath.pathString.contains("copper_golem")) {
					exclude()
				}
			}

			// Copper Golem resources
			filesMatching("**/*copper_golem*") {
				exclude()
			}

			// Lightning Rod resources
			filesMatching("**/*lightning_rod*") {
				exclude()
			}
		}

		if (project.stonecutterBuild.eval(commonMod.mc, "<1.21.4")) {
			// Pale Oak resources
			filesMatching("**/*pale_oak*") {
				exclude()
			}
		}
    }

	jar {
		exclude("accesswideners/**")
	}
}

val generateIdeaRunConfig = tasks.register("generateIdeaRunConfig") {
	doLast {
		val loader = requireNotNull(prop("loader"))
		val loaderName = requireNotNull(prop("loader_name"))
		val version = stonecutterBuild.current.version

		listOf("Client", "Server").forEach { side ->
			val taskName = "run$side"
			val runFile = rootProject.file(".idea/runConfigurations/${loaderName}_${taskName}${version}.xml")
			val text = """
				<component name="ProjectRunConfigurationManager">
				  <configuration default="false" name="Run $version $loaderName $side" type="GradleRunConfiguration" factoryName="Gradle" nameIsGenerated="false">
				    <ExternalSystemSettings>
				      <option name="executionName" />
				      <option name="externalProjectPath" value="${rootProject.projectDir.absolutePath}" />
				      <option name="externalSystemIdString" value="GRADLE" />
				      <option name="scriptParameters" value="" />
				      <option name="taskDescriptions">
				        <list />
				      </option>
				      <option name="taskNames">
				        <list>
				          <option value=":$loader:$version:$taskName" />
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
			""".trimIndent()

			val current = if (runFile.exists()) runFile.readText() else null
			if (current != text) {
				runFile.parentFile.mkdirs()
				Files.writeString(runFile.toPath(), text, StandardCharsets.UTF_8)

				if(side == "Server") {
					val eulaContent = """
					eula=true
					""".trimIndent()
					val eulaFile = rootProject.file("${loader}/versions/${version}/run/eula.txt")
					Files.writeString(eulaFile.toPath(), eulaContent, StandardCharsets.UTF_8)
				}
			}
		}
	}
}

tasks.matching { it.name == "ideaSyncTask" || it.name == "neoForgeIdeSync" || it.name == "forgeIdeSync"}.configureEach {
	dependsOn(generateIdeaRunConfig)
}