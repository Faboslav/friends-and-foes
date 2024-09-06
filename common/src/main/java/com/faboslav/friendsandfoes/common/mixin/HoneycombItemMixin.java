package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.google.common.collect.ImmutableBiMap;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.minecraft.block.Blocks;
import net.minecraft.item.HoneycombItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = HoneycombItem.class, priority = 10000)
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class HoneycombItemMixin
{
	@ModifyReceiver(method = "method_34723", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableBiMap$Builder;build()Lcom/google/common/collect/ImmutableBiMap;"))
	private static ImmutableBiMap.Builder inject(ImmutableBiMap.Builder receiver) {
		return receiver.put(FriendsAndFoesBlocks.COPPER_BUTTON.get(), FriendsAndFoesBlocks.WAXED_COPPER_BUTTON.get())
			.put(FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.WAXED_EXPOSED_COPPER_BUTTON.get())
			.put(FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.WAXED_WEATHERED_COPPER_BUTTON.get())
			.put(FriendsAndFoesBlocks.OXIDIZED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get())
			.put(Blocks.LIGHTNING_ROD, FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get())
			.put(FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get())
			.put(FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get())
			.put(FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get());
	}
}