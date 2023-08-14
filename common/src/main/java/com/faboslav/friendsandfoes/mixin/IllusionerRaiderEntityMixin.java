package com.faboslav.friendsandfoes.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RaiderEntity.class)
public abstract class IllusionerRaiderEntityMixin extends IllusionerHostileEntityMixin
{
	protected IllusionerRaiderEntityMixin(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(
		method = "damage",
		at = @At("HEAD"),
		cancellable = true
	)
	public void friendsandfoes_damage(
		DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir
	) {
	}
}
