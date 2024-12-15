package com.faboslav.friendsandfoes.common.init.registry;

import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public interface RegistryEntry<T> extends Supplier<T>
{
	@Override
	T get();

	ResourceLocation getId();
}