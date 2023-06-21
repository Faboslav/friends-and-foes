package com.faboslav.friendsandfoes.platform.fabric;

import com.faboslav.friendsandfoes.platform.TotemHelper;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

/**
 * @see TotemHelper
 */
public final class TotemHelperImpl
{
	public static void sendTotemEffectPacket(ItemStack itemStack, LivingEntity livingEntity) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeItemStack(itemStack);
		buf.writeInt(livingEntity.getId());

		if (livingEntity instanceof ServerPlayerEntity player) {
			ServerPlayNetworking.send(player, TotemHelper.TOTEM_EFFECT_PACKET, buf);
		}

		for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) livingEntity.getEntityWorld(), livingEntity.getBlockPos())) {
			ServerPlayNetworking.send(player, TotemHelper.TOTEM_EFFECT_PACKET, buf);
		}
	}

	@Nullable
	public static ItemStack getTotemFromModdedSlots(PlayerEntity player, Predicate<ItemStack> totemFilter) {
		if (FabricLoader.getInstance().isModLoaded(TotemHelper.TRINKETS_MOD_ID)) {
			return TrinketsApi.getTrinketComponent(player).map(component -> {
				List<Pair<SlotReference, ItemStack>> res = component.getEquipped(totemFilter);
				return res.size() > 0 ? res.get(0).getRight():null;
			}).orElse(null);
		}

		return null;
	}
}

