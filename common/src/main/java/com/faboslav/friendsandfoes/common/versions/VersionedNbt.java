package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.ListTag;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public final class VersionedNbt
{

	public static String getString(CompoundTag nbt, String key, String defaultValue)

	{

		return nbt.contains(key) ? nbt.getString(key) : defaultValue;

	}

	public static int getInt(CompoundTag nbt, String key, int defaultValue)
	{

		return nbt.contains(key) ? nbt.getInt(key) : defaultValue;

	}

	public static float getFloat(CompoundTag nbt, String key, float defaultValue)
	{

		return nbt.contains(key) ? nbt.getFloat(key) : defaultValue;

	}

	public static double getDouble(CompoundTag nbt, String key, double defaultValue)
	{

		return nbt.contains(key) ? nbt.getDouble(key) : defaultValue;

	}

	public static boolean getBoolean(CompoundTag nbt, String key, boolean defaultValue)

	{

		return nbt.contains(key) ? nbt.getBoolean(key) : defaultValue;

	}

	public static CompoundTag getCompound(CompoundTag nbt, String key)

	{

		return nbt.contains(key) ? nbt.getCompound(key) : new CompoundTag();

	}

	public static ListTag getList(CompoundTag nbt, String key) {

		return nbt.getList(key, 10);

	}

	public static void putUUID(CompoundTag saveData, String key, @Nullable UUID uuid)

	{
		if(uuid == null) {
			return;
		}

		saveData.putUUID(key, uuid);

	}

	@Nullable

	public static UUID getUUID(CompoundTag saveData, String key)

	{

		if(saveData.contains(key)) {

			return saveData.getUUID(key);

		}

		return null;

	}
}
