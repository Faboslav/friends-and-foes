package com.faboslav.friendsandfoes.mixin.neoforge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.util.OxidizableBlocksProvider;
import com.google.common.collect.ImmutableBiMap;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.minecraft.block.Oxidizable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Oxidizable.class, priority = 10000)
@SuppressWarnings({"rawtypes", "unchecked"})
public interface OxidizableMixin
{
	@ModifyReceiver(
		method = "method_34740",
		at = @At(
			value = "INVOKE",
			target = "Lcom/google/common/collect/ImmutableBiMap$Builder;build()Lcom/google/common/collect/ImmutableBiMap;"
		)
	)
	private static ImmutableBiMap.Builder inject(ImmutableBiMap.Builder receiver) {
		FriendsAndFoes.getLogger().info("INJECT");
		FriendsAndFoes.getLogger().info("INJECT");
		FriendsAndFoes.getLogger().info("INJECT");
		FriendsAndFoes.getLogger().info("INJECT");
		FriendsAndFoes.getLogger().info("INJECT");
		FriendsAndFoes.getLogger().info("INJECT");
		FriendsAndFoes.getLogger().info("INJECT");
		FriendsAndFoes.getLogger().info("INJECT");
		FriendsAndFoes.getLogger().info("INJECT");
		return receiver.putAll(OxidizableBlocksProvider.OXIDIZABLE_BLOCKS.get());
	}
}