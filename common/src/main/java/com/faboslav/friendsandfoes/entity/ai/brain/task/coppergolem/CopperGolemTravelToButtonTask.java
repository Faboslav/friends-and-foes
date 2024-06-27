package com.faboslav.friendsandfoes.entity.ai.brain.task.coppergolem;

import com.faboslav.friendsandfoes.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;

import java.util.Map;

public final class CopperGolemTravelToButtonTask extends MultiTickTask<CopperGolemEntity>
{
	private static final int MAX_TRAVELLING_TICKS = 600;
	private final static float WITHING_DISTANCE = 1.5F;

	public CopperGolemTravelToButtonTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get(), MemoryModuleState.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), MemoryModuleState.VALUE_ABSENT
		), MAX_TRAVELLING_TICKS);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, CopperGolemEntity copperGolem) {
		GlobalPos buttonPos = copperGolem.getButtonPos();

		if (
			buttonPos == null
			|| copperGolem.isButtonValidToBePressed(buttonPos.pos()) == false
		) {
			return false;
		}

		return true;
	}

	@Override
	protected void run(ServerWorld world, CopperGolemEntity copperGolem, long time) {
		this.walkTowardsButton(copperGolem);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, CopperGolemEntity copperGolem, long time) {
		GlobalPos buttonPos = copperGolem.getButtonPos();

		if (
			buttonPos == null
			|| copperGolem.isButtonValidToBePressed(buttonPos.pos()) == false
			|| (
				buttonPos.pos().isWithinDistance(copperGolem.getPos(), WITHING_DISTANCE)
				&& copperGolem.getNavigation().isFollowingPath() == false
			)
			|| copperGolem.isOxidized()
		) {
			return false;
		}

		return true;
	}

	protected void keepRunning(ServerWorld world, CopperGolemEntity copperGolem, long time) {
		if (copperGolem.getNavigation().isFollowingPath()) {
			return;
		}

		this.walkTowardsButton(copperGolem);
	}

	@Override
	protected void finishRunning(ServerWorld world, CopperGolemEntity copperGolem, long time) {
		copperGolem.getBrain().forget(MemoryModuleType.WALK_TARGET);
		GlobalPos buttonPos = copperGolem.getButtonPos();

		if (
			buttonPos != null &&
			(
				buttonPos.pos().isWithinDistance(copperGolem.getPos(), WITHING_DISTANCE) == false
				|| copperGolem.isButtonValidToBePressed(buttonPos.pos()) == false
			)
		) {
			copperGolem.getBrain().forget(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get());
		}
	}

	private void walkTowardsButton(CopperGolemEntity copperGolem) {
		GlobalPos buttonPos = copperGolem.getButtonPos();

		if (buttonPos == null) {
			return;
		}

		LookTargetUtil.walkTowards(
			copperGolem,
			new BlockPos(buttonPos.pos()),
			1.0F,
			0
		);
	}
}
