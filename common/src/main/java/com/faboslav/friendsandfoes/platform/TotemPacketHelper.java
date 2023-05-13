package com.faboslav.friendsandfoes.platform;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public final class TotemPacketHelper
{
	public static final Identifier TOTEM_EFFECT_PACKET = FriendsAndFoes.makeID("totem_effect_packet");

	@ExpectPlatform
	public static void sendTotemEffectPacket(ItemStack itemStack, LivingEntity livingEntity) {
		throw new AssertionError();
	}

	private TotemPacketHelper() {
	}
}
