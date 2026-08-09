package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesLootTables;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesVillagerProfessions;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.behavior.GiveGiftToHero;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import java.util.HashMap;
import java.util.Map;

@Mixin(GiveGiftToHero.class)
public abstract class GiveGiftToHeroMixin
{

	@SuppressWarnings("unchecked")
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

}