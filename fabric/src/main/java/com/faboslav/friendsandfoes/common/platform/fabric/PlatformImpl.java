package com.faboslav.friendsandfoes.common.platform.fabric;

import net.fabricmc.loader.api.FabricLoader;

public final class PlatformImpl
{
	public static String getProjectSlug() {
		return "friends-and-foes";
	}

	public static boolean isModLoaded(String modId) {
		return FabricLoader.getInstance().isModLoaded(modId);
	}

	private PlatformImpl() {
	}
}
