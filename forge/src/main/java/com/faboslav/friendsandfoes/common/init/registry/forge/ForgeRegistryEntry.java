package com.faboslav.friendsandfoes.common.init.registry.forge;

import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraftforge.registries.RegistryObject;

public class ForgeRegistryEntry<T> implements RegistryEntry<T>
{

	private final RegistryObject<T> object;

	public ForgeRegistryEntry(RegistryObject<T> object) {
		this.object = object;
	}

	@Override
	public T get() {
		return object.get();
	}

	@Override
	public Identifier getId() {
		return object.getId();
	}
}