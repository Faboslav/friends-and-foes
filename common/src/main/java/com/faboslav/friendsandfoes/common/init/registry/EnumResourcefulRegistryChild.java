package com.faboslav.friendsandfoes.common.init.registry;

import java.util.EnumMap;
import java.util.function.Supplier;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public final class EnumResourcefulRegistryChild<E extends Enum<E>, T> extends ResourcefulRegistryChild<T>
{
	private final EnumMap<E, RegistryEntries<T>> entries;

	public EnumResourcefulRegistryChild(Class<E> enumClass, ResourcefulRegistry<T> parent) {
		super(parent);
		entries = new EnumMap<>(enumClass);
	}

	public <I extends T> RegistryEntry<I> register(E enumValue, String id, Supplier<I> supplier) {
		return entries.computeIfAbsent(enumValue, a -> new RegistryEntries<>())
			.add(super.register(id, supplier));
	}

	public RegistryEntries<T> getEntries(E enumValue) {
		return entries.get(enumValue);
	}
}