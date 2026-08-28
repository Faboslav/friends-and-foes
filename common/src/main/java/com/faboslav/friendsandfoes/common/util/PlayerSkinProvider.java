//? if <= 1.21.8 {
/*package com.faboslav.friendsandfoes.common.util;

import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//? if >= 1.21.1 {
import net.minecraft.client.resources.PlayerSkin;
//?}

public final class PlayerSkinProvider
{
	@Nullable
	private static final Map<UUID, PlayerInfo> playerListEntry = new HashMap<>();

	//? if >= 1.21.1 {
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
	//?} else {
	/^public static PlayerSkinInfo getSkinTextures(PlayerIllusionEntity playerIllusion) {
		UUID uuid = playerIllusion.getPlayerUuid();

		if (uuid == null) {
			uuid = playerIllusion.getUUID();
		}

		PlayerInfo playerListEntry = getPlayerListEntry(uuid);

		if (playerListEntry != null) {
			PlayerIllusionSkinModel model = "slim".equals(playerListEntry.getModelName()) ? PlayerIllusionSkinModel.SLIM : PlayerIllusionSkinModel.WIDE;
			return new PlayerSkinInfo(playerListEntry.getSkinLocation(), playerListEntry.isCapeLoaded() ? playerListEntry.getCapeLocation() : null, model);
		}

		PlayerIllusionSkinModel defaultModel = "slim".equals(DefaultPlayerSkin.getSkinModelName(uuid)) ? PlayerIllusionSkinModel.SLIM : PlayerIllusionSkinModel.WIDE;
		return new PlayerSkinInfo(DefaultPlayerSkin.getDefaultSkin(uuid), null, defaultModel);
	}
	^///?}

	@Nullable
	private static PlayerInfo getPlayerListEntry(UUID uuid) {
		if (!playerListEntry.containsKey(uuid)) {
			playerListEntry.put(uuid, Minecraft.getInstance().getConnection().getPlayerInfo(uuid));
		}

		return playerListEntry.get(uuid);
	}
}
*///?}
