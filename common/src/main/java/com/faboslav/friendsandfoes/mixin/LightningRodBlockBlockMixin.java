package com.faboslav.friendsandfoes.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class LightningRodBlockBlockMixin extends LightningRodAbstractBlockMixin
{
	@Inject(
		method = "hasRandomTicks",
		at = @At("TAIL"),
		cancellable = true
	)
	public void friendsandfoes_hasRandomTicks(
		BlockState state, CallbackInfoReturnable<Boolean> cir
	) {
	}
}
