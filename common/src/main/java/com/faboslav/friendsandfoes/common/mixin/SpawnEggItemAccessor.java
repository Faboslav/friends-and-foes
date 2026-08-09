package com.faboslav.friendsandfoes.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.item.SpawnEggItem;

import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Map;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

@Mixin(SpawnEggItem.class)
public interface SpawnEggItemAccessor
{

	@Accessor("BY_ID")
	static Map<EntityType<? extends Mob>, SpawnEggItem> friendsandfoes$getSpawnEggs() {
		throw new AssertionError();
	}

}
