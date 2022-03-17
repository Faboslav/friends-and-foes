package com.faboslav.friendsandfoes.mixin;

import java.util.Optional;
import java.util.function.Supplier;

import com.faboslav.friendsandfoes.block.Oxidizable;
import com.faboslav.friendsandfoes.init.ModBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(AxeItem.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AxeItemMixin
{
	private static final Supplier<BiMap<Block, Block>> WAXED_TO_UNWAXED_BLOCKS = Suppliers.memoize(() -> {
		return (BiMap) ImmutableBiMap.builder()
			.put(ModBlocks.WAXED_COPPER_BUTTON.get(), ModBlocks.COPPER_BUTTON.get())
			.put(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON.get(), ModBlocks.EXPOSED_COPPER_BUTTON.get())
			.put(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON.get(), ModBlocks.WEATHERED_COPPER_BUTTON.get())
			.put(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get(), ModBlocks.OXIDIZED_COPPER_BUTTON.get())
			.build();
	});

	@ModifyVariable(
		method = "useOnBlock",
		ordinal = 1,
		at = @At(
			value = "STORE"
		)
	)
	private Optional<BlockState> modifyOxidizedBlock(
		Optional<BlockState> originalBlockState,
		ItemUsageContext context
	) {
		if(originalBlockState.isPresent()) {
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
	private Optional<BlockState> modifyWaxedBlock(
		Optional<BlockState> originalBlockState,
		ItemUsageContext context
	) {
		if(originalBlockState.isPresent()) {
			return originalBlockState;
		}

		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);

		return Optional.ofNullable(WAXED_TO_UNWAXED_BLOCKS.get().get(blockState.getBlock())).map((block) -> block.getStateWithProperties(blockState));
	}
}
