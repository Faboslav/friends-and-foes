package com.faboslav.friendsandfoes.api;

import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.nbt.NbtCompound;
import com.faboslav.friendsandfoes.mixin.IllusionerEntityMixin;

/**
 * @see IllusionerEntityMixin
 */
public interface IllusionerAccess
{
	void initGoals();

	void tick();

	void tickMovement();

	void initDataTracker();

	void writeCustomDataToNbt(NbtCompound nbt);

	void readCustomDataFromNbt(NbtCompound nbt);

	void setIllusionerEntity(IllusionerEntity illusionerEntity);

	void setIsIllusion(boolean isIllusion);

	void setTicksUntilDespawn(int ticksUntilDespawn);

	int getAliveIllusionsCount();

	void setAliveIllusionsCount(int aliveIllusionsCount);

	void spawnCloudParticles();
}
