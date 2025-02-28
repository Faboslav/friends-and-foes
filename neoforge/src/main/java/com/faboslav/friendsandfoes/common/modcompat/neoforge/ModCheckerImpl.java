package com.faboslav.friendsandfoes.common.modcompat.neoforge;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;

import static com.faboslav.friendsandfoes.common.modcompat.ModChecker.loadModCompat;

public class ModCheckerImpl
{
	public static void setupPlatformModCompat() {
		String modId = "";

		try {
			//? if curios: >0 {
			/*modId = "curios";
			loadModCompat(modId, () -> new CuriosCompat());
			*///?}
		} catch (Throwable e) {
			FriendsAndFoes.getLogger().error("Failed to setup compat with " + modId);
			e.printStackTrace();
		}
	}
}
