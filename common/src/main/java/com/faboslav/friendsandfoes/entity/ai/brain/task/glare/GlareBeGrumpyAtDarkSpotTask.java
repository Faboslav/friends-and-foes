package com.faboslav.friendsandfoes.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.entity.GlareEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.util.particle.ParticleSpawner;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.GlobalPos;

import java.util.List;
import java.util.Map;

public final class GlareBeGrumpyAtDarkSpotTask extends Task<GlareEntity>
{
	private static final int MAX_GRUMPY_TICKS = 200;
	private final static float WITHING_DISTANCE = 2.0F;
	private int grumpyTicks;
	private final ParticleEffect particleEffect = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.AZALEA.getDefaultState());

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

		if (
			GlareBeGrumpyAtDarkSpotTask.canBeGrumpyAtDarkSpot(glare) == false
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
		this.applyGlowToHostileEntities(glare);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, GlareEntity glare, long time) {
		GlobalPos darkSpotPos = glare.getDarkSpotPos();

		if (
			GlareBeGrumpyAtDarkSpotTask.canBeGrumpyAtDarkSpot(glare) == false
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

		if (grumpyTicks % 3 == 0 && glare.getRandom().nextBoolean()) {
			glare.playRustleSound();
		}

		if (grumpyTicks % 2 == 0 && glare.getRandom().nextBoolean()) {
			ParticleEffect particleEffect = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.AZALEA.getDefaultState());
			ParticleSpawner.spawnParticles(glare, particleEffect, glare.getRandom().nextBetween(4, 10), 0.1D);
		}
	}

	@Override
	protected void finishRunning(ServerWorld world, GlareEntity glare, long time) {
		glare.setGrumpy(false);
		glare.getBrain().forget(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get());
		GlareBrain.setDarkSpotLocatingCooldown(glare);
	}

	private void applyGlowToHostileEntities(GlareEntity glare) {
		Box box = (new Box(glare.getBlockPos())).expand(24.0);
		List<HostileEntity> hostileEntities = glare.getWorld().getNonSpectatingEntities(HostileEntity.class, box);

		for (HostileEntity hostileEntity : hostileEntities) {
			applyGlowToEntity(hostileEntity);
		}
	}

	private void applyGlowToEntity(HostileEntity entity) {
		entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, MAX_GRUMPY_TICKS));
	}

	public static boolean canBeGrumpyAtDarkSpot(GlareEntity glare) {
		if (
			GlareTravelToDarkSpotTask.canTravelToDarkSpot(glare) == false
			|| glare.getOwner() == null
		) {
			return false;
		}

		return true;
	}
}
