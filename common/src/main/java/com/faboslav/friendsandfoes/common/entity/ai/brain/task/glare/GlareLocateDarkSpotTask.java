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
import net.minecraft.util.TimeUtil;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public final class GlareLocateDarkSpotTask extends Behavior<GlareEntity>
{
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
			GlareBrain.setDarkSpotLocatingCooldown(glare, TimeUtil.rangeOfSeconds(10, 10));
			return;
		}

		ResourceKey<Level> registryKey = glare.level().dimension();
		glare.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(), GlobalPos.of(registryKey, darkSpotPos));
	}

	private ArrayList<BlockPos> findDarkSpots(GlareEntity glare) {
		ServerLevel serverWorld = (ServerLevel) glare.level();
		BlockPos blockPos = glare.blockPosition();
		ArrayList<BlockPos> darkSpots = new ArrayList<>();
		int searchDistance = 16;

		for (int y = 0; y <= searchDistance; y = y > 0 ? -y:1 - y) {
			for (int radius = 0; radius < searchDistance; ++radius) {
				for (int x = 0; x <= radius; x = x > 0 ? -x:1 - x) {
					for (int z = x < radius && x > -radius ? radius:0; z <= radius; z = z > 0 ? -z:1 - z) {
						BlockPos.MutableBlockPos possibleDarkSpotBlockPos = new BlockPos.MutableBlockPos();
						possibleDarkSpotBlockPos.setWithOffset(blockPos, x, y - 1, z);

						if (!blockPos.closerThan(possibleDarkSpotBlockPos, searchDistance)) {
							continue;
						}

						if (!serverWorld.isEmptyBlock(possibleDarkSpotBlockPos) || !serverWorld.isEmptyBlock(possibleDarkSpotBlockPos.above())) {
							continue;
						}

						if (serverWorld.getBrightness(LightLayer.BLOCK, possibleDarkSpotBlockPos) != 0) {
							continue;
						}

						if (!serverWorld.getBlockState(possibleDarkSpotBlockPos.below()).entityCanStandOn(serverWorld, possibleDarkSpotBlockPos, glare)) {
							continue;
						}

						darkSpots.add(possibleDarkSpotBlockPos);
					}
				}
			}
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
