package com.faboslav.friendsandfoes.init.registry;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public interface CustomRegistryLookup<T, K extends T> extends Iterable<T>
{
	boolean containsKey(Identifier id);

	boolean containsValue(T value);

	@Nullable
	T get(Identifier id);

	@Nullable
	Identifier getKey(T value);

	Collection<T> getValues();

	Collection<Identifier> getKeys();
}
