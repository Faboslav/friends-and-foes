package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Block.class, priority = 1001)
public abstract class LightningRodBlockBlockMixin extends LightningRodAbstractBlockMixin
{
	@Inject(
		method = "hasRandomTicks",
		at = @At("HEAD"),
		cancellable = true
	)
	public void friendsandfoes_hasRandomTicks(
		BlockState state, CallbackInfoReturnable<Boolean> cir
	) {
	}
}
