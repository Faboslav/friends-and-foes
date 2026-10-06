package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
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
import net.minecraft.world.level.LightLayer;

public final class GlareLocateDarkSpotTask extends Behavior<GlareEntity>
{
	private static final int HORIZONTAL_SEARCH_DISTANCE = 16;
	private static final int VERTICAL_SEARCH_DISTANCE = 8;

	public GlareLocateDarkSpotTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT,
			FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(), MemoryStatus.VALUE_ABSENT
		), 1);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, GlareEntity glare) {
		return GlareLocateDarkSpotTask.canLocateDarkSpot(glare);
	}

	@Override
	protected void start(ServerLevel world, GlareEntity glare, long time) {
		BlockPos darkSpotPos = this.findRandomDarkSpot(glare);

		if (darkSpotPos == null) {
			GlareBrain.setDarkSpotLocatingCooldown(glare);
			return;
		}

		ResourceKey<Level> registryKey = glare.level().dimension();
		glare.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(), GlobalPos.of(registryKey, darkSpotPos));
	}

	private ArrayList<BlockPos> findDarkSpots(GlareEntity glare) {
		ServerLevel serverWorld = (ServerLevel) glare.level();
		BlockPos blockPos = glare.blockPosition();
		ArrayList<BlockPos> darkSpots = new ArrayList<>();

		for (BlockPos possibleDarkSpotBlockPos : BlockPos.betweenClosed(
			blockPos.offset(-HORIZONTAL_SEARCH_DISTANCE, -VERTICAL_SEARCH_DISTANCE, -HORIZONTAL_SEARCH_DISTANCE),
			blockPos.offset(HORIZONTAL_SEARCH_DISTANCE, VERTICAL_SEARCH_DISTANCE, HORIZONTAL_SEARCH_DISTANCE)
		)) {
			if (!blockPos.closerThan(possibleDarkSpotBlockPos, HORIZONTAL_SEARCH_DISTANCE)) {
				continue;
			}

			if (!serverWorld.isEmptyBlock(possibleDarkSpotBlockPos) || !serverWorld.isEmptyBlock(possibleDarkSpotBlockPos.above())) {
				continue;
			}

			if (serverWorld.getBrightness(LightLayer.BLOCK, possibleDarkSpotBlockPos) != 0) {
				continue;
			}

			if (!serverWorld.getBlockState(possibleDarkSpotBlockPos.below()).entityCanStandOn(serverWorld, possibleDarkSpotBlockPos.below(), glare)) {
				continue;
			}

			darkSpots.add(possibleDarkSpotBlockPos.immutable());
		}

		return darkSpots;
	}

	@Nullable
	private BlockPos findRandomDarkSpot(GlareEntity glare) {
		ArrayList<BlockPos> darkSpots = this.findDarkSpots(glare);

		if (darkSpots.isEmpty()) {
			return null;
		}

		return darkSpots.get(glare.getRandom().nextInt(darkSpots.size()));
	}

	public static boolean canLocateDarkSpot(GlareEntity glare) {
		if (
			glare.isLeashed()
			|| glare.isOrderedToSit()
			|| glare.isPassenger()
			|| glare.isBaby()
			|| !glare.isTame()
		) {
			return false;
		}

		var level = glare.level();
		//? if >=1.21.5 {
		var isDay = level.isBrightOutside();
		//?} else {
		/*var isDay = level.isDay();
		*///?}

		return !isDay || !level.canSeeSky(glare.blockPosition());
	}
}
