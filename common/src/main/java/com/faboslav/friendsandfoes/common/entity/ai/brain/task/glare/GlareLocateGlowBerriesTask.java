package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.state.BlockState;

public final class GlareLocateGlowBerriesTask extends Behavior<GlareEntity>
{
	public GlareLocateGlowBerriesTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT,
			FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get(), MemoryStatus.VALUE_ABSENT
		));
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, GlareEntity glare) {
		return FriendsAndFoes.getConfig().enableGlareGriefing
			   && !glare.isLeashed()
			   && !glare.isOrderedToSit()
			   && !glare.isPassenger()
			   && glare.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
	}

	@Override
	protected void start(ServerLevel world, GlareEntity glare, long time) {
		BlockPos glowBerriesPos = this.findNearestGlowBerries(glare);

		if (glowBerriesPos == null) {
			GlareBrain.setLocatingGlowBerriesCooldown(glare, TimeUtil.rangeOfSeconds(10, 10));
			return;
		}

		ResourceKey<Level> registryKey = glare.level().dimension();
		glare.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get(), GlobalPos.of(registryKey, glowBerriesPos));
	}

	private BlockPos findNearestGlowBerries(GlareEntity glare) {
		int horizontalRange = 16;
		int verticalRange = 8;

		List<BlockPos> glowBerries = this.findAllGlowBerriesInRange(glare.blockPosition(), horizontalRange, verticalRange, (blockPos) -> {
			BlockState blockState = glare.level().getBlockState(blockPos);
			return CaveVines.hasGlowBerries(blockState);
		});

		if (glowBerries.isEmpty()) {
			return null;
		}

		return glowBerries.get(glare.getRandom().nextInt(glowBerries.size()));
	}

	private List<BlockPos> findAllGlowBerriesInRange(
		BlockPos glarePos,
		int horizontalRange,
		int verticalRange,
		Predicate<BlockPos> condition
	) {
		List<BlockPos> buttons = new ArrayList<>();

		for (BlockPos blockPos : BlockPos.withinManhattan(glarePos, horizontalRange, verticalRange, horizontalRange)) {
			BlockPos possibleGlowBerriesBlockPos = blockPos.mutable();

			if (!condition.test(possibleGlowBerriesBlockPos)) {
				continue;
			}

			buttons.add(possibleGlowBerriesBlockPos);
		}

		return buttons;
	}
}
