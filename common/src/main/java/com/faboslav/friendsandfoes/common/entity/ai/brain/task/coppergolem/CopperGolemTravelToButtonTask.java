package com.faboslav.friendsandfoes.common.entity.ai.brain.task.coppergolem;

import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;

import java.util.Map;

public final class CopperGolemTravelToButtonTask extends Task<CopperGolemEntity>
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

		return buttonPos != null
			   && copperGolem.isButtonValidToBePressed(buttonPos.getPos());
	}

	@Override
	protected void run(ServerWorld world, CopperGolemEntity copperGolem, long time) {
		this.walkTowardsButton(copperGolem);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, CopperGolemEntity copperGolem, long time) {
		GlobalPos buttonPos = copperGolem.getButtonPos();

		return buttonPos != null
			   && copperGolem.isButtonValidToBePressed(buttonPos.getPos())
			   && (!buttonPos.getPos().isWithinDistance(copperGolem.getPos(), WITHING_DISTANCE)
				   || copperGolem.getNavigation().isFollowingPath())
			   && !copperGolem.isOxidized();
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
				!buttonPos.getPos().isWithinDistance(copperGolem.getPos(), WITHING_DISTANCE)
				|| !copperGolem.isButtonValidToBePressed(buttonPos.getPos())
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
			new BlockPos(buttonPos.getPos()),
			1.0F,
			0
		);
	}
}
