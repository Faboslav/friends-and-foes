package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.block.FriendsAndFoesOxidizable;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(LightningBolt.class)
public abstract class LightningEntityMixin extends Entity
{
	public LightningEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Shadow
	protected abstract BlockPos getStrikePosition();

	@Inject(
		at = @At("TAIL"),
		method = "powerLightningRod"
	)
	private void friendsandfoes$powerLightningRod(
		CallbackInfo ci
	) {
		BlockPos blockPos = this.getStrikePosition();
		BlockState blockState = this.level().getBlockState(blockPos);

		if (blockState.is(FriendsAndFoesTags.LIGHTNING_RODS)) {
			this.level().setBlockAndUpdate(
				blockPos,
				FriendsAndFoesOxidizable.getFirst(this.level().getBlockState(blockPos))
			);

			((LightningRodBlock) this.level().getBlockState(blockPos).getBlock()).onLightningStrike(blockState, this.level(), blockPos);
		}
	}


	@Inject(
		at = @At("HEAD"),
		method = "randomWalkCleaningCopper"
	)
	private static void friendsandfoes$cleanOxidationAround(
		Level world,
		BlockPos pos,
		BlockPos.MutableBlockPos mutablePos,
		int count,
		CallbackInfo ci
	) {
		mutablePos.set(pos);

		for (int i = 0; i < count; ++i) {
			Optional<BlockPos> optional = friendsandfoes$cleanOxidationAround(world, mutablePos);
			if (!optional.isPresent()) {
				break;
			}

			mutablePos.set(optional.get());
		}
	}

	private static Optional<BlockPos> friendsandfoes$cleanOxidationAround(Level world, BlockPos pos) {
		Iterator var2 = BlockPos.randomInCube(world.getRandom(), 10, pos, 1).iterator();

		BlockPos blockPos;
		BlockState blockState;
		do {
			if (!var2.hasNext()) {
				return Optional.empty();
			}

			blockPos = (BlockPos) var2.next();
			blockState = world.getBlockState(blockPos);
		} while (!(blockState.getBlock() instanceof WeatheringCopper));

		BlockPos finalBlockPos = blockPos;
		FriendsAndFoesOxidizable.getPrevious(blockState).ifPresent((state) -> world.setBlockAndUpdate(finalBlockPos, state));

		world.levelEvent(3002, blockPos, -1);
		return Optional.of(blockPos);
	}
}
