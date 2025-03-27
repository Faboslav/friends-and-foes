package com.faboslav.friendsandfoes.neoforge.platform;

import net.neoforged.fml.ModList;

public final class PlatformHelper implements com.faboslav.friendsandfoes.common.platform.PlatformHelper
{
	@Override
	public boolean isModLoaded(String modId) {
		return ModList.get().isLoaded(modId);
	}
}
