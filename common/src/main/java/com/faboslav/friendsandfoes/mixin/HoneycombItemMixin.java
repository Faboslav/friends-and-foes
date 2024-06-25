package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.util.WaxableBlocksMap;
import com.google.common.collect.BiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoneycombItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(HoneycombItem.class)
@SuppressWarnings({"rawtypes"})
public abstract class HoneycombItemMixin
{
	@Inject(
		method = "getWaxedState",
		at = @At("RETURN"),
		cancellable = true
	)
	private static void friendsandfoes_getWaxedState(
		BlockState state,
		CallbackInfoReturnable<Optional<BlockState>> callbackInfo
	) {
		var blockState = callbackInfo.getReturnValue();

		if (blockState.isEmpty()) {
			blockState = Optional.ofNullable((Block) ((BiMap) WaxableBlocksMap.UNWAXED_TO_WAXED_BUTTON_BLOCKS.get()).get(state.getBlock())).map((block) -> {
				return block.getStateWithProperties(state);
			});

			callbackInfo.setReturnValue(blockState);
		}
	}
}