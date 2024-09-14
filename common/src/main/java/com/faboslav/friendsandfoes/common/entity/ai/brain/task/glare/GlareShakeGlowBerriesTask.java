package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.CaveVines;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.GameRules;

import java.util.Map;

public final class GlareShakeGlowBerriesTask extends MultiTickTask<GlareEntity>
{
	private static final int MAX_SHAKING_TICKS = 200;
	private final static float WITHING_DISTANCE = 2.0F;
	private int shakingTicks;

	public GlareShakeGlowBerriesTask() {
		super(
			Map.of(
				FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get(), MemoryModuleState.VALUE_PRESENT
			), MAX_SHAKING_TICKS
		);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, GlareEntity glare) {
		GlobalPos glowBerriesPos = glare.getGlowBerriesPos();

		return glare.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) != false
			   && FriendsAndFoes.getConfig().enableGlareGriefing != false
			   && !glare.isLeashed()
			   && !glare.isSitting()
			   && glare.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() != false
			   && glowBerriesPos != null
			   && glare.canEatGlowBerriesAt(glowBerriesPos.pos()) != false
			   && glowBerriesPos.pos().isWithinDistance(glare.getPos(), WITHING_DISTANCE) != false;
	}

	@Override
	protected void run(ServerWorld world, GlareEntity glare, long time) {
		this.shakingTicks = 0;
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, GlareEntity glare, long time) {
		GlobalPos glowBerriesPos = glare.getGlowBerriesPos();

		return glare.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) != false
			   && FriendsAndFoes.getConfig().enableGlareGriefing != false
			   && !glare.isLeashed()
			   && !glare.isSitting()
			   && glare.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() != false
			   && glowBerriesPos != null
			   && glare.canEatGlowBerriesAt(glowBerriesPos.pos()) != false
			   && glowBerriesPos.pos().isWithinDistance(glare.getPos(), WITHING_DISTANCE) != false;
	}

	protected void keepRunning(ServerWorld world, GlareEntity glare, long time) {
		this.shakingTicks++;

		if (shakingTicks % 5 == 0 && glare.getRandom().nextFloat() > 0.85) {
			glare.playRustleSound();
		}

		if (glare.getRandom().nextFloat() < 0.05F && this.shakingTicks > MAX_SHAKING_TICKS / 2) {
			glare.playAmbientSound();
			shakeOffGlowBerries(glare);
			GlareBrain.setLocatingGlowBerriesCooldown(glare);
		}
	}

	@Override
	protected void finishRunning(ServerWorld world, GlareEntity glare, long time) {
		glare.getBrain().forget(FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get());
	}

	private void shakeOffGlowBerries(GlareEntity glare) {
		if (
			glare.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) == false
			|| FriendsAndFoes.getConfig().enableGlareGriefing == false
		) {
			return;
		}

		GlobalPos glowBerriesPos = glare.getGlowBerriesPos();

		if (glowBerriesPos == null) {
			return;
		}

		BlockState blockState = glare.getWorld().getBlockState(glowBerriesPos.pos());

		if (CaveVines.hasBerries(blockState) == false) {
			return;
		}

		CaveVines.pickBerries(
			glare,
			blockState,
			glare.getWorld(),
			glowBerriesPos.pos()
		);
	}
}
