package com.faboslav.friendsandfoes.common.init.registry.forge;

import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import net.minecraft.registry.Registry;

public class ResourcefulRegistriesImpl {
	public static <T> ResourcefulRegistry<T> create(Registry<T> registry, String id) {
		return new ForgeResourcefulRegistry<>(registry, id);
	}
}
