package com.faboslav.friendsandfoes.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Map;
import net.minecraft.world.entity.ai.behavior.GiveGiftToHero;
import net.minecraft.world.entity.npc.villager.VillagerProfession;

//? if >= 1.21.1 {
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
//?} else {
/*import net.minecraft.resources.Identifier;
*///?}

@Mixin(GiveGiftToHero.class)
public interface GiveGiftToHeroAccessor
{
	//? if >= 1.21.5 {
	@Accessor("GIFTS")
	static Map<ResourceKey<VillagerProfession>, ResourceKey<LootTable>> friendsandfoes$getGifts() {
		throw new AssertionError();
	}

	@Mutable
	@Accessor("GIFTS")
	static void friendsandfoes$setGifts(Map<ResourceKey<VillagerProfession>, ResourceKey<LootTable>> gifts) {
		throw new AssertionError();
	}
	//?} else if >= 1.21.1 {
	/*@Accessor("GIFTS")
	static Map<VillagerProfession, ResourceKey<LootTable>> friendsandfoes$getGifts() {
		throw new AssertionError();
	}

	@Mutable
	@Accessor("GIFTS")
	static void friendsandfoes$setGifts(Map<VillagerProfession, ResourceKey<LootTable>> gifts) {
		throw new AssertionError();
	}
	*///?} else {
	/*@Accessor("GIFTS")
	static Map<VillagerProfession, Identifier> friendsandfoes$getGifts() {
		throw new AssertionError();
	}

	@Mutable
	@Accessor("GIFTS")
	static void friendsandfoes$setGifts(Map<VillagerProfession, Identifier> gifts) {
		throw new AssertionError();
	}
	*///?}
}
