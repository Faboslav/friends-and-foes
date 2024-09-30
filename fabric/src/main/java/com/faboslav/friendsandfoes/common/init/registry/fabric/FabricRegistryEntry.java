package com.faboslav.friendsandfoes.common.init.registry.fabric;

import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
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