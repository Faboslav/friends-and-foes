package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.block.Oxidizable;
import com.faboslav.friendsandfoes.init.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
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
		method = "powerLightningRod",
		at = @At("HEAD")
	)
	public void friendsAndFoesPowerLightningRod(CallbackInfo ci) {
		BlockPos blockPos = this.getAffectedBlockPos();
		BlockState blockState = this.world.getBlockState(blockPos);

		if (blockState.isIn(ModTags.LIGHTNING_RODS)) {
			((LightningRodBlock) blockState.getBlock()).setPowered(blockState, this.world, blockPos);
		}
	}

	@Inject(
		method = "cleanOxidation",
		at = @At("HEAD")
	)
	private static void friendsAndFoesCleanOxidation(
		World world,
		BlockPos pos,
		CallbackInfo ci
	) {
		BlockState blockState = world.getBlockState(pos);
		BlockPos blockPos;
		BlockState blockState2;

		if (blockState.isIn(ModTags.LIGHTNING_RODS)) {
			blockPos = pos.offset(blockState.get(LightningRodBlock.FACING).getOpposite());
			blockState2 = world.getBlockState(blockPos);
		} else {
			blockPos = pos;
			blockState2 = blockState;
		}

		if (blockState2.getBlock() instanceof Oxidizable) {
			world.setBlockState(blockPos, Oxidizable.getUnaffectedOxidationState(world.getBlockState(blockPos)));
			BlockPos.Mutable mutable = pos.mutableCopy();
			int i = world.random.nextInt(3) + 3;

			for (int j = 0; j < i; ++j) {
				int k = world.random.nextInt(8) + 1;
				LightningEntity.cleanOxidationAround(world, blockPos, mutable, k);
			}
		}
	}

	public void customCleanOxidationAround(World world, BlockPos pos, BlockPos.Mutable mutablePos, int count) {
		mutablePos.set(pos);

		for(int i = 0; i < count; ++i) {
			Optional<BlockPos> optional = cleanOxidationAround(world, mutablePos);
			if (!optional.isPresent()) {
				break;
			}

			mutablePos.set((Vec3i)optional.get());
		}

	}

	private static Optional<BlockPos> cleanOxidationAround(World world, BlockPos pos) {
		Iterator var2 = BlockPos.iterateRandomly(world.random, 10, pos, 1).iterator();

		BlockPos blockPos;
		BlockState blockState;
		do {
			if (!var2.hasNext()) {
				return Optional.empty();
			}

			blockPos = (BlockPos)var2.next();
			blockState = world.getBlockState(blockPos);
		} while(!(blockState.getBlock() instanceof net.minecraft.block.Oxidizable));

		net.minecraft.block.Oxidizable.getDecreasedOxidationState(blockState).ifPresent((state) -> {
			world.setBlockState(blockPos, state);
		});
		world.syncWorldEvent(3002, blockPos, -1);
		return Optional.of(blockPos);
	}
}
