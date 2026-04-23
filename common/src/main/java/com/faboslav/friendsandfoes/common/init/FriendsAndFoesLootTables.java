package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;

import net.minecraft.resources.ResourceKey;

import net.minecraft.world.level.storage.loot.LootTable;

public final class FriendsAndFoesLootTables {
	public static final ResourceKey<LootTable> BEEKEEPER_GIFT = ResourceKey.create(
		net.minecraft.core.registries.Registries.LOOT_TABLE,
		FriendsAndFoes.makeID("gameplay/hero_of_the_village/beekeeper_gift")
	);

	private FriendsAndFoesLootTables() {
	}
}