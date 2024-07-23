package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;

import java.util.Map;

public final class GlareTravelToGlowBerriesTask extends Task<GlareEntity>
{
	private static final int MAX_TRAVELLING_TICKS = 300;
	private final static float WITHING_DISTANCE = 1.5F;

	public GlareTravelToGlowBerriesTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get(), MemoryModuleState.VALUE_PRESENT
		), MAX_TRAVELLING_TICKS);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, GlareEntity glare) {
		GlobalPos glowBerriesPos = glare.getGlowBerriesPos();

		return FriendsAndFoes.getConfig().enableGlareGriefing != false
			   && !glare.isLeashed()
			   && !glare.isSitting()
			   && glare.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() != false
			   && glare.canEatGlowBerriesAt(glowBerriesPos.getPos()) != false
			   && glowBerriesPos != null
			   && !glowBerriesPos.getPos().isWithinDistance(glare.getPos(), WITHING_DISTANCE);
	}

	@Override
	protected void run(ServerWorld world, GlareEntity glare, long time) {
		this.flyTowardsGlowBerries(glare);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, GlareEntity glare, long time) {
		GlobalPos glowBerriesPos = glare.getGlowBerriesPos();

		if (
			glowBerriesPos == null
			|| glare.canEatGlowBerriesAt(glowBerriesPos.getPos()) == false
			|| (
				glowBerriesPos.getPos().isWithinDistance(glare.getPos(), WITHING_DISTANCE)
				&& glare.getNavigation().isFollowingPath() == false
			)
			|| FriendsAndFoes.getConfig().enableGlareGriefing == false
			|| glare.isLeashed() == true
			|| glare.isSitting() == true
			|| glare.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() == false
		) {
			return false;
		}

		return true;
	}

	protected void keepRunning(ServerWorld world, GlareEntity glare, long time) {
		if (glare.getNavigation().isFollowingPath()) {
			return;
		}

		this.flyTowardsGlowBerries(glare);
	}

	@Override
	protected void finishRunning(ServerWorld world, GlareEntity glare, long time) {
		glare.getBrain().forget(MemoryModuleType.WALK_TARGET);
		GlobalPos glowBerriesPos = glare.getGlowBerriesPos();

		if (
			glowBerriesPos != null &&
			(
				glowBerriesPos.getPos().isWithinDistance(glare.getPos(), WITHING_DISTANCE) == false
				|| glare.canEatGlowBerriesAt(glowBerriesPos.getPos()) == false
			)
		) {
			glare.getBrain().forget(FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get());
			GlareBrain.setLocatingGlowBerriesCooldown(glare, TimeHelper.betweenSeconds(10, 20));
		}
	}

	private void flyTowardsGlowBerries(GlareEntity glare) {
		GlobalPos glowBerriesPos = glare.getGlowBerriesPos();

		if (glowBerriesPos == null) {
			return;
		}

		LookTargetUtil.walkTowards(
			glare,
			new BlockPos(glowBerriesPos.getPos()),
			1.0F,
			0
		);
	}
}
