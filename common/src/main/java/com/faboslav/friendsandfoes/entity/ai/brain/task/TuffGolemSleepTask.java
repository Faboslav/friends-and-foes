package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.TuffGolemBrain;
import com.faboslav.friendsandfoes.entity.pose.TuffGolemEntityPose;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

public class TuffGolemSleepTask extends Task<TuffGolemEntity>
{
	private final static int MIN_TICKS_TO_SLEEP = 1200;
	private final static int MAX_TICKS_TO_SLEEP = 3600;
	private int ticksToSleep;

	public TuffGolemSleepTask() {
		super(ImmutableMap.of(
			FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), MAX_TICKS_TO_SLEEP);
	}

	@Override
	protected boolean shouldRun(
		ServerWorld world,
		TuffGolemEntity tuffGolem
	) {
		if (tuffGolem.isAtHome() == false) {
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
		FriendsAndFoes.getLogger().info("start sleeping");
		this.ticksToSleep = RandomGenerator.generateInt(MIN_TICKS_TO_SLEEP, MAX_TICKS_TO_SLEEP);
		tuffGolem.getNavigation().stop();
		tuffGolem.startSleeping();
	}

	@Override
	protected boolean shouldKeepRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		return this.ticksToSleep > 0;
	}

	@Override
	protected void keepRunning(
		ServerWorld serverWorld,
		TuffGolemEntity tuffGolem,
		long l
	) {
		if(this.ticksToSleep > 0) {
			this.ticksToSleep -= 1;
		}
	}

	@Override
	protected void finishRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		FriendsAndFoes.getLogger().info("stop sleeping");
		TuffGolemBrain.setSleepCooldown(tuffGolem);
		tuffGolem.startStanding();
	}
}
