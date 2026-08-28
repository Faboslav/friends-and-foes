package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;

import net.minecraft.resources.ResourceKey;

import net.minecraft.world.level.storage.loot.LootTable;

//? if >= 1.21.1 {
import net.minecraft.core.registries.Registries;
//?} else {
/*import net.minecraft.resources.Identifier;
*///?}

public final class FriendsAndFoesLootTables {
	//? if >= 1.21.1 {
	public static final ResourceKey<LootTable> BEEKEEPER_GIFT = ResourceKey.create(
		Registries.LOOT_TABLE,
		FriendsAndFoes.makeID("gameplay/hero_of_the_village/beekeeper_gift")
	);
	//?} else {
	/*public static final Identifier BEEKEEPER_GIFT = FriendsAndFoes.makeID("gameplay/hero_of_the_village/beekeeper_gift");
	*///?}

	private FriendsAndFoesLootTables() {
	}
}