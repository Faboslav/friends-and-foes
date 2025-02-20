package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.ai.goal.bee.BeePollinateMoobloomGoal;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? >=1.21.3 {
import net.minecraft.server.level.ServerLevel;
//?}

@Mixin(Bee.class)
public abstract class BeeEntityMixin extends Animal
{
	@Unique
	private BeePollinateMoobloomGoal friendsandfoes$pollinateMoobloomGoal;

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
		this.friendsandfoes$pollinateMoobloomGoal = new BeePollinateMoobloomGoal((Bee) (Object) this, (BeeEntityAccessor) this);
		this.goalSelector.addGoal(3, this.friendsandfoes$pollinateMoobloomGoal);
	}

	//? >=1.21.3 {
	@Inject(
		method = "hurtServer",
		at = @At("HEAD")
	)
	public void friendsandfoes_tweakDamage(
		ServerLevel level,
		DamageSource damageSource,
		float amount,
		CallbackInfoReturnable<Boolean> cir
	) {
	//?} else {
	/*@Inject(
		method = "hurt",
		at = @At("HEAD")
	)
	public void friendsandfoes_tweakDamage(
		DamageSource damageSource,
		float amount,
		CallbackInfoReturnable<Boolean> callbackInfo
	) {
	*///?}
		if (!this.isInvulnerableTo(/*? >=1.21.3 {*/level, /*?}*/damageSource))
		{
			if (
				!this.level().isClientSide()
				&& this.friendsandfoes$pollinateMoobloomGoal != null
			) {
				this.friendsandfoes$pollinateMoobloomGoal.cancel();
			}
		}
	}
}
