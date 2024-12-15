package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.animal.Animal;

public class CrabBreedTask extends AnimalMakeLove
{
	public CrabBreedTask(EntityType<? extends Animal> targetType) {
		super(targetType);
	}

	@Override
	protected void stop(ServerLevel serverWorld, Animal animalEntity, long time) {
		super.stop(serverWorld, animalEntity, time);
		((CrabEntity) animalEntity).setHasEgg(true);
	}
}
