package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.21.6 {
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
//?} else {
/*import net.minecraft.nbt.CompoundTag;
 *///?}

@Mixin(LivingEntity.class)
public abstract class BlazeLivingEntityMixin extends Entity
{
	public BlazeLivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Inject(
		method = "addAdditionalSaveData",
		at = @At("TAIL")
	)
	//? if >=1.21.6 {
	public void friendsandfoes_writeCustomDataToNbt(ValueOutput nbt, CallbackInfo ci)
	//?} else {
	/*public void friendsandfoes_writeCustomDataToNbt(CompoundTag nbt, CallbackInfo ci)
	*///?}
	{
	}

	@Inject(
		method = "readAdditionalSaveData",
		at = @At("TAIL")
	)
	//? if >=1.21.6 {
	public void friendsandfoes_writeCustomDataToNbt(ValueInput nbt, CallbackInfo ci)
	//?} else {
	/*public void friendsandfoes_writeCustomDataFromNbt(CompoundTag nbt, CallbackInfo ci)
	*///?}
	{
	}

	@Inject(
		method = "die",
		at = @At("HEAD")
	)
	public void friendsandfoes_onDeath(DamageSource damageSource, CallbackInfo ci) {
	}
}
