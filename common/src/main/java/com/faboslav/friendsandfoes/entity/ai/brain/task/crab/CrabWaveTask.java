package com.faboslav.friendsandfoes.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.animation.CrabAnimations;
import com.faboslav.friendsandfoes.entity.CrabEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.CrabBrain;
import com.faboslav.friendsandfoes.entity.pose.CrabEntityPose;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;

public final class CrabWaveTask extends Task<CrabEntity>
{
	private int waveTicks = 0;
	private int maxWaveTicks = 0;

	public CrabWaveTask() {
		super(
			Map.of(
				FriendsAndFoesMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
			), 40
		);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, CrabEntity crab) {
		if (crab.getNavigation().isFollowingPath()) {
			FriendsAndFoes.getLogger().info("no");
			return false;
		}

		FriendsAndFoes.getLogger().info("should run");
		return true;
	}

	@Override
	protected void run(ServerWorld world, CrabEntity crab, long time) {
		this.waveTicks = 0;
		this.maxWaveTicks = CrabAnimations.WAVE.getAnimationLengthInTicks();
		crab.startWaveAnimation();
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, CrabEntity crab, long time) {
		if (this.waveTicks > this.maxWaveTicks) {
			return false;
		}

		return true;
	}

	protected void keepRunning(ServerWorld world, CrabEntity crab, long time) {
		this.waveTicks++;
	}

	@Override
	protected void finishRunning(ServerWorld world, CrabEntity crab, long time) {
		FriendsAndFoes.getLogger().info("finished");
		crab.setPose(CrabEntityPose.IDLE);
		CrabBrain.setWaveCooldown(crab);
	}
}
