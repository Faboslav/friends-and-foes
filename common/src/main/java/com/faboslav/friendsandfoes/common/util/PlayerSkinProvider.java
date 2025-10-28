//? if <= 1.21.8 {
/*package com.faboslav.friendsandfoes.common.util;

import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayerSkinProvider
{
	@Nullable
	private static final Map<UUID, PlayerInfo> playerListEntry = new HashMap<>();

	public static PlayerSkin getSkinTextures(PlayerIllusionEntity playerIllusion) {
		UUID uuid = playerIllusion.getPlayerUuid();

		if (uuid == null) {
			uuid = playerIllusion.getUUID();
		}

		PlayerInfo playerListEntry = getPlayerListEntry(uuid);

		if (playerListEntry != null) {
			return playerListEntry.getSkin();
		}

		return DefaultPlayerSkin.get(uuid);
	}

	@Nullable
	private static PlayerInfo getPlayerListEntry(UUID uuid) {
		if (!playerListEntry.containsKey(uuid)) {
			playerListEntry.put(uuid, Minecraft.getInstance().getConnection().getPlayerInfo(uuid));
		}

		return playerListEntry.get(uuid);
	}
}
*///?}
