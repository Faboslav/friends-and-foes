package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityTypes;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TemptGoal.class)
public abstract class TemptGoalMixin extends Goal
{
	@Final
	@Shadow
	protected PathAwareEntity mob;

	@Final
	@Shadow
	private TargetPredicate predicate;

	@Final
	@Shadow
	protected abstract boolean canBeScared();

	@Nullable
	private TuffGolemEntity closestTuffGolem;
	private double lastTuffGolemX;
	private double lastTuffGolemY;
	private double lastTuffGolemZ;
	private double lastTuffGolemPitch;
	private double lastTuffGolemYaw;

	@Inject(
		method = "canStart",
		at = @At("RETURN"),
		cancellable = true
	)
	private void friendsandfoes_canStart(
		CallbackInfoReturnable<Boolean> cir
	) {
		if(cir.getReturnValue() == false) {
			cir.setReturnValue(this.friendsandfoes_canStartWithReturn());
		}
	}

	private boolean friendsandfoes_canStartWithReturn()
	{
		this.closestTuffGolem = this.mob.world.getClosestEntity(
			this.mob.world.getEntitiesByClass(
				TuffGolemEntity.class,
				this.mob.getBoundingBox().expand(16.0F, 3.0D, 16.0F), (livingEntity) -> {
					return true;
				}),
			this.predicate,
			this.mob,
			this.mob.getX(),
			this.mob.getY(),
			this.mob.getZ()
		);

		return this.closestTuffGolem != null;
	}

	@Inject(
		method = "shouldContinue",
		at = @At("RETURN"),
		cancellable = true
	)
	private void friendsandfoes_shouldContinue(
		CallbackInfoReturnable<Boolean> cir
	) {
		if (cir.getReturnValue() == false) {
			if (this.canBeScared()) {
				if (this.mob.squaredDistanceTo(this.closestTuffGolem) < 36.0) {
					if (this.closestTuffGolem.squaredDistanceTo(this.lastTuffGolemX, this.lastTuffGolemY, this.lastTuffGolemZ) > 0.010000000000000002) {
						cir.setReturnValue(false);
					}

					if (Math.abs((double)this.closestTuffGolem.getPitch() - this.lastTuffGolemPitch) > 5.0 || Math.abs((double)this.closestTuffGolem.getYaw() - this.lastTuffGolemYaw) > 5.0) {
						cir.setReturnValue(false);
					}
				} else {
					this.lastTuffGolemX = this.closestTuffGolem.getX();
					this.lastTuffGolemY = this.closestTuffGolem.getY();
					this.lastTuffGolemZ = this.closestTuffGolem.getZ();
				}

				this.lastTuffGolemPitch = this.closestTuffGolem.getPitch();
				this.lastTuffGolemYaw = this.closestTuffGolem.getYaw();
			}

			cir.setReturnValue(this.friendsandfoes_canStartWithReturn());
		}
	}
}
