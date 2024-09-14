package com.faboslav.friendsandfoes.common.init.registry;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public class RegistryEntries<T> {
    private final List<RegistryEntry<T>> entries = new ArrayList<>();

    public <I extends T, E extends RegistryEntry<I>> E add(E entry) {
        //noinspection unchecked
        entries.add((RegistryEntry<T>) entry);
        return entry;
    }

    public List<RegistryEntry<T>> getEntries() {
        return ImmutableList.copyOf(entries);
    }
}