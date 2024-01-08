package com.faboslav.friendsandfoes.util.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public final class PlayerProvider
{
	public static PlayerEntity getClientPlayer() {
		return MinecraftClient.getInstance().player;
	}

	public static PlayerListEntry getClientPlayerListEntry(UUID uuid) {
		return MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(uuid);
	}

	private PlayerProvider() {
	}
}
