package com.faboslav.friendsandfoes.neoforge.platform;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.modcompat.ModChecker;

//? if curios {
/*import com.faboslav.friendsandfoes.neoforge.modcompat.CuriosCompat;
*///?}

public final class PlatformCompat implements com.faboslav.friendsandfoes.common.platform.PlatformCompat
{
	@Override
	public void setupPlatformModCompat() {
		String modId = "";

		try {
			//? if curios {
			/*modId = "curios";
			ModChecker.loadModCompat(modId, () -> new CuriosCompat());
			*///?}
		} catch (Throwable e) {
			FriendsAndFoes.getLogger().error("Failed to setup compat with " + modId);
			e.printStackTrace();
		}
	}
}
