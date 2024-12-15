package com.faboslav.friendsandfoes.common.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public final class TotemUtil
{
	public static void playActivateAnimation(ItemStack itemStack, Entity entity, ParticleType<?> particleType) {
		Minecraft minecraftClient = Minecraft.getInstance();
		minecraftClient.particleEngine.createTrackingEmitter(entity, (ParticleOptions) particleType, 30);

		ClientLevel clientWorld = minecraftClient.level;

		if (clientWorld != null) {
			clientWorld.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TOTEM_USE, entity.getSoundSource(), 1.0f, 1.0f, false);
		}

		if (entity == minecraftClient.player) {
			minecraftClient.gameRenderer.displayItemActivation(itemStack);
		}
	}
}
