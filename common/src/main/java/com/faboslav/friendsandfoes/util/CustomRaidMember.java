package com.faboslav.friendsandfoes.util;

import net.minecraft.village.raid.Raid;

public final class CustomRaidMember
{
	static {
		Raid.Member.values();
	}

	public static final String ICEOLOGER_INTERNAL_NAME = "ICEOLOGER";
	public static final int[] ICEOLOGER_COUNT_IN_WAVE = {0, 0, 0, 0, 1, 1, 0, 1};
	public static Raid.Member ICEOLOGER;
	public static final String ILLUSIONER_INTERNAL_NAME = "ILLUSIONER";
	public static final int[] ILLUSIONER_COUNT_IN_WAVE = {0, 0, 0, 0, 1, 0, 1, 1};
	public static Raid.Member ILLUSIONER;
}
