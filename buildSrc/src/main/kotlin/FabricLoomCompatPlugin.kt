import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.named

open class FabricLoomCompatPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = with(target) {
		val current = the<StonecutterBuildExtension>().current.parsed

		if (current > "26.0") {
			setupNewLoomFacade()
		} else {
			setupOldLoomFacade()
		}

		extensions.create<FabricExtensions>("fabric", this, current > "26.0")
	}

	private fun Project.setupNewLoomFacade() {
		plugins.apply("net.fabricmc.fabric-loom")

		val names = listOf(
			"api", "implementation", "compileOnly", "runtimeOnly", "localRuntime"
		)

		for (baseName in names) {
			val loomified = "mod" + baseName.replaceFirstChar(Char::uppercaseChar)
			val modConfiguration = configurations.findByName(loomified) ?: configurations.create(loomified)

			configurations.getByName(baseName).extendsFrom(modConfiguration)
		}

		configurations.findByName("mappings") ?: configurations.register("mappings") {
			isCanBeResolved = false
			isCanBeConsumed = false
		}
	}

	private fun Project.setupOldLoomFacade() {
		plugins.apply("net.fabricmc.fabric-loom-remap")
	}

	open class FabricExtensions(val project: Project, val isNew: Boolean) {
		val modJar: TaskProvider<Jar> by lazy {
			if (isNew) project.tasks.named<Jar>("jar")
			else project.tasks.named<Jar>("remapJar")
		}

		val modSourcesJar: TaskProvider<Jar> by lazy {
			if (isNew) project.tasks.named<Jar>("sourcesJar")
			else project.tasks.named<Jar>("remapSourcesJar")
		}
	}
}