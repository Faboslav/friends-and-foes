package com.faboslav.friendsandfoes.common.init.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Supplier;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public final class ResourcefulRegistries
{
	public static <T> ResourcefulRegistry<T> create(ResourcefulRegistry<T> parent) {
		return new ResourcefulRegistryChild<>(parent);
	}

	@ExpectPlatform
	public static <T> ResourcefulRegistry<T> create(Registry<T> registry, String id) {
		throw new NotImplementedException();
	}

	@ExpectPlatform
	public static <T, R extends T, K extends Registry<T>> Pair<Supplier<CustomRegistryLookup<T, R>>, ResourcefulRegistry<T>> createCustomRegistryInternal(
		String modId,
		RegistryKey<K> key,
		boolean save,
		boolean sync,
		boolean allowModification
	) {
		throw new NotImplementedException();
	}
}
