package com.faboslav.friendsandfoes.common.entity.ai.goal.mauler;

import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

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
	public boolean canUse() {
		if (
			this.mauler.isAngry()
			//? >=1.21.5 {
			|| this.mauler.level().isDarkOutside()
			//?} else {
			/*|| this.mauler.level().isNight()
			*///?}
			|| this.mauler.getNavigation().isInProgress()
			|| this.mauler.getRandom().nextFloat() < 0.999F
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
	public boolean canContinueToUse() {
		return this.burrowedDownTicks > 0;
	}

	public void start() {
		this.isRunning = true;
		this.mauler.getNavigation().setSpeedModifier(0);
		this.mauler.getNavigation().stop();

		if (this.getBurrowedDownTicks() == 0) {
			this.burrowedDownTicks = this.mauler.getRandom().nextIntBetweenInclusive(600, 1200);
		}

		this.mauler.setBurrowedDown(true);
		this.mauler.setInvulnerable(true);

		if (this.blockUnderMauler == Blocks.SAND || this.blockUnderMauler == Blocks.RED_SAND) {
			this.soundForBlockUnderMauler = SoundEvents.SAND_BREAK;
		} else {
			this.soundForBlockUnderMauler = SoundEvents.GRASS_BREAK;
		}
	}

	@Override
	public void stop() {
		this.isRunning = false;
		this.mauler.setInvisible(false);
		this.mauler.setInvulnerable(false);
		this.mauler.setBurrowedDown(false);
		this.mauler.setTicksUntilNextBurrowingDown(
			this.mauler.getRandom().nextIntBetweenInclusive(
				MaulerEntity.MIN_TICKS_UNTIL_NEXT_BURROWING,
				MaulerEntity.MAX_TICKS_UNTIL_NEXT_BURROWING
			)
		);
	}

	@Override
	public void tick() {
		float burrowingDownAnimationProgress = this.mauler.getBurrowingDownAnimationProgress();
		if (burrowingDownAnimationProgress > 0.0F && burrowingDownAnimationProgress < 1.0F) {
			BlockPos blockPos = this.mauler.blockPosition();

			if (this.mauler.tickCount % 3 == 0) {
				this.mauler.level().playSound(null, blockPos, this.soundForBlockUnderMauler, SoundSource.BLOCKS, 0.3F, 1.0F);
			}

			Level world = this.mauler.level();

			if (world.isClientSide()) {
				return;
			}

			for (int i = 0; i < 7; i++) {
				((ServerLevel) world).sendParticles(
					new BlockParticleOption(
						ParticleTypes.BLOCK,
						this.blockUnderMauler.defaultBlockState()
					),
					this.mauler.getRandomX(0.5D),
					this.mauler.getRandomY(),
					this.mauler.getRandomZ(0.5D),
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
		BlockPos blockPos = this.mauler.blockPosition().below();
		BlockState blockState = this.mauler.level().getBlockState(blockPos);
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
