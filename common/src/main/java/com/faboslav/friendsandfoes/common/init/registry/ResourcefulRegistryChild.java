package com.faboslav.friendsandfoes.common.init.registry;

import java.util.Collection;
import java.util.function.Supplier;

public class ResourcefulRegistryChild<T> implements ResourcefulRegistry<T>
{
    private final ResourcefulRegistry<T> parent;
    private final RegistryEntries<T> entries = new RegistryEntries<>();

    public ResourcefulRegistryChild(ResourcefulRegistry<T> parent) {
        this.parent = parent;
    }

    @Override
    public <I extends T> RegistryEntry<I> register(String id, Supplier<I> supplier) {
        return this.entries.add(parent.register(id, supplier));
    }

    @Override
    public ReferenceRegistryEntry<T> registerReference(String id, Supplier<T> supplier) {
        return this.entries.add(parent.registerReference(id, supplier));
    }

    @Override
    public Collection<RegistryEntry<T>> getEntries() {
        return entries.getEntries();
    }

    @Override
    public void init() {}
}