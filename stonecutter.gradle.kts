val IS_CI = System.getenv("CI") == "true"

plugins {
	id("dev.kikugie.stonecutter")
	id("net.neoforged.moddev") version "2.0.140" apply false
	id("net.fabricmc.fabric-loom") version "1.15-SNAPSHOT" apply false
	id("net.fabricmc.fabric-loom-remap") version "1.15-SNAPSHOT" apply false
}

stonecutter {
	parameters {
		constants["trinkets"] = false
		constants["curios"] = false

		filters.exclude("**/*.accesswidener")

		replacements.string(current.parsed >= "26.1") {
			replace("ServerWorldEvents", "ServerLevelEvents")
			replace("FabricTrackedDataRegistry", "FabricEntityDataRegistry")
			replace("EntityModelLayerRegistry", "ModelLayerRegistry")
			replace("FabricBrewingRecipeRegistryBuilder", "FabricPotionBrewingBuilder")
			replace("net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents", "net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents")
			replace("ItemGroupEvents.MODIFY_ENTRIES_ALL", "CreativeModeTabEvents.MODIFY_OUTPUT_ALL")
			replace("net.minecraft.client.renderer.state.CameraRenderState", "net.minecraft.client.renderer.state.level.CameraRenderState")
			replace("net.minecraft.world.entity.npc.villager.VillagerTrades", "net.minecraft.world.item.trading.VillagerTrade")
		}

		replacements.string(current.parsed < "1.21.11") {
			replace("net.minecraft.world.entity.npc.villager.VillagerTrades", "net.minecraft.world.entity.npc.VillagerTrades")
		}

		replacements.string(current.parsed >= "1.21.11") {
			replace("ResourceLocation", "Identifier")
			replace("net.minecraft.Util", "net.minecraft.util.Util")
			replace("AbstractIllager.IllagerArmPose", "IllagerArmPose")
			replace("net.minecraft.advancements.critereon", "net.minecraft.advancements.criterion")
			replace("net.minecraft.world.entity.animal.Pufferfish", "net.minecraft.world.entity.animal.fish.Pufferfish")
			replace("net.minecraft.world.entity.animal.Bee", "net.minecraft.world.entity.animal.bee.Bee")
			replace("net.minecraft.world.entity.animal.Rabbit", "net.minecraft.world.entity.animal.rabbit.Rabbit")
			replace("net.minecraft.world.entity.animal.Chicken", "net.minecraft.world.entity.animal.chicken.Chicken")
			replace("net.minecraft.world.entity.animal.Cow", "net.minecraft.world.entity.animal.cow.Cow")
			replace("net.minecraft.world.entity.animal.AbstractCow", "net.minecraft.world.entity.animal.cow.AbstractCow")
			replace("net.minecraft.world.entity.animal.AbstractGolem", "net.minecraft.world.entity.animal.golem.AbstractGolem")
			replace("net.minecraft.world.entity.animal.horse.AbstractHorse", "net.minecraft.world.entity.animal.equine.AbstractHorse")
			replace("net.minecraft.world.entity.animal.horse.ZombieHorse", "net.minecraft.world.entity.animal.equine.ZombieHorse")
			replace("net.minecraft.world.entity.GlowSquid", "net.minecraft.world.entity.animal.squid.GlowSquid")
			replace("net.minecraft.world.entity.animal.IronGolem", "net.minecraft.world.entity.animal.golem.IronGolem")
			replace("net.minecraft.world.entity.monster.SpellcasterIllager", "net.minecraft.world.entity.monster.illager.SpellcasterIllager")
			replace("net.minecraft.world.entity.monster.Zombie", "net.minecraft.world.entity.monster.zombie.Zombie")
			replace("net.minecraft.world.entity.monster.Drowned", "net.minecraft.world.entity.monster.zombie.Drowned")
			replace("net.minecraft.client.model.CowModel", "net.minecraft.client.model.animal.cow.CowModel")
			replace("net.minecraft.client.model.IllagerModel", "net.minecraft.client.model.monster.illager.IllagerModel")
			replace("net.minecraft.world.entity.npc.Villager", "net.minecraft.world.entity.npc.villager.Villager")
			replace("net.minecraft.world.entity.npc.AbstractVillager", "net.minecraft.world.entity.npc.villager.AbstractVillager")
			replace("net.minecraft.world.entity.projectile.AbstractArrow", "net.minecraft.world.entity.projectile.arrow.AbstractArrow")
			replace("net.minecraft.world.entity.projectile.ThrowableItemProjectile", "net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile")
			replace("net.minecraft.world.entity.projectile.Snowball", "net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball")
			replace("net.minecraft.world.entity.projectile.Fireball", "net.minecraft.world.entity.projectile.hurtingprojectile.Fireball")
			replace("net.minecraft.world.level.GameRules", "net.minecraft.world.level.gamerules.GameRules")
		}
	}
}

if (IS_CI) stonecutter active null
else stonecutter active "26.1.2" /* [SC] DO NOT EDIT */