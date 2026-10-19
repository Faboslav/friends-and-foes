package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.entity.ai.behavior.GiveGiftToHero;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

//? if >= 1.21.1 {
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
//?} else {
/*import net.minecraft.resources.Identifier;
*///?}

@Mixin(GiveGiftToHero.class)
public interface GiveGiftToHeroAccessor
{
	@Accessor("GIFTS")
	//? if >= 1.21.5 {
	static Map<ResourceKey<VillagerProfession>, ResourceKey<LootTable>> friendsandfoes$getGifts()
	//?} else if >= 1.21.1 {
	/*static Map<VillagerProfession, ResourceKey<LootTable>> friendsandfoes$getGifts()
	*///?} else {
	/*static Map<VillagerProfession, Identifier> friendsandfoes$getGifts()
	*///?}
	{
		throw new AssertionError();
	}

	@Mutable
	@Accessor("GIFTS")
	//? if >= 1.21.5 {
	static void friendsandfoes$setGifts(Map<ResourceKey<VillagerProfession>, ResourceKey<LootTable>> gifts)
	//?} else if >= 1.21.1 {
	/*static void friendsandfoes$setGifts(Map<VillagerProfession, ResourceKey<LootTable>> gifts)
	*///?} else {
	/*static void friendsandfoes$setGifts(Map<VillagerProfession, Identifier> gifts)
	*///?}
	{
		throw new AssertionError();
	}
}
