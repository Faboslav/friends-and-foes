package com.faboslav.friendsandfoes.neoforge.init.registry.neoforge;

import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistryType;

public class ResourcefulRegistriesImpl
{
    public static <T> ResourcefulRegistry<T> create(net.minecraft.registry.Registry<T> registry, String id) {
        return new NeoForgeResourcefulRegistry<>(registry, id);
    }

    @SuppressWarnings("unchecked")
    public static <D, T extends ResourcefulRegistry<D>> T create(ResourcefulRegistryType<D, T> type, String id) {
        throw new IllegalArgumentException("Unknown registry type: " + type);
    }
}
