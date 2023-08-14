package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.entity.ai.goal.bee.BeePollinateMoobloomGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends AnimalEntity
{
	private BeePollinateMoobloomGoal friendsandfoes_pollinateMoobloomGoal;

	public BeeEntityMixin(
		EntityType<? extends AnimalEntity> entityType,
		World world
	) {
		super(entityType, world);
	}

	@Inject(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V",
			ordinal = 3,
			shift = At.Shift.AFTER
		),
		method = "initGoals"
	)
	private void friendsandfoes_addPollinateMoobloomGoal(CallbackInfo ci) {
		this.friendsandfoes_pollinateMoobloomGoal = new BeePollinateMoobloomGoal((BeeEntity) (Object) this, (BeeEntityAccessor) this);
		this.goalSelector.add(3, this.friendsandfoes_pollinateMoobloomGoal);
	}

	@Inject(
		method = "damage",
		at = @At("HEAD")
	)
	public void friendsandfoes_tweakDamage(
		DamageSource source,
		float amount,
		CallbackInfoReturnable<Boolean> callbackInfo
	) {
		if (!this.isInvulnerableTo(source)) {
			if (
				!this.getWorld().isClient()
				&& this.friendsandfoes_pollinateMoobloomGoal != null
			) {
				this.friendsandfoes_pollinateMoobloomGoal.cancel();
			}
		}
	}
}
