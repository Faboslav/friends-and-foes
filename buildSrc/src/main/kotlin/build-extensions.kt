import dev.kikugie.stonecutter.build.StonecutterBuild
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.*

val Project.mod: ModData get() = ModData(this)
fun Project.prop(key: String): String? = findProperty(key)?.toString()
fun String.upperCaseFirst() = replaceFirstChar { if (it.isLowerCase()) it.uppercaseChar() else it }

fun RepositoryHandler.strictMaven(url: String, alias: String, vararg groups: String) = exclusiveContent {
    forRepository { maven(url) { name = alias } }
    filter { groups.forEach(::includeGroup) }
}

val Project.stonecutterBuild get() = extensions.getByType<StonecutterBuild>()

val Project.common get() = requireNotNull(stonecutterBuild.node.sibling("")) {
    "No common project for $project"
}
val Project.commonProject get() = common.project
val Project.commonMod get() = commonProject.mod

val Project.loader: String? get() = prop("loader")

val Project.extraProcessResourceKeys: MutableMap<String, String?>
    get() {
        if (extra.has("extraProcessResourceKeys")) {
            return extra["extraProcessResourceKeys"] as MutableMap<String, String?>
        } else {
            extra["extraProcessResourceKeys"] = mutableMapOf<String, String?>()
            return extraProcessResourceKeys
        }
    }

@JvmInline
value class ModData(private val project: Project) {
    val id: String get() = modProp("id")
    val name: String get() = modProp("name")
    val version: String get() = modProp("version")
    val group: String get() = modProp("group")
    val author: String get() = modProp("author")
    val description: String get() = modProp("description")
    val license: String get() = modProp("license")
    val mc: String get() = depOrNull("minecraft") ?: project.stonecutterBuild.current.version

    fun propOrNull(key: String) = project.prop(key)
    fun prop(key: String) = requireNotNull(propOrNull(key)) { "Missing '$key'" }
    fun modPropOrNull(key: String) = project.prop("mod.$key")
    fun modProp(key: String) = requireNotNull(modPropOrNull(key)) { "Missing 'mod.$key'" }
    fun depOrNull(key: String) = project.prop("deps.$key")
    fun dep(key: String) = requireNotNull(depOrNull(key)) { "Missing 'deps.$key'" }
}