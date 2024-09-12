package com.faboslav.friendsandfoes.neoforge.init.registry.neoforge;

import com.faboslav.friendsandfoes.common.init.registry.CustomRegistryLookup;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public final class NeoForgeCustomRegistry<T, K extends T> implements CustomRegistryLookup<T, K>
{
	private final Registry<T> registry;

	public NeoForgeCustomRegistry(Registry<T> registry) {
		this.registry = registry;
	}

	@Override
	public boolean containsKey(Identifier id) {
		return this.registry.containsId(id);
	}

	@Override
	public boolean containsValue(T value) {
		return this.registry.containsValue(value);
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
		return registry.stream().collect(Collectors.toList());
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
