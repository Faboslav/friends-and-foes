package com.faboslav.friendsandfoes.common.init.registry.fabric;

import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class FabricRegistryEntry<T> implements RegistryEntry<T>
{
	private final Identifier id;
	private final T value;

	private FabricRegistryEntry(Identifier id, T value) {
		this.id = id;
		this.value = value;
	}

	public static <T, I extends T> FabricRegistryEntry<I> of(
		Registry<T> registry,
		Identifier id,
		Supplier<I> supplier
	) {
		return new FabricRegistryEntry<>(id, Registry.register(registry, id, supplier.get()));
	}

	@Override
	public T get() {
		return this.value;
	}

	@Override
	public Identifier getId() {
		return this.id;
	}
}