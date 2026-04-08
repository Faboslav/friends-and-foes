//? if >= 26.1 {
package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.TradeSet;

/**
 * @see net.minecraft.world.item.trading.TradeSets
 */
public class FriendsAndFoesTradeSets
{
	public static final ResourceKey<TradeSet> BEEKEEPER_LEVEL_1 = resourceKey("armorer/level_1");
	public static final ResourceKey<TradeSet> BEEKEEPER_LEVEL_2 = resourceKey("armorer/level_2");
	public static final ResourceKey<TradeSet> BEEKEEPER_LEVEL_3 = resourceKey("armorer/level_3");
	public static final ResourceKey<TradeSet> BEEKEEPER_LEVEL_4 = resourceKey("armorer/level_4");
	public static final ResourceKey<TradeSet> BEEKEEPER_LEVEL_5 = resourceKey("armorer/level_5");

	private static ResourceKey<TradeSet> resourceKey(final String path) {
		return ResourceKey.create(Registries.TRADE_SET, FriendsAndFoes.makeID(path));
	}

	public static void init() {

	}
}
//?}
