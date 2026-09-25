package com.faboslav.friendsandfoes.common.entity.ai.brain.task.barnacle;

import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.BarnacleBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class BarnacleLocateHidingSpotTask extends Behavior<BarnacleEntity>
{
	private static final int HORIZONTAL_SEARCH_DISTANCE = 16;
	private static final int VERTICAL_SEARCH_DISTANCE = 8;

	public BarnacleLocateHidingSpotTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.BARNACLE_HIDING_SPOT_POS.get(), MemoryStatus.VALUE_ABSENT,
			FriendsAndFoesMemoryModuleTypes.BARNACLE_HIDING_SPOT_LOCATING_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT,
			MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT
		), 1);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, BarnacleEntity barnacle) {
		return barnacle.isInWater();
	}

	@Override
	protected void start(ServerLevel world, BarnacleEntity barnacle, long time) {
		BlockPos hidingSpotPos = this.findRandomHidingSpot(world, barnacle);

		if (hidingSpotPos == null) {
			BarnacleBrain.setHidingSpotLocatingCooldown(barnacle);
			return;
		}

		barnacle.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.BARNACLE_HIDING_SPOT_POS.get(), GlobalPos.of(world.dimension(), hidingSpotPos));
	}

	@Nullable
	private BlockPos findRandomHidingSpot(ServerLevel world, BarnacleEntity barnacle) {
		List<BlockPos> hidingSpots = this.findHidingSpots(world, barnacle);

		if (hidingSpots.isEmpty()) {
			return null;
		}

		return hidingSpots.get(barnacle.getRandom().nextInt(hidingSpots.size()));
	}

	private List<BlockPos> findHidingSpots(ServerLevel world, BarnacleEntity barnacle) {
		BlockPos barnaclePos = barnacle.blockPosition();
		List<BlockPos> hidingSpots = new ArrayList<>();

		for (BlockPos pos : BlockPos.betweenClosed(
			barnaclePos.offset(-HORIZONTAL_SEARCH_DISTANCE, -VERTICAL_SEARCH_DISTANCE, -HORIZONTAL_SEARCH_DISTANCE),
			barnaclePos.offset(HORIZONTAL_SEARCH_DISTANCE, VERTICAL_SEARCH_DISTANCE, HORIZONTAL_SEARCH_DISTANCE)
		)) {
			if (barnaclePos.closerThan(pos, HORIZONTAL_SEARCH_DISTANCE) && BarnacleEntity.isHidingSpot(world, pos)) {
				hidingSpots.add(pos.immutable());
			}
		}

		return hidingSpots;
	}
}
