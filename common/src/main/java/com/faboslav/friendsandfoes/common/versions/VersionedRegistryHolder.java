package com.faboslav.friendsandfoes.common.versions;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;

//? if >= 1.21.1 {
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import net.minecraft.core.Holder;
//?}

public final class VersionedRegistryHolder
{
	//? if >= 1.21.1 {
	public static <T> Holder<T> get(RegistryEntry<T> entry) {
		return ((HolderRegistryEntry<T>) entry).holder();
	}
	//?} else {
	/*public static <T> T get(RegistryEntry<T> entry) {
		return entry.get();
	}
	*///?}
}
