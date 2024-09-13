package com.faboslav.friendsandfoes.common.init.registry.fabric;

import com.faboslav.friendsandfoes.common.init.registry.*;
import com.faboslav.friendsandfoes.fabric.mixin.PointOfInterestTypesAccessor;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
@SuppressWarnings({"unchecked"})
public final class FabricCustomResourcefulRegistry<T> implements ResourcefulRegistry<T>
{
	private final RegistryEntries<T> entries = new RegistryEntries<>();
	private final Registry<T> registry;
	private final String id;

	public FabricCustomResourcefulRegistry(Registry<T> registry, String id) {
		this.registry = registry;
		this.id = id;
	}

	@Override
	public <I extends T> RegistryEntry<I> register(String id, Supplier<I> supplier) {
		I value = Registry.register(registry, new Identifier(this.id, id), supplier.get());
		if (value instanceof PointOfInterestType poiType) {
			PointOfInterestTypesAccessor.callRegisterStates(
				(net.minecraft.registry.entry.RegistryEntry<PointOfInterestType>) registry.entryOf(registry.getKey(value).orElseThrow()),
				poiType.blockStates()
			);
		}
		return entries.add(new BasicRegistryEntry<>(new Identifier(this.id, id), value));
	}

	@Override
	public ReferenceRegistryEntry<T> registerReference(String id, Supplier<T> supplier) {
		return entries.add(FabricReferenceRegistryEntry.of(this.registry, Identifier.of(this.id, id), supplier));
	}

	@Override
	public Collection<RegistryEntry<T>> getEntries() {
		return this.entries.getEntries();
	}

	@Override
	public void init() {
	}
}
