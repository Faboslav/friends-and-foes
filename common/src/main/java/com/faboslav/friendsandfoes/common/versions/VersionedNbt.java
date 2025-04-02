package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public final class VersionedNbt
{
	public static String getString(CompoundTag nbt, String key, String defaultValue) {
		//? >= 1.21.5 {
		return nbt.getStringOr(key, defaultValue);
		//?} else {
		/*return nbt.contains(key) ? nbt.getString(key) : defaultValue;
		*///?}
	}

	public static int getInt(CompoundTag nbt, String key, int defaultValue) {
		//? >= 1.21.5 {
		return nbt.getIntOr(key, defaultValue);
		//?} else {
		/*return nbt.contains(key) ? nbt.getInt(key) : defaultValue;
		*///?}
	}

	public static float getFloat(CompoundTag nbt, String key, float defaultValue) {
		//? >= 1.21.5 {
		return nbt.getFloatOr(key, defaultValue);
		//?} else {
		/*return nbt.contains(key) ? nbt.getFloat(key) : defaultValue;
		*///?}
	}

	public static double getDouble(CompoundTag nbt, String key, double defaultValue) {
		//? >= 1.21.5 {
		return nbt.getDoubleOr(key, defaultValue);
		 //?} else {
		/*return nbt.contains(key) ? nbt.getDouble(key) : defaultValue;
		*///?}
	}

	public static boolean getBoolean(CompoundTag nbt, String key, boolean defaultValue) {
		//? >= 1.21.5 {
		return nbt.getBooleanOr(key, defaultValue);
		 //?} else {
		/*return nbt.contains(key) ? nbt.getBoolean(key) : defaultValue;
		*///?}
	}

	public static CompoundTag getCompound(CompoundTag nbt, String key) {
		//? >= 1.21.5 {
		return nbt.getCompoundOrEmpty(key);
		//?} else {
		/*return nbt.contains(key) ? nbt.getCompound(key) : new CompoundTag();
		*///?}
	}

	@Nullable
	public static UUID getUuid(CompoundTag nbt, String key, boolean defaultValue) {
		if(nbt.contains(key)) {
			//? >= 1.21.5 {
			return nbt.read(key, UUIDUtil.CODEC).orElse(null);
			 //?} else {
			/*return nbt.getUUID(key);
			*///?}
		}

		return null;
	}
}
