package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.mixin.AnimalMakeLoveAccessor;
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
	protected void tick(ServerLevel level, Animal owner, long gameTime) {
		Animal animal = ((AnimalMakeLoveAccessor)this).friendsandfoes$getBreedTarget(owner);

		if (owner.closerThan(animal, 3.0F)) {
			if (gameTime >= ((AnimalMakeLoveAccessor)this).friendsandfoes$getSpawnChildAtTime()) {
				((CrabEntity) owner).setHasEgg(true);
				owner.setAge(6000);
				animal.setAge(6000);
				owner.resetLove();
				animal.resetLove();
			}

		}

		super.tick(level, owner, gameTime);
	}

}
