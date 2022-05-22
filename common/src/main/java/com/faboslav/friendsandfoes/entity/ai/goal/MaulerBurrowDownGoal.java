package com.faboslav.friendsandfoes.entity.ai.goal;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.MaulerEntity;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

public final class MaulerBurrowDownGoal extends Goal
{
	private final MaulerEntity mauler;
	private int ticksToBeBurrowedDown;
	private int burrowedDownTicks;

	public MaulerBurrowDownGoal(MaulerEntity mauler) {
		this.mauler = mauler;
	}

	@Override
	public boolean canStart() {
		if (this.mauler.hasAngerTime()) {
			return false;
		} else if (this.mauler.getNavigation().isFollowingPath()) {
			return false;
		} else if (RandomGenerator.generateRandomFloat() < 0.99) {
			return false;
		}

		return this.mauler.getTicksUntilNextBurrowingDown() == 0;
	}

	@Override
	public boolean shouldContinue() {
		return this.burrowedDownTicks <= ticksToBeBurrowedDown;
	}

	public void start() {
		FriendsAndFoes.getLogger().info("starting");
		this.burrowedDownTicks = 0;
		this.ticksToBeBurrowedDown = RandomGenerator.generateInt(1200, 2400);
		this.mauler.setBurrowedDown(true);
		this.mauler.setInvulnerable(true);
		//this.mauler.playSound(ModSounds.ENTITY_COPPER_GOLEM_HEAD_SPIN.get(), 1.0F, copperGolem.getSoundPitch() - 1.5F);
	}

	@Override
	public void stop() {
		FriendsAndFoes.getLogger().info("end");
		this.mauler.setBurrowedDown(false);
		this.mauler.setInvulnerable(false);
		this.mauler.setTicksUntilNextBurrowingDown(
			RandomGenerator.generateInt(
				MaulerEntity.MIN_TICKS_UNTIL_NEXT_BURROWING,
				MaulerEntity.MAX_TICKS_UNTIL_NEXT_BURROWING
			)
		);
	}

	@Override
	public void tick() {
		this.burrowedDownTicks++;

		if(this.mauler.getBurrowingDownAnimationProgress() > 0 && this.mauler.getBurrowingDownAnimationProgress() < 1) {
			BlockPos blockPos = this.mauler.getBlockPos();
			this.mauler.getWorld().playSound(null, blockPos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3F, 1.0F);
		}
	}
}
