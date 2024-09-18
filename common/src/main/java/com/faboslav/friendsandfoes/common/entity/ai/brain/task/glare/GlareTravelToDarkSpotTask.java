package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;

import java.util.Map;

public final class GlareTravelToDarkSpotTask extends Task<GlareEntity>
{
	private static final int MAX_TRAVELLING_TICKS = 300;
	private final static float WITHING_DISTANCE = 1.5F;

	public GlareTravelToDarkSpotTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(), MemoryModuleState.VALUE_PRESENT
		), MAX_TRAVELLING_TICKS);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, GlareEntity glare) {
		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		return GlareTravelToDarkSpotTask.canTravelToDarkSpot(glare)
			   && !darkSpotPos.getPos().isWithinDistance(glare.getPos(), WITHING_DISTANCE);
	}

	@Override
	protected void run(ServerWorld world, GlareEntity glare, long time) {
		this.flyTowardsDarkSpot(glare);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, GlareEntity glare, long time) {
		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		return GlareTravelToDarkSpotTask.canTravelToDarkSpot(glare)
			   && (!darkSpotPos.getPos().isWithinDistance(glare.getPos(), WITHING_DISTANCE)
				   || glare.getNavigation().isFollowingPath());
	}

	protected void keepRunning(ServerWorld world, GlareEntity glare, long time) {
		if (glare.getNavigation().isFollowingPath()) {
			return;
		}

		this.flyTowardsDarkSpot(glare);
	}

	@Override
	protected void finishRunning(ServerWorld world, GlareEntity glare, long time) {
		glare.getBrain().forget(MemoryModuleType.WALK_TARGET);
		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		if (
			darkSpotPos != null &&
			(
				!darkSpotPos.getPos().isWithinDistance(glare.getPos(), WITHING_DISTANCE)
				|| !glare.isDarkSpotDark(darkSpotPos.getPos())
			)
		) {
			glare.getBrain().forget(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get());
			GlareBrain.setDarkSpotLocatingCooldown(glare, TimeHelper.betweenSeconds(10, 20));
		}
	}

	private void flyTowardsDarkSpot(GlareEntity glare) {
		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		if (darkSpotPos == null) {
			return;
		}

		LookTargetUtil.walkTowards(
			glare,
			new BlockPos(darkSpotPos.getPos()),
			1.0F,
			0
		);
	}

	public static boolean canTravelToDarkSpot(GlareEntity glare) {
		if (!GlareLocateDarkSpotTask.canLocateDarkSpot(glare)) {
			return false;
		}

		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		return darkSpotPos != null
			   && glare.isDarkSpotDark(darkSpotPos.getPos());
	}
}
