package com.faboslav.friendsandfoes.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HostileEntity.class)
public abstract class IllusionerHostileEntityMixin extends PathAwareEntity
{
	protected IllusionerHostileEntityMixin(EntityType<? extends PathAwareEntity> entityType, World world) {
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
		method = "shouldDropXp",
		cancellable = true
	)
	protected void friendsandfoes_shouldDropXp(CallbackInfoReturnable<Boolean> cir) {
	}
}
