package com.faboslav.friendsandfoes.common.util;

import net.minecraft.world.entity.raid.Raid;

public final class CustomRaidMember
{
	static {
		Raid.RaiderType.values();
	}

	public static final String ICEOLOGER_INTERNAL_NAME = "ICEOLOGER";
	public static final int[] ICEOLOGER_COUNT_IN_WAVE = {0, 0, 0, 0, 1, 1, 0, 1};
	public static Raid.RaiderType ICEOLOGER;
	public static final String ILLUSIONER_INTERNAL_NAME = "ILLUSIONER";
	public static final int[] ILLUSIONER_COUNT_IN_WAVE = {0, 0, 0, 0, 1, 0, 1, 1};
	public static Raid.RaiderType ILLUSIONER;
}
