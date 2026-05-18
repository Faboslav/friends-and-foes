package com.faboslav.friendsandfoes.fabric.platform;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.modcompat.ModChecker;

//? if trinkets {
import com.faboslav.friendsandfoes.fabric.modcompat.TrinketsCompat;
//?}

public final class PlatformCompat implements com.faboslav.friendsandfoes.common.platform.PlatformCompat
{
	@Override
	public void setupPlatformModCompat() {
		String modId = "";

		try {
			//? if trinkets {
			modId = "trinkets";
			//? if >=26.1 {
			modId = "trinkets_updated";
			//?}
			ModChecker.loadModCompat(modId, () -> new TrinketsCompat());
			//?}
		} catch (Throwable e) {
			FriendsAndFoes.getLogger().error("Failed to setup compat with " + modId);
			e.printStackTrace();
		}
	}
}
