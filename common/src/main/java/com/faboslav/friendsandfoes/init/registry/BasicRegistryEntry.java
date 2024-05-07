package com.faboslav.friendsandfoes.init.registry;

import net.minecraft.util.Identifier;

import java.util.Objects;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public final class BasicRegistryEntry<T> implements RegistryEntry<T>
{
	private final Identifier id;
	private final T value;

	public BasicRegistryEntry(Identifier id, T value) {
		this.id = Objects.requireNonNull(id);
		this.value = Objects.requireNonNull(value);
	}

	@Override
	public T get() {
		return value;
	}

	@Override
	public Identifier getId() {
		return id;
	}
}
