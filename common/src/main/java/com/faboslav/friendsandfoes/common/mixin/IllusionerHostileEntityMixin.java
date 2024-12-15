package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Monster.class)
public abstract class IllusionerHostileEntityMixin extends PathfinderMob
{
	protected IllusionerHostileEntityMixin(EntityType<? extends PathfinderMob> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(
		at = @At("HEAD"),
		method = "shouldDropLoot",
		cancellable = true
	)
	protected void friendsandfoes_shouldDropLoot(CallbackInfoReturnable<Boolean> cir) {
	}

	@Inject(
		at = @At("HEAD"),
		method = "shouldDropExperience",
		cancellable = true
	)
	protected void friendsandfoes_shouldDropXp(CallbackInfoReturnable<Boolean> cir) {
	}
}
