package com.faboslav.friendsandfoes.common.init.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.registry.Registry;
import org.apache.commons.lang3.NotImplementedException;

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
