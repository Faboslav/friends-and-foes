package com.faboslav.friendsandfoes.common.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.sound.SoundEvents;

public final class TotemUtil
{
	public static void playActivateAnimation(ItemStack itemStack, Entity entity, ParticleType<?> particleType) {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		minecraftClient.particleManager.addEmitter(entity, (ParticleEffect) particleType, 30);

		ClientWorld clientWorld = minecraftClient.world;

		if (clientWorld != null) {
			clientWorld.playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0f, 1.0f, false);
		}

		if (entity == minecraftClient.player) {
			minecraftClient.gameRenderer.showFloatingItem(itemStack);
		}
	}
}
