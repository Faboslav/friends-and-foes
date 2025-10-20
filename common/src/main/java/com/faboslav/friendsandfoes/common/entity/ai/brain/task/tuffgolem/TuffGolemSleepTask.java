package com.faboslav.friendsandfoes.common.entity.ai.brain.task.tuffgolem;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.TuffGolemBrain;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.util.MovementUtil;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public final class TuffGolemSleepTask extends Behavior<TuffGolemEntity>
{
	private final static int MIN_TICKS_TO_SLEEP = 6000;
	private final static int MAX_TICKS_TO_SLEEP = 12000;

	public TuffGolemSleepTask() {
		super(ImmutableMap.of(
			FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT
		), MIN_TICKS_TO_SLEEP, MAX_TICKS_TO_SLEEP);
	}

	@Override
	protected boolean checkExtraStartConditions(
		ServerLevel world,
		TuffGolemEntity tuffGolem
	) {
		return tuffGolem.isAtHome()
			   && tuffGolem.getCurrentAnimationTick() == 0
			   && tuffGolem.getBrain().getMemoryInternal(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get()).isEmpty();
	}

	@Override
	protected void start(
		ServerLevel world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		tuffGolem.setPos(tuffGolem.getHomePos());
		tuffGolem.setSpawnYaw(tuffGolem.getHomeYaw());

		if (tuffGolem.isInEntityPose(FriendsAndFoesEntityPose.STANDING)) {
			tuffGolem.startSleeping();
		} else if (tuffGolem.isInEntityPose(FriendsAndFoesEntityPose.STANDING_WITH_ITEM)) {
			tuffGolem.startSleepingWithItem();
		}
	}

	@Override
	protected boolean canStillUse(
		ServerLevel world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		return tuffGolem.isInSleepingPose() && tuffGolem.getBrain().getMemoryInternal(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get()).isEmpty();
	}

	@Override
	protected void stop(
		ServerLevel world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		TuffGolemBrain.setSleepCooldown(tuffGolem);
		MovementUtil.stopMovement(tuffGolem);

		if (tuffGolem.isInEntityPose(FriendsAndFoesEntityPose.SLEEPING)) {
			tuffGolem.startStanding();

			if (tuffGolem.isInSleepingPose()) {
				tuffGolem.playWakeSound();
			} else {
				tuffGolem.playMoveSound();
			}
		} else if (tuffGolem.isInEntityPose(FriendsAndFoesEntityPose.SLEEPING_WITH_ITEM)) {
			tuffGolem.startStandingWithItem();

			if (tuffGolem.isInSleepingPose()) {
				tuffGolem.playWakeSound();
			} else {
				tuffGolem.playMoveSound();
			}
		}
	}
}
