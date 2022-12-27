package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.block.Oxidizable;
import com.faboslav.friendsandfoes.tag.FriendsAndFoesTags;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Optional;

@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin extends Entity
{
	public LightningEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Shadow
	protected abstract BlockPos getAffectedBlockPos();

	@ModifyExpressionValue(
		method = "powerLightningRod",
		at = @At(
			value = "INVOKE",
			target = "net/minecraft/block/BlockState.isOf (Lnet/minecraft/block/Block;)Z"
		)
	)
	private boolean friendsandfoes_expandPowerLightningRodIsLightningRodCondition(boolean original) {
		BlockPos blockPos = this.getAffectedBlockPos();
		BlockState blockState = this.world.getBlockState(blockPos);

		return original || blockState.isIn(FriendsAndFoesTags.LIGHTNING_RODS);
	}

	@Inject(
		at = @At(
			value = "INVOKE",
			target = "net/minecraft/entity/LightningEntity.powerLightningRod ()V",
			ordinal = 0,
			shift = At.Shift.AFTER
		),
		method = "tick()V"
	)
	private void friendsandfoes_cleanLightningRodOxidation(CallbackInfo ci) {
		BlockPos blockPos = this.getAffectedBlockPos();
		BlockState blockState = this.getWorld().getBlockState(blockPos);

		if (blockState.isIn(FriendsAndFoesTags.LIGHTNING_RODS)) {
			this.getWorld().setBlockState(
				blockPos,
				Oxidizable.getUnaffectedOxidationState(this.getWorld().getBlockState(blockPos))
			);
		}
	}

	@ModifyExpressionValue(
		method = "cleanOxidation",
		at = @At(
			value = "INVOKE",
			target = "net/minecraft/block/BlockState.isOf (Lnet/minecraft/block/Block;)Z"
		)
	)
	private static boolean friendsandfoes_expandCleanOxidationRodIsLightningRodCondition(
		boolean original,
		World world,
		BlockPos pos
	) {
		BlockState blockState = world.getBlockState(pos);

		return original || blockState.isIn(FriendsAndFoesTags.LIGHTNING_RODS);
	}

	@Inject(
		at = @At(
			value = "INVOKE",
			target = "net/minecraft/block/Oxidizable.getDecreasedOxidationState (Lnet/minecraft/block/BlockState;)Ljava/util/Optional;",
			ordinal = 0,
			shift = At.Shift.AFTER
		),
		method = "cleanOxidationAround(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Ljava/util/Optional;",
		locals = LocalCapture.CAPTURE_FAILSOFT
	)
	private static void friendsandfoes_decreaseCustomOxidationStates(
		World world,
		BlockPos pos,
		CallbackInfoReturnable<Optional<BlockPos>> cir,
		Iterator<BlockPos> blockPosIterator,
		BlockPos blockPos,
		BlockState blockState
	) {
		Oxidizable.getDecreasedOxidationState(blockState).ifPresent((state) -> {
			world.setBlockState(blockPos, state);
		});
	}
}
