package com.faboslav.friendsandfoes.common.init.registry;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public interface ResourcefulRegistry<T>
{
	<I extends T> RegistryEntry<I> register(String id, Supplier<I> supplier);

	Collection<RegistryEntry<T>> getEntries();

	default Stream<RegistryEntry<T>> stream() {
		return getEntries().stream();
	}

	void init();
}
