plugins {
	`kotlin-dsl`
	kotlin("jvm") version "2.2.0"
}

repositories {
	mavenCentral()
	gradlePluginPortal()
	maven("https://maven.kikugie.dev/snapshots")
}

dependencies {
	fun plugin(id: String, version: String) = "$id:$id.gradle.plugin:$version"

	implementation("dev.kikugie:stonecutter:0.7-beta.6")
}