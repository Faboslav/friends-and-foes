package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.init.ModBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoneycombItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Supplier;

@Mixin(HoneycombItem.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class HoneycombItemMixin
{
	private static final Supplier<BiMap<Block, Block>> UNWAXED_TO_WAXED_BUTTON_BLOCKS = Suppliers.memoize(() -> {
		return (BiMap) ImmutableBiMap.builder()
			.put(ModBlocks.COPPER_BUTTON.get(), ModBlocks.WAXED_COPPER_BUTTON.get())
			.put(ModBlocks.EXPOSED_COPPER_BUTTON.get(), ModBlocks.WAXED_EXPOSED_COPPER_BUTTON.get())
			.put(ModBlocks.WEATHERED_COPPER_BUTTON.get(), ModBlocks.WAXED_WEATHERED_COPPER_BUTTON.get())
			.put(ModBlocks.OXIDIZED_COPPER_BUTTON.get(), ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get())
			.build();
	});

	@Inject(
		method = "getWaxedState",
		at = @At("RETURN"),
		cancellable = true
	)
	private static void getWaxedState(
		BlockState state,
		CallbackInfoReturnable<Optional<BlockState>> cir
	) {
		var blockState = cir.getReturnValue();

		if (blockState.isPresent()) {
			cir.setReturnValue(blockState);
		}

		blockState = Optional.ofNullable((Block) ((BiMap) UNWAXED_TO_WAXED_BUTTON_BLOCKS.get()).get(state.getBlock())).map((block) -> {
			return block.getStateWithProperties(state);
		});

		cir.setReturnValue(blockState);
	}
}