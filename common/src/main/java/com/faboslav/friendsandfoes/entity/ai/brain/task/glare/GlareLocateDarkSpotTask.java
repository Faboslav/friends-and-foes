package com.faboslav.friendsandfoes.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.GlareEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Map;

public final class GlareLocateDarkSpotTask extends Task<GlareEntity>
{
	public GlareLocateDarkSpotTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT,
			FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(), MemoryModuleState.VALUE_ABSENT
		));
	}

	@Override
	protected boolean shouldRun(ServerWorld world, GlareEntity glare) {
        return !glare.isLeashed()
                && !glare.isSitting()
                && !glare.isBaby();
    }

	@Override
	protected void run(ServerWorld world, GlareEntity glare, long time) {
		BlockPos darkSpotPos = this.findRandomDarkSpot(glare);

		if (darkSpotPos == null) {
			FriendsAndFoes.getLogger().info("not found setting cd");
			GlareBrain.setDarkSpotLocatingCooldown(glare, TimeHelper.betweenSeconds(10, 10));
			return;
		}

		RegistryKey<World> registryKey = glare.getWorld().getRegistryKey();
		glare.getBrain().remember(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(), GlobalPos.create(registryKey, darkSpotPos));
	}

	private ArrayList<BlockPos> findDarkSpots(GlareEntity glare) {
		ServerWorld serverWorld = (ServerWorld) glare.getEntityWorld();
		BlockPos blockPos = glare.getBlockPos();
		ArrayList<BlockPos> darkSpots = new ArrayList<>();
		int searchDistance = 16;

		for (int i = 0; (double) i <= searchDistance; i = i > 0 ? -i:1 - i) {
			for (int j = 0; (double) j < searchDistance; ++j) {
				for (int k = 0; k <= j; k = k > 0 ? -k:1 - k) {
					for (int l = k < j && k > -j ? j:0; l <= j; l = l > 0 ? -l:1 - l) {
						BlockPos.Mutable possibleDarkSpotBlockPos = new BlockPos.Mutable();
						possibleDarkSpotBlockPos.set(blockPos, k, i - 1, l);

						boolean isBlockWithinDistance = blockPos.isWithinDistance(
							possibleDarkSpotBlockPos,
							searchDistance
						);
						boolean isSpotDarkEnough = glare.getWorld().getLightLevel(LightType.BLOCK, possibleDarkSpotBlockPos) == 0;
						boolean isBlockSolidSurface = serverWorld.getBlockState(possibleDarkSpotBlockPos.down()).hasSolidTopSurface(serverWorld,
							possibleDarkSpotBlockPos,
							glare
						);
						boolean isBlockAccessible = serverWorld.isAir(possibleDarkSpotBlockPos) && serverWorld.isAir(possibleDarkSpotBlockPos.up());

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
}
