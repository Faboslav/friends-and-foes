package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockBehaviour.class, priority = 1001)
public abstract class LightningRodAbstractBlockMixin
{
	@Inject(
		method = "isRandomlyTicking",
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
		ServerLevel world,
		BlockPos pos,
		RandomSource random,
		CallbackInfo ci
	) {
	}

	@Inject(
		method = "useWithoutItem",
		at = @At("HEAD"),
		cancellable = true
	)
	public void friendsandfoes_onUse(
		BlockState state,
		Level world,
		BlockPos pos,
		Player player,
		BlockHitResult hit,
		CallbackInfoReturnable<InteractionResult> cir
	) {
	}
}
