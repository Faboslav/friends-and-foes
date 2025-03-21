package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.task.BreedTask;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.world.ServerWorld;

public class CrabBreedTask extends BreedTask
{
	public CrabBreedTask(EntityType<? extends AnimalEntity> targetType) {
		super(targetType);
	}

	@Override
	protected void finishRunning(ServerWorld serverWorld, AnimalEntity animalEntity, long time) {
		super.finishRunning(serverWorld, animalEntity, time);
		((CrabEntity) animalEntity).setHasEgg(true);
	}
}
