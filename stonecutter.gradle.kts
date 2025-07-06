plugins {
    id("dev.kikugie.stonecutter")

    id("net.neoforged.moddev") version "2.0.78" apply false
    id("fabric-loom") version "1.10-SNAPSHOT" apply false
}

stonecutter active "1.21.6" /* [SC] DO NOT EDIT */

stonecutter {
	parameters {
		const("trinkets", false)
		const("curios", false)
	}
}