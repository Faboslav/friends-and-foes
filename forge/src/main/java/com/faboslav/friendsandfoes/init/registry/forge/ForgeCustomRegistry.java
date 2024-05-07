package com.faboslav.friendsandfoes.init.registry.forge;

import com.faboslav.friendsandfoes.init.registry.CustomRegistryLookup;
import net.minecraft.util.Identifier;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Supplier;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public final class ForgeCustomRegistry<T, K extends T> implements CustomRegistryLookup<T, K>
{
	private final Supplier<IForgeRegistry<T>> registry;

	public ForgeCustomRegistry(Supplier<IForgeRegistry<T>> registry) {
		this.registry = registry;
	}

	@Override
	public boolean containsKey(Identifier id) {
		return registry.get().containsKey(id);
	}

	@Override
	public boolean containsValue(T value) {
		return registry.get().containsValue(value);
	}

	@Override
	public @Nullable T get(Identifier id) {
		return registry.get().getValue(id);
	}

	@Override
	public @Nullable Identifier getKey(T value) {
		return registry.get().getKey(value);
	}

	@Override
	public Collection<T> getValues() {
		return registry.get().getValues();
	}

	@Override
	public Collection<Identifier> getKeys() {
		return registry.get().getKeys();
	}

	@NotNull
	@Override
	public Iterator<T> iterator() {
		return registry.get().iterator();
	}
}
