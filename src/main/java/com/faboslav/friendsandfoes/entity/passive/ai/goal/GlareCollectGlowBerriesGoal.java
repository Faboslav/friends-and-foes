package com.faboslav.friendsandfoes.entity.passive.ai.goal;

import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import com.faboslav.friendsandfoes.registry.SoundRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.CaveVines;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldView;

public class GlareCollectGlowBerriesGoal extends MoveToTargetPosGoal
{
	private final GlareEntity glareEntity;
	private static final int COLLECTING_TIME = 40;
	private static final int RANGE = 12;
	private static final int MAX_Y_DIFFERENCE = 12;
	protected int timer;

	public GlareCollectGlowBerriesGoal(GlareEntity glareEntity) {
		super(
			glareEntity,
			glareEntity.getMovementSpeed(),
			RANGE,
			MAX_Y_DIFFERENCE
		);

		this.glareEntity = glareEntity;
	}

	public double getDesiredSquaredDistanceToTarget() {
		return 2.0D;
	}

	public boolean shouldResetPath() {
		return this.tryingTime % 100 == 0;
	}

	protected boolean isTargetPos(WorldView world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		return CaveVines.hasBerries(blockState);
	}

	@Override
	public boolean canStart() {
		if (this.glareEntity.isLeashed()) {
			return false;
		}

		return super.canStart();
	}

	@Override
	public void start() {
		this.timer = 0;
		super.start();
	}

	@Override
	public void tick() {
		if (this.hasReached()) {
			if (this.timer >= COLLECTING_TIME) {
				this.collectGlowBerries();
			} else {
				++this.timer;
			}
		} else {
			if (this.glareEntity.getRandom().nextFloat() < 0.05F) {
				this.glareEntity.playSound(SoundRegistry.ENTITY_GLARE_AMBIENT, 1.0F, 1.0F);
			}
		}

		super.tick();
	}

	private void collectGlowBerries() {
		if (this.glareEntity.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
			BlockState blockState = this.glareEntity.world.getBlockState(this.targetPos);

			if (CaveVines.hasBerries(blockState)) {
				CaveVines.pickBerries(
					blockState,
					this.glareEntity.world,
					this.targetPos
				);
			}
		}
	}
}
