package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.block.Oxidizable;
import com.faboslav.friendsandfoes.util.WaxableBlocksMap;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin
{
	@ModifyVariable(
		method = "useOnBlock",
		ordinal = 1,
		at = @At(
			value = "STORE"
		)
	)
	private Optional<BlockState> friendsandfoes_modifyOxidizedBlock(
		Optional<BlockState> originalBlockState,
		ItemUsageContext context
	) {
		if (originalBlockState.isPresent()) {
			return originalBlockState;
		}

		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);

		return Oxidizable.getDecreasedOxidationState(blockState);
	}

	@ModifyVariable(
		method = "useOnBlock",
		ordinal = 2,
		at = @At(
			value = "STORE"
		)
	)
	private Optional<BlockState> friendsandfoes_modifyWaxedBlock(
		Optional<BlockState> originalBlockState,
		ItemUsageContext context
	) {
		if (originalBlockState.isPresent()) {
			return originalBlockState;
		}

		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);

		return Optional.ofNullable(WaxableBlocksMap.WAXED_TO_UNWAXED_BLOCKS.get().get(blockState.getBlock())).map((block) -> block.getStateWithProperties(blockState));
	}
}
