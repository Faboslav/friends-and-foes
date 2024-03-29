package com.faboslav.friendsandfoes.modcompat.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoes;

import static com.faboslav.friendsandfoes.modcompat.ModChecker.loadModCompat;

public final class ModCheckerImpl
{
	public static void setupPlatformModCompat() {
		String modId = "";

		try {
			modId = "trinkets";
			loadModCompat(modId, () -> new TrinketsCompat());
		} catch (Throwable e) {
			FriendsAndFoes.getLogger().error("Failed to setup compat with " + modId);
			e.printStackTrace();
		}
	}
}
