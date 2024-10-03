package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractBlock.class, priority = 1001)
public abstract class LightningRodAbstractBlockMixin
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

	@Inject(
		method = "randomTick",
		at = @At("HEAD"),
		cancellable = true
	)
	public void friendsandfoes_randomTick(
		BlockState state,
		ServerWorld world,
		BlockPos pos,
		Random random,
		CallbackInfo ci
	) {
	}

	@Inject(
		method = "onUse",
		at = @At("HEAD"),
		cancellable = true
	)
	public void friendsandfoes_onUse(
		BlockState state,
		World world,
		BlockPos pos,
		PlayerEntity player,
		BlockHitResult hit,
		CallbackInfoReturnable<ActionResult> cir
	) {
	}
}
