package com.faboslav.friendsandfoes.common.entity.ai.brain.task.tuffgolem;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public final class TuffGolemGoToHomePositionTask extends Task<TuffGolemEntity>
{
	private final static int GO_TO_SLEEP_POSITION_DURATION = 2400;

	public TuffGolemGoToHomePositionTask() {
		super(ImmutableMap.of(
			MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_ABSENT,
			MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT,
			FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), GO_TO_SLEEP_POSITION_DURATION);
	}

	@Override
	protected boolean shouldRun(
		ServerWorld world,
		TuffGolemEntity tuffGolem
	) {
		return !tuffGolem.isInSleepingPose()
			   && !tuffGolem.isAtHomePos()
			   && !tuffGolem.isGlued()
			   && !tuffGolem.isLeashed()
			   && !tuffGolem.hasVehicle()
			   && tuffGolem.getBrain().getOptionalMemory(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get()).isEmpty();
	}

	@Override
	protected void run(
		ServerWorld serverWorld,
		TuffGolemEntity tuffGolem,
		long l
	) {
		this.walkTowardsHomePos(tuffGolem);
	}

	@Override
	protected boolean shouldKeepRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		return !tuffGolem.isAtHome()
			   && !tuffGolem.isGlued()
			   && !tuffGolem.isLeashed()
			   && !tuffGolem.hasVehicle()
			   && tuffGolem.getBrain().getOptionalMemory(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get()).isEmpty();
	}

	@Override
	protected void keepRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		if (tuffGolem.getNavigation().isIdle()) {
			this.walkTowardsHomePos(tuffGolem);
		}

		if (
			tuffGolem.isCloseToHomePos(2.0F) == false
			|| Math.abs((int) tuffGolem.getY() - (int) tuffGolem.getHomePos().getY()) > 1
		) {
			return;
		}

		tuffGolem.setVelocity(
			new Vec3d(
				(tuffGolem.getHomePos().getX() - tuffGolem.getX()),
				(tuffGolem.getHomePos().getY() - tuffGolem.getY()),
				(tuffGolem.getHomePos().getZ() - tuffGolem.getZ())
			)
		);
		tuffGolem.setSpawnYaw(tuffGolem.getHomeYaw());
	}

	@Override
	protected void finishRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		if (tuffGolem.isCloseToHomePos(1.5F)) {
			tuffGolem.setPosition(tuffGolem.getHomePos());
		}

		tuffGolem.getBrain().forget(MemoryModuleType.LOOK_TARGET);
		tuffGolem.getBrain().forget(MemoryModuleType.WALK_TARGET);
		tuffGolem.stopMovement();
	}

	private void walkTowardsHomePos(
		TuffGolemEntity tuffGolem
	) {
		LookTargetUtil.walkTowards(
			tuffGolem,
			new BlockPos(tuffGolem.getHomePos()),
			0.6F,
			0
		);
	}
}
