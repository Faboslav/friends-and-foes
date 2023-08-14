package com.faboslav.friendsandfoes.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorseEntity.class)
public abstract class ZombieHorseAbstractHorseEntityMixin extends ZombieHorseAnimalEntityMixin
{
	protected ZombieHorseAbstractHorseEntityMixin(EntityType<? extends AbstractHorseEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(
		method = "tickMovement",
		at = @At("TAIL")
	)
	protected void friendsandfoes_tickMovement(CallbackInfo ci) {
	}
}
