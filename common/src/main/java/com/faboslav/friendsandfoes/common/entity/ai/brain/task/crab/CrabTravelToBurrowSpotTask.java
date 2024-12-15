package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public final class CrabTravelToBurrowSpotTask extends Behavior<CrabEntity>
{
	private static final int MAX_TRAVELLING_TICKS = 600;
	private final static float WITHING_DISTANCE = 1.5F;

	public CrabTravelToBurrowSpotTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryStatus.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryStatus.VALUE_PRESENT
		), MAX_TRAVELLING_TICKS);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, CrabEntity crab) {
		GlobalPos burrowSpotPos = crab.getBurrowSpotPos();

		if (burrowSpotPos == null) {
			return false;
		}

		return true;
	}

	@Override
	protected void start(ServerLevel world, CrabEntity crab, long time) {
		this.walkTowardsBurrowSpot(crab);
	}

	@Override
	protected boolean canStillUse(ServerLevel world, CrabEntity crab, long time) {
		GlobalPos burrowSpotPos = crab.getBurrowSpotPos();

		if (burrowSpotPos == null || !crab.isBurrowSpotAccessible(burrowSpotPos.pos())) {
			return false;
		}

		return true;
	}

	protected void tick(ServerLevel world, CrabEntity crab, long time) {
		if (crab.getNavigation().isInProgress()) {
			return;
		}

		this.walkTowardsBurrowSpot(crab);
	}

	@Override
	protected void stop(ServerLevel world, CrabEntity crab, long time) {
		crab.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		GlobalPos burrowSpotPos = crab.getBurrowSpotPos();

		if (
			burrowSpotPos != null &&
			(
				burrowSpotPos.pos().closerToCenterThan(crab.position(), WITHING_DISTANCE) == false
				|| crab.isBurrowSpotAccessible(burrowSpotPos.pos()) == false
			)
		) {
			crab.setHasEgg(false);
			crab.setInLoveTime(600);
			crab.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get());
		}
	}

	private void walkTowardsBurrowSpot(CrabEntity crab) {
		GlobalPos burrowSpotPos = crab.getBrain().getMemoryInternal(FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get()).orElse(null);

		if (burrowSpotPos == null) {
			return;
		}

		BehaviorUtils.setWalkAndLookTargetMemories(
			crab,
			new BlockPos(burrowSpotPos.pos()),
			0.6F,
			0
		);
	}
}
