package com.faboslav.friendsandfoes.common.init.registry.forge;

import com.faboslav.friendsandfoes.common.init.registry.ReferenceRegistryEntry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraftforge.registries.RegistryObject;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public class ForgeReferenceRegistryEntry<R> implements ReferenceRegistryEntry<R>
{
	private final RegistryObject<R> object;

	public ForgeReferenceRegistryEntry(RegistryObject<R> object) {
		this.object = object;
	}

	@Override
	public RegistryEntry<R> referenceRegistryEntry() {
		return object;
	}

	@Override
	public Identifier getId() {
		return object.getId();
	}
}
