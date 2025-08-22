package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AnimalMakeLove.class)
public interface AnimalMakeLoveAccessor
{
	@Invoker("getBreedTarget")
	Animal friendsandfoes$getBreedTarget(Animal animal);

	@Accessor("spawnChildAtTime")
	long friendsandfoes$getSpawnChildAtTime();
}
