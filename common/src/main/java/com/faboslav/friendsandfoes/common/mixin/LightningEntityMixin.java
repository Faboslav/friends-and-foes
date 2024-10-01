package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.block.FriendsAndFoesOxidizable;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.block.Oxidizable;
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

	@Inject(
		at = @At("TAIL"),
		method = "powerLightningRod"
	)
	private void friendsandfoes$powerLightningRod(
		CallbackInfo ci
	) {
		BlockPos blockPos = this.getAffectedBlockPos();
		BlockState blockState = this.getWorld().getBlockState(blockPos);

		if (blockState.isIn(FriendsAndFoesTags.LIGHTNING_RODS)) {
			this.getWorld().setBlockState(
				blockPos,
				Oxidizable.getUnaffectedOxidationState(this.getWorld().getBlockState(blockPos))
			);

			((LightningRodBlock)this.getWorld().getBlockState(blockPos).getBlock()).setPowered(blockState, this.getWorld(), blockPos);
		}
	}


	@Inject(
		at = @At("HEAD"),
		method = "cleanOxidationAround(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos$Mutable;I)V"
	)
	private static void friendsandfoes$cleanOxidationAround(
		World world,
		BlockPos pos,
		BlockPos.Mutable mutablePos,
		int count,
		CallbackInfo ci
	) {
		mutablePos.set(pos);

		for(int i = 0; i < count; ++i) {
			Optional<BlockPos> optional = friendsandfoes$cleanOxidationAround(world, mutablePos);
			if (!optional.isPresent()) {
				break;
			}

			mutablePos.set(optional.get());
		}
	}

	private static Optional<BlockPos> friendsandfoes$cleanOxidationAround(World world, BlockPos pos) {
		Iterator var2 = BlockPos.iterateRandomly(world.getRandom(), 10, pos, 1).iterator();

		BlockPos blockPos;
		BlockState blockState;
		do {
			if (!var2.hasNext()) {
				return Optional.empty();
			}

			blockPos = (BlockPos)var2.next();
			blockState = world.getBlockState(blockPos);
		} while(!(blockState.getBlock() instanceof Oxidizable));

		BlockPos finalBlockPos = blockPos;
		FriendsAndFoesOxidizable.getDecreasedOxidationState(blockState).ifPresent((state) -> world.setBlockState(finalBlockPos, state));

		world.syncWorldEvent(3002, blockPos, -1);
		return Optional.of(blockPos);
	}
}
