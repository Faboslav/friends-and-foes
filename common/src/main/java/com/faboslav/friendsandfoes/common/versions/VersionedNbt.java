package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.nbt.CompoundTag;

public final class VersionedNbt
{
	public static String getString(CompoundTag nbt, String key, String defaultValue) {
		return nbt.getStringOr(key, defaultValue);
	}

	public static int getInt(CompoundTag nbt, String key, int defaultValue) {
		return nbt.getIntOr(key, defaultValue);
	}

	public static float getFloat(CompoundTag nbt, String key, float defaultValue) {
		return nbt.getFloatOr(key, defaultValue);
	}

	public static double getDouble(CompoundTag nbt, String key, double defaultValue) {
		return nbt.getDoubleOr(key, defaultValue);
	}

	public static boolean getBoolean(CompoundTag nbt, String key, boolean defaultValue) {
		return nbt.getBooleanOr(key, defaultValue);
	}

	public static CompoundTag getCompound(CompoundTag nbt, String key) {
		return nbt.getCompoundOrEmpty(key);
	}
}
