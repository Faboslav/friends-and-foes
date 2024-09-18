package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Map;

public final class CrabLocateBurrowSpotTask extends Task<CrabEntity>
{
	public CrabLocateBurrowSpotTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryModuleState.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryModuleState.VALUE_ABSENT
		));
	}

	@Override
	protected boolean shouldRun(
		ServerWorld world,
		CrabEntity crab
	) {
		return crab.isCloseToHomePos(6.0F);
	}

	@Override
	protected void run(ServerWorld world, CrabEntity crab, long time) {
		BlockPos burrowSpotPos = this.findRandomBurrowSpot(crab);

		if (burrowSpotPos == null) {
			crab.setHasEgg(false);
			crab.setLoveTicks(600);
			return;
		}

		RegistryKey<World> registryKey = crab.getWorld().getRegistryKey();
		crab.getBrain().remember(FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), GlobalPos.create(registryKey, burrowSpotPos));
	}

	private ArrayList<BlockPos> findBurrowSpots(CrabEntity crab) {
		BlockPos blockPos = crab.getBlockPos();
		ArrayList<BlockPos> darkSpots = new ArrayList<>();
		int searchDistance = 16;

		for (int i = 0; (double) i <= searchDistance; i = i > 0 ? -i:1 - i) {
			for (int j = 0; (double) j < searchDistance; ++j) {
				for (int k = 0; k <= j; k = k > 0 ? -k:1 - k) {
					for (int l = k < j && k > -j ? j:0; l <= j; l = l > 0 ? -l:1 - l) {
						BlockPos.Mutable possibleBurrowSpotBlockPos = new BlockPos.Mutable();
						possibleBurrowSpotBlockPos.set(blockPos, k, i - 1, l);

						boolean isBlockWithinDistance = blockPos.isWithinDistance(
							possibleBurrowSpotBlockPos,
							searchDistance
						);


						if (isBlockWithinDistance && crab.isBurrowSpotAccessible(possibleBurrowSpotBlockPos)) {
							darkSpots.add(possibleBurrowSpotBlockPos);
						}
					}
				}
			}
		}

		return darkSpots;
	}

	@Nullable
	private BlockPos findRandomBurrowSpot(CrabEntity crab) {
		ArrayList<BlockPos> burrowSpots = this.findBurrowSpots(crab);

		if (burrowSpots.isEmpty()) {
			return null;
		}

		return burrowSpots.get(crab.getRandom().nextInt(burrowSpots.size()));
	}
}
