package com.faboslav.friendsandfoes.common.entity.ai.brain.task.coppergolem;

import com.faboslav.friendsandfoes.common.client.render.entity.animation.CopperGolemAnimations;
import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.common.entity.pose.CopperGolemEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;

public final class CopperGolemSpinHeadTask extends MultiTickTask<CopperGolemEntity>
{
	private int spinHeadTicks = 0;
	private int maxSpinHeadTicks = 0;

	public CopperGolemSpinHeadTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_SPIN_HEAD_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT,
			FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), MemoryModuleState.VALUE_ABSENT
		), 40);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, CopperGolemEntity copperGolem) {
		return !(copperGolem.getRandom().nextFloat() < 0.9);
	}

	@Override
	protected void run(ServerWorld world, CopperGolemEntity copperGolem, long time) {
		this.spinHeadTicks = 0;
		this.maxSpinHeadTicks = CopperGolemAnimations.getSpinHeadKeyframeAnimation(copperGolem.getAnimationSpeedModifier()).getAnimationLengthInTicks();
		copperGolem.startSpinHeadAnimation();
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, CopperGolemEntity copperGolem, long time) {
		return this.spinHeadTicks <= this.maxSpinHeadTicks
			   && !copperGolem.isOxidized();
	}

	protected void keepRunning(ServerWorld world, CopperGolemEntity copperGolem, long time) {
		this.spinHeadTicks++;
	}

	protected void finishRunning(ServerWorld world, CopperGolemEntity copperGolem, long time) {
		copperGolem.setPose(CopperGolemEntityPose.IDLE);
		CopperGolemBrain.setSpinHeadCooldown(copperGolem);
	}
}
