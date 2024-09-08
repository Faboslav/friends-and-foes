package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;

import java.util.Map;

public final class CrabTravelToBurrowSpotTask extends MultiTickTask<CrabEntity>
{
	private static final int MAX_TRAVELLING_TICKS = 600;
	private final static float WITHING_DISTANCE = 1.5F;

	public CrabTravelToBurrowSpotTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryModuleState.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryModuleState.VALUE_PRESENT
		), MAX_TRAVELLING_TICKS);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, CrabEntity crab) {
		GlobalPos burrowSpotPos = crab.getBurrowSpotPos();

		if (burrowSpotPos == null) {
			return false;
		}

		return true;
	}

	@Override
	protected void run(ServerWorld world, CrabEntity crab, long time) {
		this.walkTowardsBurrowSpot(crab);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, CrabEntity crab, long time) {
		GlobalPos burrowSpotPos = crab.getBurrowSpotPos();

		if (burrowSpotPos == null || !crab.isBurrowSpotAccessible(burrowSpotPos.getPos())) {
			return false;
		}

		return true;
	}

	protected void keepRunning(ServerWorld world, CrabEntity crab, long time) {
		if (crab.getNavigation().isFollowingPath()) {
			return;
		}

		this.walkTowardsBurrowSpot(crab);
	}

	@Override
	protected void finishRunning(ServerWorld world, CrabEntity crab, long time) {
		crab.getBrain().forget(MemoryModuleType.WALK_TARGET);
		GlobalPos burrowSpotPos = crab.getBurrowSpotPos();

		if (
			burrowSpotPos != null &&
			(
				burrowSpotPos.getPos().isWithinDistance(crab.getPos(), WITHING_DISTANCE) == false
				|| crab.isBurrowSpotAccessible(burrowSpotPos.getPos()) == false
			)
		) {
			crab.setHasEgg(false);
			crab.setLoveTicks(600);
			crab.getBrain().forget(FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get());
		}
	}

	private void walkTowardsBurrowSpot(CrabEntity crab) {
		GlobalPos burrowSpotPos = crab.getBrain().getOptionalMemory(FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get()).orElse(null);

		if (burrowSpotPos == null) {
			return;
		}

		LookTargetUtil.walkTowards(
			crab,
			new BlockPos(burrowSpotPos.getPos()),
			0.6F,
			0
		);
	}
}
