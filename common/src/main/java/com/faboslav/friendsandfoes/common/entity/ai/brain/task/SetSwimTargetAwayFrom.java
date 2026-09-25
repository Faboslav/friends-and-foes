package com.faboslav.friendsandfoes.common.entity.ai.brain.task;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;

public final class SetSwimTargetAwayFrom
{
	public static OneShot<PathfinderMob> entity(
		MemoryModuleType<? extends Entity> memory,
		float speedModifier,
		int desiredDistance,
		boolean interruptCurrentWalk
	) {
		return create(memory, speedModifier, desiredDistance, interruptCurrentWalk);
	}

	private static <T extends Entity> OneShot<PathfinderMob> create(
		MemoryModuleType<T> avoidMemory,
		float speedModifier,
		int desiredDistance,
		boolean interruptCurrentWalk
	) {
		return BehaviorBuilder.create(context -> context.group(
			context.registered(MemoryModuleType.WALK_TARGET),
			context.present(avoidMemory)
		).apply(context, (walkTarget, avoidTarget) -> (world, mob, time) -> {
			Optional<WalkTarget> currentWalkTarget = context.tryGet(walkTarget);

			if (currentWalkTarget.isPresent() && !interruptCurrentWalk) {
				return false;
			}

			Vec3 mobPosition = mob.position();
			Vec3 avoidPosition = context.get(avoidTarget).position();

			if (!mobPosition.closerThan(avoidPosition, desiredDistance)) {
				return false;
			}

			if (currentWalkTarget.isPresent() && currentWalkTarget.get().getSpeedModifier() == speedModifier) {
				Vec3 currentDirection = currentWalkTarget.get().getTarget().currentPosition().subtract(mobPosition);
				Vec3 avoidDirection = avoidPosition.subtract(mobPosition);

				if (currentDirection.dot(avoidDirection) < 0.0D) {
					return false;
				}
			}

			for (int attempt = 0; attempt < 10; attempt++) {
				Vec3 fleePosition = DefaultRandomPos.getPosAway(mob, 16, 7, avoidPosition);

				if (fleePosition != null && world.getFluidState(BlockPos.containing(fleePosition)).is(FluidTags.WATER)) {
					walkTarget.set(new WalkTarget(fleePosition, speedModifier, 0));
					break;
				}
			}

			return true;
		}));
	}
}
