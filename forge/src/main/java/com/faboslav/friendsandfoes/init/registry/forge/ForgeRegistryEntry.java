package com.faboslav.friendsandfoes.init.registry.forge;

import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraftforge.registries.RegistryObject;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public final class ForgeRegistryEntry<R, T extends R> implements RegistryEntry<T>
{
	private final RegistryObject<T> object;

	public ForgeRegistryEntry(RegistryObject<T> object) {
		this.object = object;
	}

	@Override
	public T get() {
		return object.get();
	}

	@Override
	public Identifier getId() {
		return object.getId();
	}
}