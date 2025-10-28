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