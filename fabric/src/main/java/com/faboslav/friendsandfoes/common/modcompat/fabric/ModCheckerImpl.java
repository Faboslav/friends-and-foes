package com.faboslav.friendsandfoes.common.modcompat.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.fabric.modcompat.fabric.TrinketsCompat;

import static com.faboslav.friendsandfoes.common.modcompat.ModChecker.loadModCompat;

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
