package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.client.render.entity.animation.CrabAnimations;
import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CrabBrain;
import com.faboslav.friendsandfoes.common.entity.pose.CrabEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.util.MovementUtil;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public final class CrabWaveTask extends Behavior<CrabEntity>
{
	private final static int WAVE_DURATION = CrabAnimations.WAVE.get().lengthInTicks();

	private LivingEntity nearestTarget;
	private int waveTicks = 0;
	private int maxWaveTicks = 0;

	public CrabWaveTask() {
		super(
			Map.of(
				MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryStatus.VALUE_PRESENT,
				MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT,
				FriendsAndFoesMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT,
				FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryStatus.VALUE_ABSENT,
				FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryStatus.VALUE_ABSENT
			), WAVE_DURATION
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, CrabEntity crab) {
		if (crab.getNavigation().isInProgress()) {
			return false;
		}

		LivingEntity nearestTarget = crab.getBrain().getMemoryInternal(MemoryModuleType.NEAREST_VISIBLE_PLAYER).orElse(null);

		if (nearestTarget == null) {
			return false;
		}

		this.nearestTarget = nearestTarget;

		return true;
	}

	@Override
	protected void start(ServerLevel world, CrabEntity crab, long time) {
		MovementUtil.stopMovement(crab);
		BehaviorUtils.lookAtEntity(crab, this.nearestTarget);
		crab.getLookControl().setLookAt(this.nearestTarget);
		crab.getLookControl().tick();

		this.waveTicks = 0;
		this.maxWaveTicks = WAVE_DURATION;
		crab.startWaveAnimation();
	}

	@Override
	protected boolean canStillUse(ServerLevel world, CrabEntity crab, long time) {
		return this.waveTicks <= this.maxWaveTicks;
	}

	protected void tick(ServerLevel world, CrabEntity crab, long time) {
		this.waveTicks++;
	}

	@Override
	protected void stop(ServerLevel world, CrabEntity crab, long time) {
		crab.setPose(CrabEntityPose.IDLE);
		CrabBrain.setWaveCooldown(crab);
	}
}
