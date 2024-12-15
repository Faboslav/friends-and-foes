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
		ServerLevel serverWorld = (ServerLevel) glare.getCommandSenderWorld();
		BlockPos blockPos = glare.blockPosition();
		ArrayList<BlockPos> darkSpots = new ArrayList<>();
		int searchDistance = 16;

		for (int i = 0; (double) i <= searchDistance; i = i > 0 ? -i:1 - i) {
			for (int j = 0; (double) j < searchDistance; ++j) {
				for (int k = 0; k <= j; k = k > 0 ? -k:1 - k) {
					for (int l = k < j && k > -j ? j:0; l <= j; l = l > 0 ? -l:1 - l) {
						BlockPos.MutableBlockPos possibleDarkSpotBlockPos = new BlockPos.MutableBlockPos();
						possibleDarkSpotBlockPos.setWithOffset(blockPos, k, i - 1, l);

						boolean isBlockWithinDistance = blockPos.closerThan(
							possibleDarkSpotBlockPos,
							searchDistance
						);
						boolean isSpotDarkEnough = glare.level().getBrightness(LightLayer.BLOCK, possibleDarkSpotBlockPos) == 0;
						boolean isBlockSolidSurface = serverWorld.getBlockState(possibleDarkSpotBlockPos.below()).entityCanStandOn(serverWorld,
							possibleDarkSpotBlockPos,
							glare
						);
						boolean isBlockAccessible = serverWorld.isEmptyBlock(possibleDarkSpotBlockPos) && serverWorld.isEmptyBlock(possibleDarkSpotBlockPos.above());

						if (
							isBlockWithinDistance
							&& isBlockSolidSurface
							&& isBlockAccessible
							&& isSpotDarkEnough
						) {
							darkSpots.add(possibleDarkSpotBlockPos);
						}
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
		return !glare.isLeashed()
			   && !glare.isOrderedToSit()
			   && !glare.isPassenger()
			   && glare.isTame()
			   && !glare.isBaby()
			   && (!glare.level().isDay()
				   || !glare.level().canSeeSky(glare.blockPosition()));
	}
}
