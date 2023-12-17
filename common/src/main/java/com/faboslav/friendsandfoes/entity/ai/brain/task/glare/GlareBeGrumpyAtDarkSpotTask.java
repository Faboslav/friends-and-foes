package com.faboslav.friendsandfoes.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.GlareEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.util.particle.ParticleSpawner;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CaveVines;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.GameRules;

import java.util.Map;

public final class GlareBeGrumpyAtDarkSpotTask extends Task<GlareEntity>
{
	private static final int MAX_GRUMPY_TICKS = 200;
	private final static float WITHING_DISTANCE = 2.0F;
	private int grumpyTicks;

	public GlareBeGrumpyAtDarkSpotTask() {
		super(
			Map.of(
				FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(), MemoryModuleState.VALUE_PRESENT
			), MAX_GRUMPY_TICKS
		);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, GlareEntity glare) {
		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		if(
			glare.isLeashed()
			|| glare.isSitting()
			|| glare.isBaby()
			|| glare.getOwner() == null
			|| darkSpotPos == null
			|| glare.isDarkSpotDark(darkSpotPos.getPos()) == false
			|| darkSpotPos.getPos().isWithinDistance(glare.getPos(), WITHING_DISTANCE) == false
		) {
			return false;
		}

		return true;
    }

	@Override
	protected void run(ServerWorld world, GlareEntity glare, long time) {
		this.grumpyTicks = 0;
		glare.setGrumpy(true);

		LookTargetUtil.lookAt(glare, glare.getOwner());
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, GlareEntity glare, long time) {
		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		if(
			glare.isLeashed()
			|| glare.isSitting()
			|| glare.isBaby()
			|| glare.getOwner() == null
			|| darkSpotPos == null
			|| glare.isDarkSpotDark(darkSpotPos.getPos()) == false
			|| darkSpotPos.getPos().isWithinDistance(glare.getPos(), WITHING_DISTANCE) == false
		) {
			return false;
		}

		return true;
    }

	protected void keepRunning(ServerWorld world, GlareEntity glare, long time) {
		this.grumpyTicks++;
		LookTargetUtil.lookAt(glare, glare.getOwner());

		if (glare.isGrumpy() == false) {
			glare.setGrumpy(true);
		}

		if (grumpyTicks == 10) {
			glare.playGrumpinessSound();
		}

		if (grumpyTicks % 5 == 0) {
			glare.playRustleSound();
		}

		if (grumpyTicks % 10 == 0) {
			ParticleEffect particleEffect = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.AZALEA.getDefaultState());
			ParticleSpawner.spawnParticles(glare, particleEffect, 7, 0.1D);
		}
	}

	@Override
	protected void finishRunning(ServerWorld world, GlareEntity glare, long time) {
		glare.setGrumpy(false);
		glare.getBrain().forget(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get());
		GlareBrain.setDarkSpotLocatingCooldown(glare);
		FriendsAndFoes.getLogger().info("Setting cooldown");
	}
}
