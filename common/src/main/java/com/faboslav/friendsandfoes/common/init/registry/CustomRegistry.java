package com.faboslav.friendsandfoes.common.init.registry;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.apache.commons.lang3.tuple.Pair;

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
public interface CustomRegistry<T> extends ResourcefulRegistry<T>
{
	static <T, K extends Registry<T>> CustomRegistry<T> of(
		String modId,
		Class<T> type,
		RegistryKey<K> key,
		boolean save,
		boolean sync,
		boolean allowModification
	) {
		Pair<Supplier<CustomRegistryLookup<T, T>>, ResourcefulRegistry<T>> pair = ResourcefulRegistries.createCustomRegistryInternal(modId, key, save, sync, allowModification);
		return new CustomRegistry<>()
		{
			@Override
			public CustomRegistryLookup<T, T> lookup() {
				return pair.getLeft().get();
			}

			@Override
			public ResourcefulRegistry<T> registry() {
				return pair.getRight();
			}
		};
	}

	CustomRegistryLookup<T, T> lookup();

	ResourcefulRegistry<T> registry();

	@Override
	default <I extends T> RegistryEntry<I> register(String id, Supplier<I> supplier) {
		return registry().register(id, supplier);
	}

	@Override
	default Collection<RegistryEntry<T>> getEntries() {
		return registry().getEntries();
	}

	@Override
	default Stream<RegistryEntry<T>> stream() {
		return registry().stream();
	}

	@Override
	default void init() {
		registry().init();
	}
}
