package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public final class GlareTravelToGlowBerriesTask extends Behavior<GlareEntity>
{
	private static final int MAX_TRAVELLING_TICKS = 300;
	private final static float WITHING_DISTANCE = 1.5F;

	public GlareTravelToGlowBerriesTask() {
		super(Map.of(
			FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get(), MemoryStatus.VALUE_PRESENT
		), MAX_TRAVELLING_TICKS);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, GlareEntity glare) {
		GlobalPos glowBerriesPos = glare.getGlowBerriesPos();

		return FriendsAndFoes.getConfig().enableGlareGriefing != false
			   && !glare.isLeashed()
			   && !glare.isOrderedToSit()
			   && glare.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() != false
			   && glare.canEatGlowBerriesAt(glowBerriesPos.pos()) != false
			   && glowBerriesPos != null
			   && !glowBerriesPos.pos().closerToCenterThan(glare.position(), WITHING_DISTANCE);
	}

	@Override
	protected void start(ServerLevel world, GlareEntity glare, long time) {
		this.flyTowardsGlowBerries(glare);
	}

	@Override
	protected boolean canStillUse(ServerLevel world, GlareEntity glare, long time) {
		GlobalPos glowBerriesPos = glare.getGlowBerriesPos();

		if (
			glowBerriesPos == null
			|| glare.canEatGlowBerriesAt(glowBerriesPos.pos()) == false
			|| (
				glowBerriesPos.pos().closerToCenterThan(glare.position(), WITHING_DISTANCE)
				&& glare.getNavigation().isInProgress() == false
			)
			|| FriendsAndFoes.getConfig().enableGlareGriefing == false
			|| glare.isLeashed() == true
			|| glare.isOrderedToSit() == true
			|| glare.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() == false
		) {
			return false;
		}

		return true;
	}

	protected void tick(ServerLevel world, GlareEntity glare, long time) {
		if (glare.getNavigation().isInProgress()) {
			return;
		}

		this.flyTowardsGlowBerries(glare);
	}

	@Override
	protected void stop(ServerLevel world, GlareEntity glare, long time) {
		glare.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		GlobalPos glowBerriesPos = glare.getGlowBerriesPos();

		if (
			glowBerriesPos != null &&
			(
				glowBerriesPos.pos().closerToCenterThan(glare.position(), WITHING_DISTANCE) == false
				|| glare.canEatGlowBerriesAt(glowBerriesPos.pos()) == false
			)
		) {
			glare.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get());
			GlareBrain.setLocatingGlowBerriesCooldown(glare, TimeUtil.rangeOfSeconds(10, 20));
		}
	}

	private void flyTowardsGlowBerries(GlareEntity glare) {
		GlobalPos glowBerriesPos = glare.getGlowBerriesPos();

		if (glowBerriesPos == null) {
			return;
		}

		BehaviorUtils.setWalkAndLookTargetMemories(
			glare,
			new BlockPos(glowBerriesPos.pos()),
			1.0F,
			0
		);
	}
}
