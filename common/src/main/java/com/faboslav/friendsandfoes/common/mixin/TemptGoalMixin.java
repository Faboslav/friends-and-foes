package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TemptGoal.class)
public abstract class TemptGoalMixin
{
	@Shadow
	@Final
	private double speedModifier;

	@Shadow
	private boolean isRunning;

	@Final
	@Shadow
	protected PathfinderMob mob;

	@Final
	@Shadow
	private TargetingConditions targetingConditions;

	@Final
	@Shadow
	protected abstract boolean canScare();

	@Nullable
	private TuffGolemEntity closestTuffGolem;
	private double lastTuffGolemX;
	private double lastTuffGolemY;
	private double lastTuffGolemZ;
	private double lastTuffGolemPitch;
	private double lastTuffGolemYaw;

	@Inject(
		method = "canUse",
		at = @At("RETURN"),
		cancellable = true
	)
	private void friendsandfoes_canStart(
		CallbackInfoReturnable<Boolean> cir
	) {
		if (!cir.getReturnValue()) {
			cir.setReturnValue(this.friendsandfoes_canStartWithReturn());
		}
	}

	private boolean friendsandfoes_canStartWithReturn() {
		this.closestTuffGolem = this.mob.level().getNearestEntity(
			this.mob.level().getEntitiesOfClass(
				TuffGolemEntity.class,
				this.mob.getBoundingBox().inflate(16.0F, 3.0D, 16.0F), (livingEntity) -> {
					return true;
				}),
			this.targetingConditions,
			this.mob,
			this.mob.getX(),
			this.mob.getY(),
			this.mob.getZ()
		);

		return this.closestTuffGolem != null;
	}

	@Inject(
		method = "canContinueToUse",
		at = @At("RETURN"),
		cancellable = true
	)
	private void friendsandfoes_shouldContinue(
		CallbackInfoReturnable<Boolean> cir
	) {
		if (!cir.getReturnValue() && this.closestTuffGolem != null) {
			if (this.canScare()) {
				if (this.mob.distanceToSqr(this.closestTuffGolem) < 36.0) {
					if (this.closestTuffGolem.distanceToSqr(this.lastTuffGolemX, this.lastTuffGolemY, this.lastTuffGolemZ) > 0.010000000000000002) {
						cir.setReturnValue(false);
					}

					if (Math.abs((double) this.closestTuffGolem.getXRot() - this.lastTuffGolemPitch) > 5.0 || Math.abs((double) this.closestTuffGolem.getYRot() - this.lastTuffGolemYaw) > 5.0) {
						cir.setReturnValue(false);
					}
				} else {
					this.lastTuffGolemX = this.closestTuffGolem.getX();
					this.lastTuffGolemY = this.closestTuffGolem.getY();
					this.lastTuffGolemZ = this.closestTuffGolem.getZ();
				}

				this.lastTuffGolemPitch = this.closestTuffGolem.getXRot();
				this.lastTuffGolemYaw = this.closestTuffGolem.getYRot();
			}

			cir.setReturnValue(this.friendsandfoes_canStartWithReturn());
		}
	}

	@Inject(
		method = "start",
		at = @At("HEAD"),
		cancellable = true
	)
	public void friendsandfoes_start(CallbackInfo ci) {
		if (this.closestTuffGolem != null) {
			this.lastTuffGolemX = this.closestTuffGolem.getX();
			this.lastTuffGolemY = this.closestTuffGolem.getY();
			this.lastTuffGolemZ = this.closestTuffGolem.getZ();
			this.isRunning = true;
			ci.cancel();
		}
	}

	@Inject(
		at = @At("TAIL"),
		method = "stop"
	)
	private void friendsandfoes_stop(
		CallbackInfo ci
	) {
		this.closestTuffGolem = null;
	}

	@Inject(
		at = @At("HEAD"),
		method = "tick",
		cancellable = true)
	public void friendsandfoes_tick(CallbackInfo ci) {
		if (this.closestTuffGolem != null) {
			this.mob.getLookControl().setLookAt(this.closestTuffGolem, (float) (this.mob.getMaxHeadYRot() + 20), (float) this.mob.getMaxHeadXRot());

			if (this.mob.distanceToSqr(this.closestTuffGolem) < 6.25) {
				this.mob.getNavigation().stop();
			} else {
				this.mob.getNavigation().moveTo(this.closestTuffGolem, this.speedModifier);
			}

			ci.cancel();
		}
	}
}
