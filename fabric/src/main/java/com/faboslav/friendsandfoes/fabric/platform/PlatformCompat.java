package com.faboslav.friendsandfoes.fabric.platform;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;

public final class PlatformCompat implements com.faboslav.friendsandfoes.common.platform.PlatformCompat
{
	@Override
	public void setupPlatformModCompat() {
		String modId = "";

		try {
			//? trinkets {
			modId = "trinkets";
			loadModCompat(modId, () -> new TrinketsCompat());
			//?}
		} catch (Throwable e) {
			FriendsAndFoes.getLogger().error("Failed to setup compat with " + modId);
			e.printStackTrace();
		}
	}
}
