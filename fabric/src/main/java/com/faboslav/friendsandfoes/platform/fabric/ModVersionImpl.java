package com.faboslav.friendsandfoes.platform.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;

public final class ModVersionImpl
{
	@Nullable
	public static String getModVersion() {
		return FabricLoader.getInstance().getModContainer(FriendsAndFoes.MOD_ID).map(modContainer -> modContainer.getMetadata().getVersion().toString()).orElse(null);
	}
}
