package com.faboslav.friendsandfoes.common.entity.ai.brain.task.penguin;

import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
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

public final class PenguinLocateEggSpotTask extends Behavior<PenguinEntity>
{
	private static final int SEARCH_DISTANCE = 16;

	public PenguinLocateEggSpotTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.PENGUIN_HAS_EGG.get(), MemoryStatus.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.PENGUIN_EGG_POS.get(), MemoryStatus.VALUE_ABSENT
		));
	}

	@Override
	protected void start(ServerLevel world, PenguinEntity penguin, long time) {
		BlockPos eggSpotPos = this.findRandomEggSpot(penguin);

		if (eggSpotPos == null) {
			penguin.setHasEgg(false);
			penguin.setInLoveTime(600);
			return;
		}

		ResourceKey<Level> registryKey = penguin.level().dimension();
		penguin.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.PENGUIN_EGG_POS.get(), GlobalPos.of(registryKey, eggSpotPos));
	}

	private ArrayList<BlockPos> findEggSpots(PenguinEntity penguin) {
		BlockPos blockPos = penguin.blockPosition();
		ArrayList<BlockPos> eggSpots = new ArrayList<>();

		for (int i = 0; (double) i <= SEARCH_DISTANCE; i = i > 0 ? -i:1 - i) {
			for (int j = 0; (double) j < SEARCH_DISTANCE; ++j) {
				for (int k = 0; k <= j; k = k > 0 ? -k:1 - k) {
					for (int l = k < j && k > -j ? j:0; l <= j; l = l > 0 ? -l:1 - l) {
						BlockPos.MutableBlockPos possibleEggSpotBlockPos = new BlockPos.MutableBlockPos();
						possibleEggSpotBlockPos.setWithOffset(blockPos, k, i - 1, l);

						boolean isBlockWithinDistance = blockPos.closerThan(
							possibleEggSpotBlockPos,
							SEARCH_DISTANCE
						);

						if (isBlockWithinDistance && penguin.isEggSpotAccessible(possibleEggSpotBlockPos)) {
							eggSpots.add(possibleEggSpotBlockPos);
						}
					}
				}
			}
		}

		return eggSpots;
	}

	@Nullable
	private BlockPos findRandomEggSpot(PenguinEntity penguin) {
		ArrayList<BlockPos> eggSpots = this.findEggSpots(penguin);

		if (eggSpots.isEmpty()) {
			return null;
		}

		return eggSpots.get(penguin.getRandom().nextInt(eggSpots.size()));
	}
}
