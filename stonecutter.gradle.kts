val IS_CI = System.getenv("CI") == "true"

plugins {
	id("dev.kikugie.stonecutter")
	id("net.neoforged.moddev") version "2.0.115" apply false
	id("fabric-loom") version "1.12.0-alpha.+" apply false
}

if (IS_CI) stonecutter active null
else stonecutter active "1.21.9" /* [SC] DO NOT EDIT */

stonecutter {
	parameters {
		constants["trinkets"] = false
		constants["curios"] = false
	}
}