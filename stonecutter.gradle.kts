
plugins {
    id("dev.kikugie.stonecutter")

    id("net.neoforged.moddev") version "2.0.78" apply false
    id("fabric-loom") version "1.10-SNAPSHOT" apply false
}

stonecutter active "1.21.5" /* [SC] DO NOT EDIT */

stonecutter {
	//constants["trinkets"] = false

	parameters {
		constants["trinkets"] = false;
		constants["curios"] = false;
	}
}