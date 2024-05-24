package com.faboslav.friendsandfoes.modcompat.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;

import static com.faboslav.friendsandfoes.common.modcompat.ModChecker.loadModCompat;

public class ModCheckerImpl
{
	public static void setupPlatformModCompat() {
		String modId = "";

		try {
			modId = "curios";
			loadModCompat(modId, () -> new CuriosCompat());
		} catch (Throwable e) {
			FriendsAndFoes.getLogger().error("Failed to setup compat with " + modId);
			e.printStackTrace();
		}
	}
}
