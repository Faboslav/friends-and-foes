plugins {
	id("dev.kikugie.stonecutter")
	id("net.neoforged.moddev") version "2.0.107" apply false
	id("fabric-loom") version "1.11-SNAPSHOT" apply false
}

stonecutter active "1.21.8" /* [SC] DO NOT EDIT */

stonecutter {
	parameters {
		const("trinkets", false)
		const("curios", false)
	}
}