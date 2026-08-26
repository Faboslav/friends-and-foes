package com.faboslav.friendsandfoes.common.entity.ai.brain.task.crab;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.Level;

public final class CrabLocateBurrowSpotTask extends Behavior<CrabEntity>
{
	public CrabLocateBurrowSpotTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryStatus.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryStatus.VALUE_ABSENT
		));
	}

	@Override
	protected boolean checkExtraStartConditions(
		ServerLevel world,
		CrabEntity crab
	) {
		return crab.isCloseToHomePos(6.0F);
	}

	@Override
	protected void start(ServerLevel world, CrabEntity crab, long time) {
		BlockPos burrowSpotPos = this.findRandomBurrowSpot(crab);

		if (burrowSpotPos == null) {
			crab.setHasEgg(false);
			crab.setInLoveTime(600);
			return;
		}

		ResourceKey<Level> registryKey = crab.level().dimension();
		crab.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get(), GlobalPos.of(registryKey, burrowSpotPos));
	}

	private ArrayList<BlockPos> findBurrowSpots(CrabEntity crab) {
		BlockPos blockPos = crab.blockPosition();
		ArrayList<BlockPos> burrowSpots = new ArrayList<>();
		int searchDistance = 16;

		for (int y = 0; y <= searchDistance; y = y > 0 ? -y:1 - y) {
			for (int radius = 0; radius < searchDistance; ++radius) {
				for (int x = 0; x <= radius; x = x > 0 ? -x:1 - x) {
					for (int z = x < radius && x > -radius ? radius:0; z <= radius; z = z > 0 ? -z:1 - z) {
						BlockPos.MutableBlockPos possibleBurrowSpotBlockPos = new BlockPos.MutableBlockPos();
						possibleBurrowSpotBlockPos.setWithOffset(blockPos, x, y - 1, z);

						if (!blockPos.closerThan(possibleBurrowSpotBlockPos, searchDistance)) {
							continue;
						}

						if (!crab.isBurrowSpotAccessible(possibleBurrowSpotBlockPos)) {
							continue;
						}

						burrowSpots.add(possibleBurrowSpotBlockPos);
					}
				}
			}
		}

		return burrowSpots;
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
