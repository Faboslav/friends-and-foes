package com.faboslav.friendsandfoes.common.entity.ai.brain.task.coppergolem;

import com.faboslav.friendsandfoes.common.entity.animation.CopperGolemAnimations;
import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.common.entity.pose.CopperGolemEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public final class CopperGolemSpinHeadTask extends Behavior<CopperGolemEntity>
{
	private int spinHeadTicks = 0;
	private int maxSpinHeadTicks = 0;

	public CopperGolemSpinHeadTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_SPIN_HEAD_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT,
			FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), MemoryStatus.VALUE_ABSENT
		), 40);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, CopperGolemEntity copperGolem) {
		return !(copperGolem.getRandom().nextFloat() < 0.9);
	}

	@Override
	protected void start(ServerLevel world, CopperGolemEntity copperGolem, long time) {
		this.spinHeadTicks = 0;
		this.maxSpinHeadTicks = CopperGolemAnimations.SPIN_HEAD.get().lengthInTicks(copperGolem.getAnimationSpeedModifier());
		copperGolem.startSpinHeadAnimation();
	}

	@Override
	protected boolean canStillUse(ServerLevel world, CopperGolemEntity copperGolem, long time) {
		return this.spinHeadTicks <= this.maxSpinHeadTicks
			   && !copperGolem.isOxidized();
	}

	protected void tick(ServerLevel world, CopperGolemEntity copperGolem, long time) {
		this.spinHeadTicks++;
	}

	protected void stop(ServerLevel world, CopperGolemEntity copperGolem, long time) {
		copperGolem.setPose(CopperGolemEntityPose.IDLE);
		CopperGolemBrain.setSpinHeadCooldown(copperGolem);
	}
}
