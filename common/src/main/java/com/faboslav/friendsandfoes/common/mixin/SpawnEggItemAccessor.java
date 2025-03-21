package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.SpawnEggItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SpawnEggItem.class)
public interface SpawnEggItemAccessor
{
	@Accessor("SPAWN_EGGS")
	static Map<EntityType<? extends MobEntity>, SpawnEggItem> variantsandventures$getSpawnEggs() {
		throw new AssertionError();
	}
}
