package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.util.WaxableBlocksMap;
import com.google.common.collect.ImmutableBiMap;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.minecraft.item.HoneycombItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = HoneycombItem.class, priority = 10000)
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class HoneycombItemMixin
{
	@ModifyReceiver(method = "method_34723", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableBiMap$Builder;build()Lcom/google/common/collect/ImmutableBiMap;"))
	private static ImmutableBiMap.Builder inject(ImmutableBiMap.Builder receiver) {
		return receiver.putAll(WaxableBlocksMap.UNWAXED_TO_WAXED_BUTTON_BLOCKS.get());
	}
}