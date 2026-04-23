package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesLootTables;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesVillagerProfessions;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.behavior.GiveGiftToHero;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import java.util.HashMap;
import java.util.Map;

//? if >= 1.21.4 {
import com.google.common.collect.ImmutableMap;
//?}

@Mixin(GiveGiftToHero.class)
public abstract class GiveGiftToHeroMixin
{
	//? if >= 1.21.4 {
	@ModifyExpressionValue(
		method = "<clinit>",
		at = @At(
			value = "INVOKE",
			target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"
		)
	)
	private static ImmutableMap<ResourceKey<VillagerProfession>, ResourceKey<LootTable>> friendsandfoes$addBeekeeperGift(ImmutableMap<ResourceKey<VillagerProfession>, ResourceKey<LootTable>> original)
	{
		Map<ResourceKey<VillagerProfession>, ResourceKey<LootTable>> map = new HashMap<>(original);
		map.put(FriendsAndFoesVillagerProfessions.BEEKEEPER_KEY, FriendsAndFoesLootTables.BEEKEEPER_GIFT);

		return ImmutableMap.copyOf(map);
	}
	//?} else {
	/*@SuppressWarnings("unchecked")
	@ModifyExpressionValue(
		method = "<clinit>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/Util;make(Ljava/lang/Object;Ljava/util/function/Consumer;)Ljava/lang/Object;"
		)
	)
	private static Object friendsandfoes$addBeekeeperGift(Object original) {
		Map<VillagerProfession, ResourceKey<LootTable>> map = new HashMap<>((Map<VillagerProfession, ResourceKey<LootTable>>) original);
		map.put(FriendsAndFoesVillagerProfessions.BEEKEEPER.get(), FriendsAndFoesLootTables.BEEKEEPER_GIFT);
		return map;
	}
	*///?}
}