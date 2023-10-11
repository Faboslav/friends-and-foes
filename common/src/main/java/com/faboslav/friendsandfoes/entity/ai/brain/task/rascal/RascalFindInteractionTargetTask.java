package com.faboslav.friendsandfoes.entity.ai.brain.task.rascal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Optional;

public final class RascalFindInteractionTargetTask
{
	public static Task<LivingEntity> create(int maxDistance) {
		int squaredMaxDistance = maxDistance * maxDistance;
		return TaskTriggerer.task((context) -> {
			return context.group(context.queryMemoryOptional(MemoryModuleType.LOOK_TARGET), context.queryMemoryAbsent(MemoryModuleType.INTERACTION_TARGET), context.queryMemoryValue(MemoryModuleType.VISIBLE_MOBS)).apply(context, (lookTarget, interactionTarget, visibleMobs) -> {
				return (world, entity, time) -> {
					Optional<LivingEntity> optional = context.getValue(visibleMobs).findFirst((target) -> {
						return target.squaredDistanceTo(entity) <= (double) squaredMaxDistance && target instanceof PlayerEntity && !target.isSpectator() && !((PlayerEntity) target).isCreative();
					});

					if (optional.isEmpty()) {
						return false;
					} else {
						LivingEntity livingEntity = optional.get();
						interactionTarget.remember(livingEntity);
						lookTarget.remember(new EntityLookTarget(livingEntity, true));
						return true;
					}
				};
			});
		});
	}
}
