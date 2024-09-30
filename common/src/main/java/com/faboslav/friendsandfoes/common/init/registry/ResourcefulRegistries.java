package com.faboslav.friendsandfoes.common.init.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public class ResourcefulRegistries
{
	public static <T> ResourcefulRegistry<T> create(ResourcefulRegistry<T> parent) {
		return new ResourcefulRegistryChild<>(parent);
	}

	@ExpectPlatform
	public static <T> ResourcefulRegistry<T> create(Registry<T> registry, String id) {
		throw new NotImplementedException();
	}

	@ExpectPlatform
	public static <D, T extends ResourcefulRegistry<D>> T create(ResourcefulRegistryType<D, T> type, String id) {
		throw new NotImplementedException();
	}
}