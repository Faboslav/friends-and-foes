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
    compileOnly(project(path = commonPath)) {
        capabilities {
            requireCapability("$group:${mod.id}")
        }
    }
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
    }

    javadoc {
        dependsOn(commonJava)
        source(commonJava)
    }

    named<Jar>("sourcesJar") {
        dependsOn(commonJava)
        from(commonJava)

        dependsOn(commonResources)
        from(commonResources)
    }
}