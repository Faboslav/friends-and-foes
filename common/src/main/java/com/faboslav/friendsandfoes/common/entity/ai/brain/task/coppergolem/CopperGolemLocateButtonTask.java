package com.faboslav.friendsandfoes.common.entity.ai.brain.task.coppergolem;

import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.Level;

public final class CopperGolemLocateButtonTask extends Behavior<CopperGolemEntity>
{
	public CopperGolemLocateButtonTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT,
			FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get(), MemoryStatus.VALUE_ABSENT,
			FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), MemoryStatus.VALUE_ABSENT
		));
	}

	@Override
	protected void start(ServerLevel world, CopperGolemEntity copperGolem, long time) {
		BlockPos buttonBlockPos = this.findNearestRandomButton(copperGolem);

		if (buttonBlockPos == null) {
			CopperGolemBrain.setPressButtonCooldown(copperGolem, TimeUtil.rangeOfSeconds(10, 10));
			return;
		}

		ResourceKey<Level> registryKey = copperGolem.level().dimension();
		copperGolem.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get(), GlobalPos.of(registryKey, buttonBlockPos));
	}

	private BlockPos findNearestRandomButton(CopperGolemEntity copperGolem) {
		int horizontalRange = 16;
		int verticalRange = 8;

		List<BlockPos> buttons = this.findAllButtonsInRange(copperGolem.blockPosition(), horizontalRange, verticalRange, (blockPos) -> {
			return copperGolem.level().getBlockState(blockPos).is(FriendsAndFoesTags.COPPER_BUTTONS);
		});

		if (buttons.isEmpty()) {
			return null;
		}

		return buttons.get(copperGolem.getRandom().nextInt(buttons.size()));
	}

	private List<BlockPos> findAllButtonsInRange(
		BlockPos copperGolemPos,
		int horizontalRange,
		int verticalRange,
		Predicate<BlockPos> condition
	) {
		List<BlockPos> buttons = new ArrayList<>();
		for (BlockPos blockPos : BlockPos.withinManhattan(copperGolemPos, horizontalRange, verticalRange, horizontalRange)) {
			BlockPos possibleButtonBlockPos = blockPos.mutable();

			if (!condition.test(possibleButtonBlockPos)) {
				continue;
			}

			buttons.add(possibleButtonBlockPos);
		}

		return buttons;
	}
}
