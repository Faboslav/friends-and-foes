package com.faboslav.friendsandfoes.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.entity.LivingEntity;

//? if >= 1.21.9 {
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

@Mixin(LivingEntity.class)
public abstract class MannequinLivingEntityMixin extends MannequinEntityMixin
{
	@Shadow
	public abstract float getVoicePitch();

	@Shadow
	protected abstract float getSoundVolume();

	@WrapMethod(
		method = "readAdditionalSaveData"
	)
	protected void friendsandfoes$readAdditionalSaveData(ValueInput valueInput, Operation<Void> original) {
		original.call(valueInput);
	}

	@WrapMethod(
		method = "addAdditionalSaveData"
	)
	protected void friendsandfoes$addAdditionalSaveData(ValueOutput valueOutput, Operation<Void> original) {
		original.call(valueOutput);
	}

	@WrapMethod(
		method = "aiStep"
	)
	protected void friendsandfoes$aiStep(Operation<Void> original) {
		original.call();
	}

	@WrapMethod(
		method = "hurtServer"
	)
	protected boolean friendsandfoes$mannequinHurtServer(
		ServerLevel serverLevel, DamageSource damageSource, float f, Operation<Boolean> original
	) {
		return original.call(serverLevel, damageSource, f);
	}
}
//?} else {
/*@Mixin(LivingEntity.class)
public abstract class MannequinLivingEntityMixin
{
}
*///?}