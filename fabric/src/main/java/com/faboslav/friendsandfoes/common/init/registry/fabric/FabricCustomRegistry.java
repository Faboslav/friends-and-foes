package com.faboslav.friendsandfoes.common.init.registry.fabric;

import com.faboslav.friendsandfoes.common.init.registry.CustomRegistryLookup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public final class FabricCustomRegistry<T> implements CustomRegistryLookup<T, T>
{
	private final Registry<T> registry;

	public FabricCustomRegistry(Registry<T> registry) {
		this.registry = registry;
	}

	@Override
	public boolean containsKey(Identifier id) {
		return registry.containsId(id);
	}

	@Override
	public boolean containsValue(T value) {
		return registry.getKey(value) != null;
	}

	@Override
	public @Nullable T get(Identifier id) {
		return registry.get(id);
	}

	@Override
	public @Nullable Identifier getKey(T value) {
		return registry.getId(value);
	}

	@Override
	public Collection<T> getValues() {
		return registry.stream().toList();
	}

	@Override
	public Collection<Identifier> getKeys() {
		return registry.getIds();
	}

	@NotNull
	@Override
	public Iterator<T> iterator() {
		return registry.iterator();
	}
}
