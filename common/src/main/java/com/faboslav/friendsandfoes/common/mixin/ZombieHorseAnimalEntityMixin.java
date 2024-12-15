package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
	public void friendsandfoes_writeCustomDataToNbt(CompoundTag nbt, CallbackInfo ci) {
	}

	@Inject(
		method = "readAdditionalSaveData",
		at = @At("TAIL")
	)
	public void friendsandfoes_readCustomDataFromNbt(CompoundTag nbt, CallbackInfo ci) {
	}
}
