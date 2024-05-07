package com.faboslav.friendsandfoes.init.registry;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public class ResourcefulRegistryChild<T> implements ResourcefulRegistry<T>
{
	private final ResourcefulRegistry<T> parent;
	private final RegistryEntries<T> entries = new RegistryEntries<>();

	public ResourcefulRegistryChild(ResourcefulRegistry<T> parent) {
		this.parent = parent;
	}

	@Override
	public <I extends T> RegistryEntry<I> register(String id, Supplier<I> supplier) {
		return this.entries.add(parent.register(id, supplier));
	}

	@Override
	public Collection<RegistryEntry<T>> getEntries() {
		return entries.getEntries();
	}

	@Override
	public void init() {
	}
}