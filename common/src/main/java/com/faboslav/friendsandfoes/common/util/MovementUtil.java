package com.faboslav.friendsandfoes.common.util;

import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;

public final class MovementUtil
{
	public static void stopMovement(MobEntity entity) {
		entity.getBrain().forget(MemoryModuleType.AVOID_TARGET);
		entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
		entity.getBrain().forget(MemoryModuleType.LOOK_TARGET);

		entity.getNavigation().setSpeed(0);
		entity.getNavigation().stop();
		entity.getMoveControl().moveTo(entity.getX(), entity.getY(), entity.getZ(), 0);
		entity.getMoveControl().tick();
		entity.getLookControl().lookAt(entity.getLookControl().getLookX(), entity.getLookControl().getLookY(), entity.getLookControl().getLookZ());
		entity.getLookControl().lookAt(Vec3d.ZERO);
		entity.getLookControl().tick();

		entity.setJumping(false);
		entity.setMovementSpeed(0.0F);
		entity.prevHorizontalSpeed = 0.0F;
		entity.horizontalSpeed = 0.0F;
		entity.sidewaysSpeed = 0.0F;
		entity.upwardSpeed = 0.0F;
	}
}
