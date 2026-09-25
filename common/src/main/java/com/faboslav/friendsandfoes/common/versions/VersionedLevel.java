package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.world.level.Level;

public final class VersionedLevel
{
	public static boolean isNight(Level level) {
		//? if >=1.21.5 {
		return level.isDarkOutside();
		//?} else {
		/*return level.isNight();
		*///?}
	}
}
