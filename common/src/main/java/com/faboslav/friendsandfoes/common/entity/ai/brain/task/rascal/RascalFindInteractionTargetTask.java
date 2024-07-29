package com.faboslav.friendsandfoes.common.entity.ai.brain.task.rascal;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.function.Predicate;

public class RascalFindInteractionTargetTask extends Task<LivingEntity>
{
	private final int maxSquaredDistance;
	private final Predicate<LivingEntity> predicate;
	private final Predicate<LivingEntity> shouldRunPredicate;

	public RascalFindInteractionTargetTask(
		int maxDistance,
		Predicate<LivingEntity> shouldRunPredicate,
		Predicate<LivingEntity> predicate
	) {
		super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED, MemoryModuleType.INTERACTION_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleState.VALUE_PRESENT));
		this.maxSquaredDistance = maxDistance * maxDistance;
		this.predicate = predicate;
		this.shouldRunPredicate = shouldRunPredicate;
	}

	public RascalFindInteractionTargetTask(int maxDistance) {
		this(maxDistance, (livingEntity) -> {
			return true;
		}, (livingEntity) -> {
			return true;
		});
	}

	public boolean shouldRun(ServerWorld world, LivingEntity entity) {
		return this.shouldRunPredicate.test(entity) && this.getVisibleMobs(entity).anyMatch(this::test);
	}

	public void run(ServerWorld world, LivingEntity entity, long time) {
		super.run(world, entity, time);
		Brain<?> brain = entity.getBrain();
		brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).flatMap((livingTargetCache) -> {
			return livingTargetCache.findFirst((target) -> {
				return target.squaredDistanceTo(entity) <= (double) this.maxSquaredDistance && target instanceof PlayerEntity && !target.isSpectator() && !((PlayerEntity) target).isCreative();
			});
		}).ifPresent((target) -> {
			brain.remember(MemoryModuleType.INTERACTION_TARGET, target);
			brain.remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(target, true));
		});
	}

	private boolean test(LivingEntity entity) {
		return EntityType.PLAYER.equals(entity.getType()) && this.predicate.test(entity);
	}

	private LivingTargetCache getVisibleMobs(LivingEntity entity) {
		return entity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).get();
	}
}
