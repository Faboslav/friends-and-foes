package com.faboslav.friendsandfoes.common.init.registry;

public final class ResourcefulRegistryType<D, T extends ResourcefulRegistry<D>>
{
    private final Class<T> type;

    private ResourcefulRegistryType(Class<T> type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ResourcefulRegistryType{type=" + type + "}";
    }
}
