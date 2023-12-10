package com.faboslav.friendsandfoes.util.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public final class PlayerProvider
{
	public static PlayerEntity getClientPlayer() {
		return MinecraftClient.getInstance().player;
	}

	private PlayerProvider() {
	}
}
