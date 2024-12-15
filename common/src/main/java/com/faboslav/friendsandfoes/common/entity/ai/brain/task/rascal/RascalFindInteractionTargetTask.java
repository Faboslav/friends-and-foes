package com.faboslav.friendsandfoes.common.entity.ai.brain.task.rascal;

import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;

public final class RascalFindInteractionTargetTask
{
	public static BehaviorControl<LivingEntity> create(int maxDistance) {
		int squaredMaxDistance = maxDistance * maxDistance;
		return BehaviorBuilder.create((context) -> {
			return context.group(context.registered(MemoryModuleType.LOOK_TARGET), context.absent(MemoryModuleType.INTERACTION_TARGET), context.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)).apply(context, (lookTarget, interactionTarget, visibleMobs) -> {
				return (world, entity, time) -> {
					Optional<LivingEntity> optional = context.get(visibleMobs).findClosest((target) -> {
						return target.distanceToSqr(entity) <= (double) squaredMaxDistance && target instanceof Player && !target.isSpectator() && !((Player) target).isCreative();
					});

					if (optional.isEmpty()) {
						return false;
					} else {
						LivingEntity livingEntity = optional.get();
						interactionTarget.set(livingEntity);
						lookTarget.set(new EntityTracker(livingEntity, true));
						return true;
					}
				};
			});
		});
	}
}
