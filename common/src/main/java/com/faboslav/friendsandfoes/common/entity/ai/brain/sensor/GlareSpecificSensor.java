package com.faboslav.friendsandfoes.common.entity.ai.brain.sensor;

import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.monster.Monster;

public class GlareSpecificSensor extends Sensor<GlareEntity>
{
	private static final UniformInt AVOID_MEMORY_DURATION = TimeUtil.rangeOfSeconds(5, 10);

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(MemoryModuleType.AVOID_TARGET);
	}

	@Override
	protected void doTick(ServerLevel world, GlareEntity glare) {
		Brain<?> brain = glare.getBrain();
		NearestVisibleLivingEntities livingTargetCache = brain.getMemoryInternal(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty());
		LivingEntity firstHostileEntity = livingTargetCache.findClosest(livingEntity -> livingEntity instanceof Monster).orElse(null);

		if (firstHostileEntity == null || glare.isTame()) {
			return;
		}

		brain.setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, firstHostileEntity, AVOID_MEMORY_DURATION.sample(glare.getRandom()));
	}
}

