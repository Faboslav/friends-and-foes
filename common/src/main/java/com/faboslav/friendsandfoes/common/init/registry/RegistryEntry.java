package com.faboslav.friendsandfoes.common.init.registry;

import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public interface RegistryEntry<T> extends Supplier<T>
{
    @Override
    T get();

    Identifier getId();
}