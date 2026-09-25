package com.faboslav.friendsandfoes.common.entity.ai.brain.task.barnacle;

import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.BarnacleBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import java.util.Map;

public final class BarnacleTravelToHidingSpotTask extends Behavior<BarnacleEntity>
{
	private static final int MAX_TRAVELLING_TICKS = 600;
	private static final float WITHIN_DISTANCE = 1.5F;

	public BarnacleTravelToHidingSpotTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.BARNACLE_HIDING_SPOT_POS.get(), MemoryStatus.VALUE_PRESENT,
			MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED,
			MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED,
			MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT,
			MemoryModuleType.AVOID_TARGET, MemoryStatus.VALUE_ABSENT
		), MAX_TRAVELLING_TICKS);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, BarnacleEntity barnacle) {
		GlobalPos hidingSpotPos = barnacle.getHidingSpotPos();

		return hidingSpotPos != null
			   && barnacle.isInWater()
			   && BarnacleEntity.isHidingSpot(world, hidingSpotPos.pos())
			   && !isAtHidingSpot(barnacle, hidingSpotPos);
	}

	@Override
	protected void start(ServerLevel world, BarnacleEntity barnacle, long time) {
		this.swimTowardsHidingSpot(barnacle);
	}

	@Override
	protected boolean canStillUse(ServerLevel world, BarnacleEntity barnacle, long time) {
		GlobalPos hidingSpotPos = barnacle.getHidingSpotPos();

		return hidingSpotPos != null
			   && barnacle.isInWater()
			   && !barnacle.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)
			   && !barnacle.getBrain().hasMemoryValue(MemoryModuleType.AVOID_TARGET)
			   && BarnacleEntity.isHidingSpot(world, hidingSpotPos.pos())
			   && !(isAtHidingSpot(barnacle, hidingSpotPos) && barnacle.getNavigation().isDone());
	}

	@Override
	protected void tick(ServerLevel world, BarnacleEntity barnacle, long time) {
		if (barnacle.getNavigation().isInProgress()) {
			return;
		}

		this.swimTowardsHidingSpot(barnacle);
	}

	@Override
	protected void stop(ServerLevel world, BarnacleEntity barnacle, long time) {
		barnacle.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		GlobalPos hidingSpotPos = barnacle.getHidingSpotPos();

		if (
			hidingSpotPos != null
			&& (
				!isAtHidingSpot(barnacle, hidingSpotPos)
				|| !BarnacleEntity.isHidingSpot(world, hidingSpotPos.pos())
			)
		) {
			barnacle.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.BARNACLE_HIDING_SPOT_POS.get());
			BarnacleBrain.setHidingSpotLocatingCooldown(barnacle);
		}
	}

	private void swimTowardsHidingSpot(BarnacleEntity barnacle) {
		GlobalPos hidingSpotPos = barnacle.getHidingSpotPos();

		if (hidingSpotPos == null) {
			return;
		}

		BehaviorUtils.setWalkAndLookTargetMemories(barnacle, hidingSpotPos.pos(), 0.6F, 1);
	}

	public static boolean isAtHidingSpot(BarnacleEntity barnacle, GlobalPos hidingSpotPos) {
		return hidingSpotPos.pos().closerToCenterThan(barnacle.position(), WITHIN_DISTANCE);
	}
}
