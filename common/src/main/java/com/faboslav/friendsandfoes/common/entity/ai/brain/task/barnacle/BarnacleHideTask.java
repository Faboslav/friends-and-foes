package com.faboslav.friendsandfoes.common.entity.ai.brain.task.barnacle;

import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.BarnacleBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import java.util.Map;

public final class BarnacleHideTask extends Behavior<BarnacleEntity>
{
	private static final int MIN_HIDING_TICKS = 400;
	private static final int MAX_HIDING_TICKS = 1200;
	private static final float MAX_DRIFT_DISTANCE = 2.5F;

	public BarnacleHideTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.BARNACLE_HIDING_SPOT_POS.get(), MemoryStatus.VALUE_PRESENT,
			MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT,
			MemoryModuleType.AVOID_TARGET, MemoryStatus.VALUE_ABSENT
		), MIN_HIDING_TICKS, MAX_HIDING_TICKS);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, BarnacleEntity barnacle) {
		GlobalPos hidingSpotPos = barnacle.getHidingSpotPos();

		return hidingSpotPos != null
			   && barnacle.isInWater()
			   && BarnacleTravelToHidingSpotTask.isAtHidingSpot(barnacle, hidingSpotPos);
	}

	@Override
	protected void start(ServerLevel world, BarnacleEntity barnacle, long time) {
		barnacle.getNavigation().stop();
		barnacle.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
	}

	@Override
	protected boolean canStillUse(ServerLevel world, BarnacleEntity barnacle, long time) {
		GlobalPos hidingSpotPos = barnacle.getHidingSpotPos();

		return hidingSpotPos != null
			   && barnacle.isInWater()
			   && !barnacle.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)
			   && !barnacle.getBrain().hasMemoryValue(MemoryModuleType.AVOID_TARGET)
			   && hidingSpotPos.pos().closerToCenterThan(barnacle.position(), MAX_DRIFT_DISTANCE);
	}

	@Override
	protected void stop(ServerLevel world, BarnacleEntity barnacle, long time) {
		barnacle.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.BARNACLE_HIDING_SPOT_POS.get());
		BarnacleBrain.setHidingSpotLocatingCooldown(barnacle);
	}
}
