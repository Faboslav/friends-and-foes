package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.ListTag;
import org.jetbrains.annotations.Nullable;

//? if >=1.21.6 {
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
//?}

import java.util.UUID;

public final class VersionedNbt
{
	//? if >=1.21.6 {
	public static String getString(ValueInput nbt, String key, String defaultValue)
	//?} else {
	/*public static String getString(CompoundTag nbt, String key, String defaultValue)
	*///?}
	{
		//? if >= 1.21.5 {
		return nbt.getStringOr(key, defaultValue);
		//?} else {
		/*return nbt.contains(key) ? nbt.getString(key) : defaultValue;
		*///?}
	}

	//? if >=1.21.6 {
	public static int getInt(ValueInput nbt, String key, int defaultValue)
	{
		return nbt.getIntOr(key, defaultValue);
	}
	//?}

	public static int getInt(CompoundTag nbt, String key, int defaultValue)
	{
		//? if >= 1.21.5 {
		return nbt.getIntOr(key, defaultValue);
		//?} else {
		/*return nbt.contains(key) ? nbt.getInt(key) : defaultValue;
		*///?}
	}

	//? if >=1.21.6 {
	public static float getFloat(ValueInput nbt, String key, float defaultValue)
	{
		return nbt.getFloatOr(key, defaultValue);
	}
	//?}

	public static float getFloat(CompoundTag nbt, String key, float defaultValue)
	{
		//? if >=1.21.5 {
		return nbt.getFloatOr(key, defaultValue);
		//?} else {
		/*return nbt.contains(key) ? nbt.getFloat(key) : defaultValue;
		*///?}
	}

	//? if >=1.21.6 {
	public static double getDouble(ValueInput nbt, String key, double defaultValue)
	{
		return nbt.getDoubleOr(key, defaultValue);
	}
	//?}

	public static double getDouble(CompoundTag nbt, String key, double defaultValue)
	{
		//? if >=1.21.5 {
		return nbt.getDoubleOr(key, defaultValue);
		//?} else {
		/*return nbt.contains(key) ? nbt.getDouble(key) : defaultValue;
		*///?}
	}

	//? if >=1.21.6 {
	public static boolean getBoolean(ValueInput nbt, String key, boolean defaultValue)
	 //?} else {
	/*public static boolean getBoolean(CompoundTag nbt, String key, boolean defaultValue)
	*///?}
	{
		//? if >= 1.21.5 {
		return nbt.getBooleanOr(key, defaultValue);
		 //?} else {
		/*return nbt.contains(key) ? nbt.getBoolean(key) : defaultValue;
		*///?}
	}

	//? if >=1.21.6 {
	public static CompoundTag getCompound(CompoundTag nbt, String key)
	//?} else {
	/*public static CompoundTag getCompound(CompoundTag nbt, String key)
	*///?}
	{
		//? if >= 1.21.5 {
		return nbt.getCompoundOrEmpty(key);
		//?} else {
		/*return nbt.contains(key) ? nbt.getCompound(key) : new CompoundTag();
		*///?}
	}

	public static ListTag getList(CompoundTag nbt, String key) {
		//? if >= 1.21.5 {
		return nbt.getListOrEmpty(key);
		//?} else {
		/*return nbt.getList(key, 10);
		 *///?}
	}

	//? if >=1.21.6 {
	public static void putUUID(ValueOutput saveData, String key, @Nullable UUID uuid)
	//?} else {
	/*public static void putUUID(CompoundTag saveData, String key, @Nullable UUID uuid)
	*///?}
	{
		if(uuid == null) {
			return;
		}

		//? if >=1.21.6 {
		saveData.storeNullable(key, UUIDUtil.CODEC, uuid);
		//?} else if >=1.21.5 {
		/*saveData.store(key, UUIDUtil.CODEC, uuid);
		*///?} else {
		/*saveData.putUUID(key, uuid);
		*///?}
	}

	@Nullable
	//? if >=1.21.6 {
	public static UUID getUUID(ValueInput saveData, String key)
	//?} else {
	/*public static UUID getUUID(CompoundTag saveData, String key)
	*///?}
	{
		//? if >=1.21.6 {
		return saveData.read(key, UUIDUtil.CODEC).orElse(null);
		//?} else {
		/*if(saveData.contains(key)) {
			//? if >= 1.21.5 {
			return saveData.read(key, UUIDUtil.CODEC).orElse(null);
			 //?} else {
			/^return saveData.getUUID(key);
			 ^///?}
		}

		return null;
		*///?}
	}
}
