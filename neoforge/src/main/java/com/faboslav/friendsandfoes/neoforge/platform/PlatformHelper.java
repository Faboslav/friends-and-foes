package com.faboslav.friendsandfoes.neoforge.platform;

import net.neoforged.fml.ModList;

//? if <1.21.3 {
/*import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;
*///?}

@SuppressWarnings({"deprecation", "unchecked"})
public final class PlatformHelper implements com.faboslav.friendsandfoes.common.platform.PlatformHelper
{
	@Override
	public boolean isModLoaded(String modId) {
		return ModList.get().isLoaded(modId);
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
