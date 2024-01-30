package com.faboslav.friendsandfoes.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.entity.GlareEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;

import java.util.Map;

public final class GlareTravelToDarkSpotTask extends MultiTickTask<GlareEntity>
{
	private static final int MAX_TRAVELLING_TICKS = 600;
	private final static float WITHING_DISTANCE = 1.5F;

	public GlareTravelToDarkSpotTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(), MemoryModuleState.VALUE_PRESENT
		), MAX_TRAVELLING_TICKS);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, GlareEntity glare) {
		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		if (
			GlareTravelToDarkSpotTask.canTravelToDarkSpot(glare) == false
			|| darkSpotPos.getPos().isWithinDistance(glare.getPos(), WITHING_DISTANCE)
		) {
			return false;
		}

		return true;
	}

	@Override
	protected void run(ServerWorld world, GlareEntity glare, long time) {
		this.flyTowardsDarkSpot(glare);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, GlareEntity glare, long time) {
		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		if (
			GlareTravelToDarkSpotTask.canTravelToDarkSpot(glare) == false
			|| (
				darkSpotPos.getPos().isWithinDistance(glare.getPos(), WITHING_DISTANCE)
				&& glare.getNavigation().isFollowingPath() == false
			)
		) {
			return false;
		}

		return true;
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
				darkSpotPos.getPos().isWithinDistance(glare.getPos(), WITHING_DISTANCE) == false
				|| glare.isDarkSpotDark(darkSpotPos.getPos()) == false
			)
		) {
			glare.getBrain().forget(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get());
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
		if (GlareLocateDarkSpotTask.canLocateDarkSpot(glare) == false) {
			return false;
		}

		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		if (
			darkSpotPos == null
			|| glare.isDarkSpotDark(darkSpotPos.getPos()) == false
		) {
			return false;
		}

		return true;
	}
}
