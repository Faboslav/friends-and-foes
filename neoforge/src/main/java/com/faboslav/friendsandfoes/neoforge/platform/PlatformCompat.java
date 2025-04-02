package com.faboslav.friendsandfoes.neoforge.platform;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;

public final class PlatformCompat implements com.faboslav.friendsandfoes.common.platform.PlatformCompat
{
	@Override
	public void setupPlatformModCompat() {
		String modId = "";

		try {
			//? if curios {
			modId = "curios";
			loadModCompat(modId, () -> new CuriosCompat());
			//?}
		} catch (Throwable e) {
			FriendsAndFoes.getLogger().error("Failed to setup compat with " + modId);
			e.printStackTrace();
		}
	}
}
