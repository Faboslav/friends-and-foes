package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public final class GlareTravelToDarkSpotTask extends Behavior<GlareEntity>
{
	private static final int MAX_TRAVELLING_TICKS = 300;
	private final static float WITHING_DISTANCE = 1.5F;

	public GlareTravelToDarkSpotTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(), MemoryStatus.VALUE_PRESENT
		), MAX_TRAVELLING_TICKS);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, GlareEntity glare) {
		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		if (
			GlareTravelToDarkSpotTask.canTravelToDarkSpot(glare) == false
			|| darkSpotPos.pos().closerToCenterThan(glare.position(), WITHING_DISTANCE)
		) {
			return false;
		}

		return true;
	}

	@Override
	protected void start(ServerLevel world, GlareEntity glare, long time) {
		this.flyTowardsDarkSpot(glare);
	}

	@Override
	protected boolean canStillUse(ServerLevel world, GlareEntity glare, long time) {
		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		if (
			GlareTravelToDarkSpotTask.canTravelToDarkSpot(glare) == false
			|| (
				darkSpotPos.pos().closerToCenterThan(glare.position(), WITHING_DISTANCE)
				&& glare.getNavigation().isInProgress() == false
			)
		) {
			return false;
		}

		return true;
	}

	protected void tick(ServerLevel world, GlareEntity glare, long time) {
		if (glare.getNavigation().isInProgress()) {
			return;
		}

		this.flyTowardsDarkSpot(glare);
	}

	@Override
	protected void stop(ServerLevel world, GlareEntity glare, long time) {
		glare.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		if (
			darkSpotPos != null &&
			(
				darkSpotPos.pos().closerToCenterThan(glare.position(), WITHING_DISTANCE) == false
				|| glare.isDarkSpotDark(darkSpotPos.pos()) == false
			)
		) {
			glare.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get());
			GlareBrain.setDarkSpotLocatingCooldown(glare, TimeUtil.rangeOfSeconds(10, 20));
		}
	}

	private void flyTowardsDarkSpot(GlareEntity glare) {
		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		if (darkSpotPos == null) {
			return;
		}

		BehaviorUtils.setWalkAndLookTargetMemories(
			glare,
			new BlockPos(darkSpotPos.pos()),
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
			|| glare.isDarkSpotDark(darkSpotPos.pos()) == false
		) {
			return false;
		}

		return true;
	}
}
