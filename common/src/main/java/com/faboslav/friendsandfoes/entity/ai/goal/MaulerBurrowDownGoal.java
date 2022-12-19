package com.faboslav.friendsandfoes.entity.ai.goal;

import com.faboslav.friendsandfoes.entity.MaulerEntity;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class MaulerBurrowDownGoal extends Goal
{
	private final MaulerEntity mauler;
	private int burrowedDownTicks;
	private Block blockUnderMauler;
	private SoundEvent soundForBlockUnderMauler;
	private boolean isRunning;

	public MaulerBurrowDownGoal(MaulerEntity mauler) {
		this.mauler = mauler;
	}

	@Override
	public boolean canStart() {
		if (
			this.mauler.hasAngerTime()
			|| this.mauler.getWorld().isNight()
			|| this.mauler.getNavigation().isFollowingPath()
			|| RandomGenerator.generateRandomFloat() < 0.999
			|| this.mauler.getTicksUntilNextBurrowingDown() > 0
		) {
			return false;
		}

		this.setBlockUnderMauler();

		boolean isRelatedBlock = (
			blockUnderMauler == Blocks.SAND
			|| blockUnderMauler == Blocks.RED_SAND
			|| blockUnderMauler == Blocks.DIRT
			|| blockUnderMauler == Blocks.COARSE_DIRT
			|| blockUnderMauler == Blocks.GRASS_BLOCK
		);

		return isRelatedBlock;
	}

	@Override
	public boolean shouldContinue() {
		return this.burrowedDownTicks > 0;
	}

	public void start() {
		this.isRunning = true;
		this.mauler.getNavigation().setSpeed(0);
		this.mauler.getNavigation().stop();

		if (this.getBurrowedDownTicks() == 0) {
			this.burrowedDownTicks = RandomGenerator.generateInt(600, 1200);
		}

		this.mauler.setBurrowedDown(true);
		this.mauler.setInvulnerable(true);

		if (this.blockUnderMauler == Blocks.SAND || this.blockUnderMauler == Blocks.RED_SAND) {
			this.soundForBlockUnderMauler = SoundEvents.BLOCK_SAND_BREAK;
		} else {
			this.soundForBlockUnderMauler = SoundEvents.BLOCK_GRASS_BREAK;
		}
	}

	@Override
	public void stop() {
		this.isRunning = false;
		this.mauler.setInvisible(false);
		this.mauler.setInvulnerable(false);
		this.mauler.setBurrowedDown(false);
		this.mauler.setTicksUntilNextBurrowingDown(
			RandomGenerator.generateInt(
				MaulerEntity.MIN_TICKS_UNTIL_NEXT_BURROWING,
				MaulerEntity.MAX_TICKS_UNTIL_NEXT_BURROWING
			)
		);
	}

	@Override
	public void tick() {
		float burrowingDownAnimationProgress = this.mauler.getBurrowingDownAnimationProgress();
		if (burrowingDownAnimationProgress > 0.0F && burrowingDownAnimationProgress < 1.0F) {
			BlockPos blockPos = this.mauler.getBlockPos();

			if (this.mauler.age % 3 == 0) {
				this.mauler.getWorld().playSound(null, blockPos, this.soundForBlockUnderMauler, SoundCategory.BLOCKS, 0.3F, 1.0F);
			}

			World world = this.mauler.getWorld();

			if (world.isClient()) {
				return;
			}

			for (int i = 0; i < 7; i++) {
				((ServerWorld) world).spawnParticles(
					new BlockStateParticleEffect(
						ParticleTypes.BLOCK,
						this.blockUnderMauler.getDefaultState()
					),
					this.mauler.getParticleX(0.5D),
					this.mauler.getRandomBodyY(),
					this.mauler.getParticleZ(0.5D),
					1,
					this.mauler.getRandom().nextGaussian() * 0.02D,
					this.mauler.getRandom().nextGaussian() * 0.02D,
					this.mauler.getRandom().nextGaussian() * 0.02D,
					0.1D
				);
			}
		} else if (this.mauler.isBurrowedDown() && this.mauler.getBurrowingDownAnimationProgress() == 1.0F) {
			this.mauler.setInvisible(true);
		}

		this.burrowedDownTicks--;
	}

	public void setBlockUnderMauler() {
		BlockPos blockPos = this.mauler.getBlockPos().down();
		BlockState blockState = this.mauler.getWorld().getBlockState(blockPos);
		this.blockUnderMauler = blockState.getBlock();
	}

	public int getBurrowedDownTicks() {
		return this.burrowedDownTicks;
	}

	public void setBurrowedDownTicks(int burrowedDownTicks) {
		this.burrowedDownTicks = burrowedDownTicks;
	}

	public boolean isRunning() {
		return this.isRunning;
	}
}
