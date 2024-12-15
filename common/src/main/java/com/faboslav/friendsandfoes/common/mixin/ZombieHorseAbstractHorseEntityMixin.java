package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorse.class)
public abstract class ZombieHorseAbstractHorseEntityMixin extends ZombieHorseAnimalEntityMixin
{
	protected ZombieHorseAbstractHorseEntityMixin(EntityType<? extends AbstractHorse> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(
		method = "aiStep",
		at = @At("TAIL")
	)
	protected void friendsandfoes_tickMovement(CallbackInfo ci) {
	}
}
