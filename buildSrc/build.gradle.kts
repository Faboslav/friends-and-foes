import java.util.Properties

plugins {
	`kotlin-dsl`
	kotlin("jvm") version "2.2.0"
}

repositories {
	mavenCentral()
	gradlePluginPortal()
	maven("https://maven.kikugie.dev/snapshots")
}

val rootProps = Properties().apply {
	rootDir.parentFile.resolve("gradle.properties").inputStream().use(::load)
}

val stonecutterVersion: String = rootProps.getProperty("stonecutter_version")

dependencies {
	implementation("dev.kikugie:stonecutter:$stonecutterVersion")
}