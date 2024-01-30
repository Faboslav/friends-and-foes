package com.faboslav.friendsandfoes.platform;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public final class TotemHelper
{
	public static final Identifier TOTEM_EFFECT_PACKET = FriendsAndFoes.makeID("totem_effect_packet");
	public static final String TRINKETS_MOD_ID = "trinkets";
	public static final String CURIOS_MOD_ID = "curios";

	@ExpectPlatform
	@Nullable
	public static ItemStack getTotemFromModdedSlots(PlayerEntity player, Predicate<ItemStack> totemFilter) {
		throw new AssertionError();
	}

	private TotemHelper() {
	}
}
