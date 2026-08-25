package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;

/**
 * @see net.minecraft.world.level.saveddata.maps.MapDecorationTypes
 */
public final class FriendsAndFoesMapDecorationTypes
{
	public static final ResourcefulRegistry<MapDecorationType> MAP_DECORATION_TYPES = ResourcefulRegistries.create(BuiltInRegistries.MAP_DECORATION_TYPE, FriendsAndFoes.MOD_ID);

	public static final HolderRegistryEntry<MapDecorationType> CITADEL = MAP_DECORATION_TYPES.registerHolder("citadel", () -> new MapDecorationType(
		FriendsAndFoes.makeID("citadel"),
		true,
		MapDecorationType.NO_MAP_COLOR,
		true,
		false
	));
}
