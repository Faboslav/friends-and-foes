package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
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

@Mixin(Animal.class)
public abstract class ZombieHorseAnimalEntityMixin extends AgeableMob
{
	protected ZombieHorseAnimalEntityMixin(EntityType<? extends AbstractHorse> entityType, Level world) {
		super(entityType, world);
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
	public void friendsandfoes_readCustomDataFromNbt(ValueInput nbt, CallbackInfo ci)
	//?} else {
	/*public void friendsandfoes_readCustomDataFromNbt(CompoundTag nbt, CallbackInfo ci)
	*///?}
	{
	}
}
