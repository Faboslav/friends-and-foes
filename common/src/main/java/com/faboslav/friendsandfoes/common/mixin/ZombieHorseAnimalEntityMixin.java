package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalEntity.class)
public abstract class ZombieHorseAnimalEntityMixin extends PassiveEntity
{
	protected ZombieHorseAnimalEntityMixin(EntityType<? extends AbstractHorseEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(
		method = "writeCustomDataToNbt",
		at = @At("TAIL")
	)
	public void friendsandfoes_writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
	}

	@Inject(
		method = "readCustomDataFromNbt",
		at = @At("TAIL")
	)
	public void friendsandfoes_readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
	}
}
