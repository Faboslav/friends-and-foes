package com.faboslav.friendsandfoes.common.init.registry;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public final class ResourcefulRegistryType<D, T extends ResourcefulRegistry<D>> {
    private final Class<T> type;

    private ResourcefulRegistryType(Class<T> type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ResourcefulRegistryType{type=" + type + "}";
    }
}
