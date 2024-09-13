package com.faboslav.friendsandfoes.common.init.registry;

public interface ReferenceRegistryEntry<T> extends RegistryEntry<T>
{
	net.minecraft.registry.entry.RegistryEntry<T> referenceRegistryEntry();

	@Override
	default T get() {
		return referenceRegistryEntry().value();
	}
}