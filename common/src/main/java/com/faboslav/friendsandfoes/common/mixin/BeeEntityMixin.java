package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.ai.goal.bee.BeePollinateMoobloomGoal;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Bee.class)
public abstract class BeeEntityMixin extends Animal
{
	private BeePollinateMoobloomGoal friendsandfoes_pollinateMoobloomGoal;

	public BeeEntityMixin(
		EntityType<? extends Animal> entityType,
		Level world
	) {
		super(entityType, world);
	}

	@Inject(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V",
			ordinal = 3,
			shift = At.Shift.AFTER
		),
		method = "registerGoals"
	)
	private void friendsandfoes_addPollinateMoobloomGoal(CallbackInfo ci) {
		this.friendsandfoes_pollinateMoobloomGoal = new BeePollinateMoobloomGoal((Bee) (Object) this, (BeeEntityAccessor) this);
		this.goalSelector.addGoal(3, this.friendsandfoes_pollinateMoobloomGoal);
	}

	@Inject(
		method = "hurt",
		at = @At("HEAD")
	)
	public void friendsandfoes_tweakDamage(
		DamageSource source,
		float amount,
		CallbackInfoReturnable<Boolean> callbackInfo
	) {
		if (!this.isInvulnerableTo(source)) {
			if (
				!this.level().isClientSide()
				&& this.friendsandfoes_pollinateMoobloomGoal != null
			) {
				this.friendsandfoes_pollinateMoobloomGoal.cancel();
			}
		}
	}
}
