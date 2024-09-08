package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AbstractBlock.class, priority = 10000)
public abstract class LightningRodAbstractBlockMixin
{
	@Inject(
		method = "randomTick",
		at = @At("TAIL")
	)
	public void friendsandfoes_randomTick(
		BlockState state,
		ServerWorld world,
		BlockPos pos,
		Random random,
		CallbackInfo ci
	) {
	}
}
