package com.faboslav.friendsandfoes.forge.platform;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.ModList;

import java.util.function.Supplier;

public final class PlatformHelper implements com.faboslav.friendsandfoes.common.platform.PlatformHelper
{
	@Override
	public boolean isModLoaded(String modId) {
		return ModList.get().isLoaded(modId);
	}

	@Override
	public SpawnEggItem createSpawnEggItem(
		Supplier<? extends EntityType<? extends Mob>> type,
		int backgroundColor,
		int highlightColor,
		Item.Properties properties
	) {
		return new ForgeSpawnEggItem(type, backgroundColor, highlightColor, properties);
	}
}
