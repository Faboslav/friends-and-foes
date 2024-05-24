/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package com.faboslav.friendsandfoes.common.entity.ai.brain.sensor;

import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.Set;

public class BarnacleSpecificSensor extends Sensor<BarnacleEntity>
{
	private static final UniformIntProvider AVOID_MEMORY_DURATION;

	static {
		AVOID_MEMORY_DURATION = TimeHelper.betweenSeconds(5, 10);
	}

	@Override
	public Set<MemoryModuleType<?>> getOutputMemoryModules() {
		return ImmutableSet.of(MemoryModuleType.AVOID_TARGET);
	}

	@Override
	protected void sense(ServerWorld world, BarnacleEntity barnacle) {
		Brain<?> brain = barnacle.getBrain();
		LivingTargetCache livingTargetCache = brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).orElse(LivingTargetCache.empty());
		LivingEntity firstEntityToBeAvoided = livingTargetCache.findFirst(livingEntity -> livingEntity.getType().isIn(FriendsAndFoesTags.BARNACLE_AVOID_TARGETS)).orElse(null);
		LivingEntity firstEntityToBeHunted = livingTargetCache.findFirst(livingEntity -> livingEntity.getType().isIn(FriendsAndFoesTags.BARNACLE_PREY)).orElse(null);

		if (firstEntityToBeAvoided != null) {
			brain.remember(MemoryModuleType.AVOID_TARGET, firstEntityToBeAvoided, AVOID_MEMORY_DURATION.get(barnacle.getRandom()));
		}

		if (firstEntityToBeHunted != null) {
			//brain.remember(MemoryModuleType.ATTACK_TARGET, firstEntityToBeAvoided, AVOID_MEMORY_DURATION.get(barnacle.getRandom()));
		}
	}
}

