package com.faboslav.friendsandfoes.common.modcompat.forge;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.forge.modcompat.CuriosCompat;

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
