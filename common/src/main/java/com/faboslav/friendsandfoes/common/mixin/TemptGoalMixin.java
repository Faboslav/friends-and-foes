package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

//? if >=1.21.6 {
import net.minecraft.world.entity.Mob;
//?} else {
/*import net.minecraft.world.entity.PathfinderMob;
*///?}

@Mixin(TemptGoal.class)
public abstract class TemptGoalMixin
{
	@Shadow
	@Final
	protected double speedModifier;

	@Shadow
	private boolean isRunning;

	@Final
	@Shadow
	//? if >=1.21.6 {
	protected Mob mob;
	//?} else {
	/*protected PathfinderMob mob;
	*///?}

	@Final
	@Shadow
	private TargetingConditions targetingConditions;

	@Final
	@Shadow
	protected abstract boolean canScare();

	@Unique
	@Nullable
	private TuffGolemEntity friendsandfoes$closestTuffGolem;

	@Unique
	private double friendsandfoes$lastTuffGolemX;

	@Unique
	private double friendsandfoes$lastTuffGolemY;

	@Unique
	private double friendsandfoes$lastTuffGolemZ;

	@Unique
	private double friendsandfoes$lastTuffGolemPitch;

	@Unique
	private double friendsandfoes$lastTuffGolemYaw;

	@Unique
	private boolean friendsandfoes$canStartWithReturn() {
		this.friendsandfoes$closestTuffGolem = (/*? if >=1.21.3 {*/(ServerLevel)/*?}*/this.mob.level()).getNearestEntity(
			TuffGolemEntity.class,
			this.targetingConditions,
			this.mob,
			this.mob.getX(),
			this.mob.getY(),
			this.mob.getZ(),
			this.mob.getBoundingBox().inflate(16.0F, 3.0D, 16.0F)
		);

		return this.friendsandfoes$closestTuffGolem != null;
	}

	@ModifyReturnValue(
		method = "canUse",
		at = @At("RETURN")
	)
	private boolean friendsandfoes$canStart(
		boolean original
	) {
		if(original) {
			return original;
		}

		return this.friendsandfoes$canStartWithReturn();
	}

	@ModifyReturnValue(
		method = "canContinueToUse",
		at = @At("RETURN")
	)
	private boolean friendsandfoes$canContinueToUse(
		boolean original
	) {
		if(original || this.friendsandfoes$closestTuffGolem == null || !this.canScare()) {
			return original;
		}

		if (this.mob.distanceToSqr(this.friendsandfoes$closestTuffGolem) < 36.0) {
			if (this.friendsandfoes$closestTuffGolem.distanceToSqr(this.friendsandfoes$lastTuffGolemX, this.friendsandfoes$lastTuffGolemY, this.friendsandfoes$lastTuffGolemZ) > 0.010000000000000002) {
				return false;
			}

			if (Math.abs((double) this.friendsandfoes$closestTuffGolem.getXRot() - this.friendsandfoes$lastTuffGolemPitch) > 5.0 || Math.abs((double) this.friendsandfoes$closestTuffGolem.getYRot() - this.friendsandfoes$lastTuffGolemYaw) > 5.0) {
				return false;
			}
		} else {
			this.friendsandfoes$lastTuffGolemX = this.friendsandfoes$closestTuffGolem.getX();
			this.friendsandfoes$lastTuffGolemY = this.friendsandfoes$closestTuffGolem.getY();
			this.friendsandfoes$lastTuffGolemZ = this.friendsandfoes$closestTuffGolem.getZ();
		}

		this.friendsandfoes$lastTuffGolemPitch = this.friendsandfoes$closestTuffGolem.getXRot();
		this.friendsandfoes$lastTuffGolemYaw = this.friendsandfoes$closestTuffGolem.getYRot();

		return friendsandfoes$canStartWithReturn();
	}

	@WrapMethod(
		method = "start"
	)
	public void friendsandfoes$start(Operation<Void> original) {
		if (this.friendsandfoes$closestTuffGolem == null) {
			original.call();
		}

		this.friendsandfoes$lastTuffGolemX = this.friendsandfoes$closestTuffGolem.getX();
		this.friendsandfoes$lastTuffGolemY = this.friendsandfoes$closestTuffGolem.getY();
		this.friendsandfoes$lastTuffGolemZ = this.friendsandfoes$closestTuffGolem.getZ();
		this.isRunning = true;
	}

	@WrapMethod(
		method = "stop"
	)
	private void friendsandfoes$stop(
		Operation<Void> original
	) {
		original.call();

		this.friendsandfoes$closestTuffGolem = null;
	}

	@WrapMethod(
		method = "tick"
	)
	public void friendsandfoes$tick(Operation<Void> original) {
		if (this.friendsandfoes$closestTuffGolem == null) {
			original.call();
		}

		this.mob.getLookControl().setLookAt(this.friendsandfoes$closestTuffGolem, (float) (this.mob.getMaxHeadYRot() + 20), (float) this.mob.getMaxHeadXRot());

		if (this.mob.distanceToSqr(this.friendsandfoes$closestTuffGolem) < 6.25) {
			this.mob.getNavigation().stop();
		} else {
			this.mob.getNavigation().moveTo(this.friendsandfoes$closestTuffGolem, this.speedModifier);
		}
	}
}
