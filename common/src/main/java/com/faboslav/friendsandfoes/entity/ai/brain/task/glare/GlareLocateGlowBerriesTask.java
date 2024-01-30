package com.faboslav.friendsandfoes.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.GlareEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.CaveVines;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class GlareLocateGlowBerriesTask extends MultiTickTask<GlareEntity>
{
	public GlareLocateGlowBerriesTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT,
			FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get(), MemoryModuleState.VALUE_ABSENT
		));
	}

	@Override
	protected boolean shouldRun(ServerWorld world, GlareEntity glare) {
		return FriendsAndFoes.getConfig().enableGlareGriefing != false
			   && !glare.isLeashed()
			   && !glare.isSitting()
			   && glare.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() != false;
	}

	@Override
	protected void run(ServerWorld world, GlareEntity glare, long time) {
		BlockPos glowBerriesPos = this.findNearestGlowBerries(glare);

		if (glowBerriesPos == null) {
			GlareBrain.setLocatingGlowBerriesCooldown(glare, TimeHelper.betweenSeconds(10, 10));
			return;
		}

		RegistryKey<World> registryKey = glare.getWorld().getRegistryKey();
		glare.getBrain().remember(FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get(), GlobalPos.create(registryKey, glowBerriesPos));
	}

	private BlockPos findNearestGlowBerries(GlareEntity glare) {
		int horizontalRange = 16;
		int verticalRange = 8;

		List<BlockPos> glowBerries = this.findAllGlowBerriesInRange(glare.getBlockPos(), horizontalRange, verticalRange, (blockPos) -> {
			BlockState blockState = glare.getWorld().getBlockState(blockPos);
			return CaveVines.hasBerries(blockState);
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

		for (BlockPos blockPos : BlockPos.iterateOutwards(glarePos, horizontalRange, verticalRange, horizontalRange)) {
			BlockPos possibleGlowBerriesBlockPos = blockPos.mutableCopy();

			if (condition.test(possibleGlowBerriesBlockPos) == false) {
				continue;
			}

			buttons.add(possibleGlowBerriesBlockPos);
		}

		return buttons;
	}
}
