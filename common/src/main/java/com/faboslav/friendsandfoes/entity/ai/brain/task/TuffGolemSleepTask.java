package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.TuffGolemBrain;
import com.faboslav.friendsandfoes.entity.pose.TuffGolemEntityPose;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class TuffGolemSleepTask extends Task<TuffGolemEntity>
{
	private final static int MIN_TICKS_TO_SLEEP = 200;
	//private final static int MIN_TICKS_TO_SLEEP = 1200;
	private final static int MAX_TICKS_TO_SLEEP = 400;
	//private final static int MAX_TICKS_TO_SLEEP = 3600;

	public TuffGolemSleepTask() {
		super(ImmutableMap.of(
			FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), MIN_TICKS_TO_SLEEP, MAX_TICKS_TO_SLEEP);
	}

	@Override
	protected boolean shouldRun(
		ServerWorld world,
		TuffGolemEntity tuffGolem
	) {
		if (tuffGolem.isAtHomePos() == false) {
			return false;
		}

		FriendsAndFoes.getLogger().info("TuffGolemSleepTask true");
		return true;
	}

	@Override
	protected void run(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		tuffGolem.getBrain().forget(MemoryModuleType.LOOK_TARGET);
		tuffGolem.getBrain().forget(MemoryModuleType.WALK_TARGET);

		tuffGolem.setSpawnYaw(tuffGolem.getHomeYaw());

		tuffGolem.getNavigation().setSpeed(0);
		tuffGolem.getNavigation().stop();
		tuffGolem.getMoveControl().moveTo(tuffGolem.getX(), tuffGolem.getY(), tuffGolem.getZ(), 0);
		tuffGolem.getMoveControl().tick();
		tuffGolem.getLookControl().lookAt(tuffGolem.getLookControl().getLookX(), tuffGolem.getLookControl().getLookY(), tuffGolem.getLookControl().getLookZ());
		tuffGolem.getLookControl().tick();

		tuffGolem.setJumping(false);
		tuffGolem.setMovementSpeed(0.0F);
		tuffGolem.prevHorizontalSpeed = 0.0F;
		tuffGolem.horizontalSpeed = 0.0F;
		tuffGolem.sidewaysSpeed = 0.0F;
		tuffGolem.upwardSpeed = 0.0F;
		tuffGolem.setVelocity(Vec3d.ZERO);
		tuffGolem.velocityDirty = true;

		if (tuffGolem.isInPose(TuffGolemEntityPose.STANDING.get())) {
			FriendsAndFoes.getLogger().info("start startSleeping");
			tuffGolem.startSleeping();
		} else if (tuffGolem.isInPose(TuffGolemEntityPose.STANDING_WITH_ITEM.get())) {
			FriendsAndFoes.getLogger().info("start startSleepingWithItem");
			tuffGolem.startSleepingWithItem();
		}
	}

	@Override
	protected boolean shouldKeepRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		return true;
	}

	@Override
	protected void finishRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		FriendsAndFoes.getLogger().info("stop sleeping");
		TuffGolemBrain.setSleepCooldown(tuffGolem);

		if (tuffGolem.isInPose(TuffGolemEntityPose.SLEEPING.get())) {
			tuffGolem.startStanding();
		} else if (tuffGolem.isInPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM.get())) {
			tuffGolem.startStandingWithItem();
		}
	}
}
