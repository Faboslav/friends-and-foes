package com.faboslav.friendsandfoes.entity.ai.goal;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.GlareEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.CaveVines;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldView;

public final class GlareShakeOffGlowBerriesGoal extends MoveToTargetPosGoal
{
	public boolean isRunning = false;
	private final GlareEntity glare;
	private static final int COLLECTING_TIME = 40;
	private static final int RANGE = 8;
	private static final int MAX_Y_DIFFERENCE = 8;
	private int timer;

	public GlareShakeOffGlowBerriesGoal(GlareEntity glare) {
		super(
			glare,
			glare.getMovementSpeed(),
			RANGE,
			MAX_Y_DIFFERENCE
		);

		this.glare = glare;
	}

	@Override
	public double getDesiredDistanceToTarget() {
		return 4.0D;
	}

	public boolean shouldResetPath() {
		return this.tryingTime % 100 == 0;
	}

	protected boolean isTargetPos(WorldView world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		return CaveVines.hasBerries(blockState);
	}

	protected void startMovingToTarget() {
		this.mob.getNavigation().startMovingTo(
			this.targetPos.getX(),
			this.targetPos.getY() - 2.0D,
			this.targetPos.getZ(),
			this.speed
		);
	}

	@Override
	public boolean canStart() {
		return this.glare.isLeashed() != true
			   && this.glare.isSitting() != true
			   && this.glare.getRandom().nextInt(10) == 0
			   && this.glare.isBaby() == false
			   && super.canStart() != false;
	}

	@Override
	public void start() {
		this.isRunning = true;
		this.timer = 0;
		super.start();
	}

	@Override
	public void tick() {
		if (this.hasReached()) {
			if (this.timer >= COLLECTING_TIME) {
				this.shakeOffGlowBerries();
			} else {
				++this.timer;
			}
		} else {
			if (this.glare.getRandom().nextFloat() < 0.05F) {
				this.glare.playAmbientSound();
			}
		}

		super.tick();
	}

	@Override
	public void stop() {
		this.isRunning = false;
	}

	private void shakeOffGlowBerries() {
		if (
			this.glare.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) == false
			|| FriendsAndFoes.getConfig().enableGlareGriefing == false
		) {
			return;
		}

		BlockState blockState = this.glare.getWorld().getBlockState(this.targetPos);

		if (CaveVines.hasBerries(blockState) == false) {
			return;
		}

		CaveVines.pickBerries(
			blockState,
			this.glare.getWorld(),
			this.targetPos
		);
	}
}
