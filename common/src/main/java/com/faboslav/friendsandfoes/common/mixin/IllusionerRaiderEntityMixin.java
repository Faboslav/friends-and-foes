package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Raider.class)
public abstract class IllusionerRaiderEntityMixin extends IllusionerHostileEntityMixin
{
	protected IllusionerRaiderEntityMixin(EntityType<? extends PathfinderMob> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(
		at = @At("HEAD"),
		//? >=1.21.3 {
		method = "hurtServer",
		//?} else {
		/*method = "hurt",
		*///?}
		cancellable = true
	)
	public void friendsandfoes_damage(
		/*? >=1.21.3 {*/ServerLevel level,/*?}*/
		DamageSource damageSource,
		float amount,
		CallbackInfoReturnable<Boolean> cir
	) {
	}
}
