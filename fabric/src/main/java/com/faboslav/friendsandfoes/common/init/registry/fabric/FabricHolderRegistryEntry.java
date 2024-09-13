package com.faboslav.friendsandfoes.common.init.registry.fabric;

import com.faboslav.friendsandfoes.common.init.registry.ReferenceRegistryEntry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public class FabricHolderRegistryEntry<T> implements ReferenceRegistryEntry<T>
{
	private final Identifier id;
	private final RegistryEntry<T> value;

	private FabricHolderRegistryEntry(Identifier id, RegistryEntry<T> value) {
		this.id = id;
		this.value = value;
	}

	public static <T, I extends T> FabricHolderRegistryEntry<T> of(
		Registry<T> registry,
		Identifier id,
		Supplier<I> supplier
	) {
		return new FabricHolderRegistryEntry<>(id, Registry.registerReference(registry, id, supplier.get()));
	}

	@Override
	public RegistryEntry<T> referenceRegistryEntry() {
		return this.value;
	}

	@Override
	public Identifier getId() {
		return this.id;
	}
}