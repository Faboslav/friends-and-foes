package com.faboslav.friendsandfoes.fabric.platform;

import net.fabricmc.loader.api.FabricLoader;

//? if <1.21.3 {
/*import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;
*///?}

public final class PlatformHelper implements com.faboslav.friendsandfoes.common.platform.PlatformHelper
{
	@Override
	public boolean isModLoaded(String modId) {
		return FabricLoader.getInstance().isModLoaded(modId);
	}

	//? if <1.21.3 {
	/*@Override
	public SpawnEggItem createSpawnEggItem(
		Supplier<? extends EntityType<? extends Mob>> type,
		int backgroundColor,
		int highlightColor,
		Item.Properties properties
	) {
		return new SpawnEggItem(type.get(), backgroundColor, highlightColor, properties);
	}
	*///?}
}
