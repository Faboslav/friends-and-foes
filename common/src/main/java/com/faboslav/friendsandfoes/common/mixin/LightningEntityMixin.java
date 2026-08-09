package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;

//? if <=1.21.8 {
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.world.entity.Entity;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Unique;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import com.faboslav.friendsandfoes.common.block.FriendsAndFoesOxidizable;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;

@Mixin(LightningBolt.class)
public abstract class LightningEntityMixin extends Entity
{
	public LightningEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Shadow
	protected abstract BlockPos getStrikePosition();

	@WrapMethod(
		method = "powerLightningRod"
	)
	private void friendsandfoes$powerLightningRod(
		Operation<Void> original
	) {
		original.call();

		BlockPos blockPos = this.getStrikePosition();
		BlockState blockState = this.level().getBlockState(blockPos);
		Block block = blockState.getBlock();

		if (blockState.is(FriendsAndFoesTags.LIGHTNING_RODS)) {
			((LightningRodBlock)block).onLightningStrike(blockState, this.level(), blockPos);
		}
	}

	@WrapMethod(
		method = "clearCopperOnLightningStrike"
	)
	private static void friendsandfoes$clearCopperOnLightningStrike(
		Level level,
		BlockPos blockPos,
		Operation<Void> original
	) {
		original.call(level, blockPos);

		BlockState blockState = level.getBlockState(blockPos);
		Block block = blockState.getBlock();

		if (blockState.is(FriendsAndFoesTags.LIGHTNING_RODS) || blockState.is(FriendsAndFoesTags.COPPER_BUTTONS)) {
			level.setBlockAndUpdate(blockPos, FriendsAndFoesOxidizable.getFirst(level.getBlockState(blockPos)));
		}

		if((block instanceof WeatheringCopper && !blockState.is(FriendsAndFoesTags.LIGHTNING_RODS)) || blockState.is(FriendsAndFoesTags.COPPER_BUTTONS)) {
			BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
			int i = level.random.nextInt(3) + 3;

			for(int j = 0; j < i; ++j) {
				int k = level.random.nextInt(8) + 1;
				friendsandfoes$randomWalkCleaningCopper(level, blockPos, mutableBlockPos, k);
			}

		}
	}

	@Unique
	private static void friendsandfoes$randomWalkCleaningCopper(Level level, BlockPos pos, BlockPos.MutableBlockPos mutable, int steps) {
		mutable.set(pos);

		for(int i = 0; i < steps; ++i) {
			Optional<BlockPos> optional = friendsandfoes$randomStepCleaningCopper(level, mutable);
			if (optional.isEmpty()) {
				break;
			}

			mutable.set(optional.get());
		}

	}

	@Unique
	private static Optional<BlockPos> friendsandfoes$randomStepCleaningCopper(Level level, BlockPos pos) {
		for(BlockPos blockPos : BlockPos.randomInCube(level.random, 10, pos, 1)) {
			BlockState blockState = level.getBlockState(blockPos);
			Block block = blockState.getBlock();

			if (blockState.is(FriendsAndFoesTags.LIGHTNING_RODS) || blockState.is(FriendsAndFoesTags.COPPER_BUTTONS)) {
				FriendsAndFoesOxidizable.getPrevious(blockState).ifPresent((blockStatex) -> level.setBlockAndUpdate(blockPos, blockStatex));
				level.levelEvent(3002, blockPos, -1);
				return Optional.of(blockPos);
			}

			if (block instanceof WeatheringCopper) {
				WeatheringCopper.getPrevious(blockState).ifPresent((blockStatex) -> level.setBlockAndUpdate(blockPos, blockStatex));
				level.levelEvent(3002, blockPos, -1);
				return Optional.of(blockPos);
			}
		}

		return Optional.empty();
	}
}
//?} else {
/*@Mixin(LightningBolt.class)
public class LightningEntityMixin
{
}
*///?}