package com.faboslav.friendsandfoes.common.util;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.Vec3;

public final class MovementUtil
{
	public static void stopMovement(Mob entity) {
		entity.setJumping(false);
		entity.setXxa(0.0F);
		entity.setYya(0.0F);
		entity.setSpeed(0.0F);

		//? >=1.21.3 {
		entity.walkAnimation.stop();
		//?} else {
		/*entity.walkAnimation.update(0.0F, 0.0F);
		entity.walkDistO = 0.0F;
		entity.walkDist = 0.0F;
		*///?}

		entity.getBrain().eraseMemory(MemoryModuleType.PATH);
		entity.getBrain().eraseMemory(MemoryModuleType.AVOID_TARGET);
		entity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		entity.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);

		entity.getMoveControl().setWantedPosition(entity.getX(), entity.getY(), entity.getZ(), 0);
		entity.getMoveControl().tick();
		entity.getLookControl().setLookAt(entity.getLookControl().getWantedX(), entity.getLookControl().getWantedY(), entity.getLookControl().getWantedZ());
		entity.getLookControl().setLookAt(Vec3.ZERO);
		entity.getLookControl().tick();
		entity.getNavigation().setSpeedModifier(0);
		entity.getNavigation().stop();
	}
}
