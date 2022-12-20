package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.mixin.IllusionerEntityMixin;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.nbt.NbtCompound;

/**
 * @see IllusionerEntityMixin
 */
public interface IllusionerEntityAccess
{
	void initGoals();

	void tick();

	void tickMovement();

	void initDataTracker();

	void writeCustomDataToNbt(NbtCompound nbt);

	void readCustomDataFromNbt(NbtCompound nbt);

	void setIllusioner(IllusionerEntity illusionerEntity);

	void setIsIllusion(boolean isIllusion);

	void setTicksUntilDespawn(int ticksUntilDespawn);

	int getAliveIllusionsCount();

	void setAliveIllusionsCount(int aliveIllusionsCount);

	boolean tryToTeleport(int x, int y, int z);

	void spawnCloudParticles();
}
