package com.faboslav.friendsandfoes.fabric.platform;

import net.fabricmc.loader.api.FabricLoader;

public final class PlatformHelper implements com.faboslav.friendsandfoes.common.platform.PlatformHelper
{
	@Override
	public boolean isModLoaded(String modId) {
		return FabricLoader.getInstance().isModLoaded(modId);
	}
}
