package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.init.FriendsAndFoesBlocks;
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
			.put(FriendsAndFoesBlocks.COPPER_BUTTON.get(), FriendsAndFoesBlocks.WAXED_COPPER_BUTTON.get())
			.put(FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.WAXED_EXPOSED_COPPER_BUTTON.get())
			.put(FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.WAXED_WEATHERED_COPPER_BUTTON.get())
			.put(FriendsAndFoesBlocks.OXIDIZED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get())
			.build();
	});

	@Inject(
		method = "getWaxedState",
		at = @At("RETURN"),
		cancellable = true
	)
	private static void getWaxedState(
		BlockState state,
		CallbackInfoReturnable<Optional<BlockState>> callbackInfo
	) {
		var blockState = callbackInfo.getReturnValue();

		if (blockState.isEmpty()) {
			blockState = Optional.ofNullable((Block) ((BiMap) UNWAXED_TO_WAXED_BUTTON_BLOCKS.get()).get(state.getBlock())).map((block) -> {
				return block.getStateWithProperties(state);
			});

			callbackInfo.setReturnValue(blockState);
		}
	}
}