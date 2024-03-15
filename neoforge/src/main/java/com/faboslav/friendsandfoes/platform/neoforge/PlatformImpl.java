package com.faboslav.friendsandfoes.platform.neoforge;

import net.neoforged.fml.ModList;

public final class PlatformImpl
{
	public static String getProjectSlug() {
		return "friends-and-foes-forge";
	}

	public static boolean isModLoaded(String modId) {
		return ModList.get().isLoaded(modId);
	}

	private PlatformImpl() {
	}
}
