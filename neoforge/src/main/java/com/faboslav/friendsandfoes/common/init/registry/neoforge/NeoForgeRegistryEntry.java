package com.faboslav.friendsandfoes.common.init.registry.neoforge;

import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.neoforged.neoforge.registries.DeferredHolder;

public class NeoForgeRegistryEntry<R, T extends R> implements RegistryEntry<T>
{
	private final DeferredHolder<R, T> object;

	public NeoForgeRegistryEntry(DeferredHolder<R, T> object) {
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
