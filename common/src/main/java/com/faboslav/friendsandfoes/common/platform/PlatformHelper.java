package com.faboslav.friendsandfoes.common.platform;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public interface PlatformHelper {
	boolean isModLoaded(String modId);

	//? if <1.21.3 {
	/*SpawnEggItem createSpawnEggItem(
		Supplier<? extends EntityType<? extends Mob>> type,
		int backgroundColor,
		int highlightColor,
		Item.Properties properties
	);
	*///?}
}
