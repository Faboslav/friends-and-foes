package com.faboslav.friendsandfoes.common.entity.ai.brain.sensor;

import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.google.common.collect.ImmutableSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.Set;

public class BarnacleSpecificSensor extends Sensor<BarnacleEntity>
{
	private static final float AVOID_TARGET_DETECTION_DISTANCE = 16.0F;
	private static final UniformInt AVOID_MEMORY_DURATION;

	static {
		AVOID_MEMORY_DURATION = TimeUtil.rangeOfSeconds(5, 10);
	}

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(MemoryModuleType.AVOID_TARGET);
	}

	@Override
	protected void doTick(ServerLevel world, BarnacleEntity barnacle) {
		Brain<?> brain = barnacle.getBrain();
		NearestVisibleLivingEntities livingTargetCache = brain.getMemoryInternal(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty());
		LivingEntity avoidTarget = livingTargetCache.findClosest(livingEntity -> isAvoidTarget(barnacle, livingEntity)).orElse(null);

		if (avoidTarget == null) {
			return;
		}

		brain.setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, avoidTarget, AVOID_MEMORY_DURATION.sample(barnacle.getRandom()));
	}

	private static boolean isAvoidTarget(BarnacleEntity barnacle, LivingEntity livingEntity) {
		return VersionedEntity.isEntityType(livingEntity, FriendsAndFoesTags.BARNACLE_AVOID_TARGETS)
			   && livingEntity.distanceToSqr(barnacle) <= AVOID_TARGET_DETECTION_DISTANCE * AVOID_TARGET_DETECTION_DISTANCE;
	}
}
