package com.faboslav.friendsandfoes.common.init.registry.fabric;

import com.faboslav.friendsandfoes.common.init.registry.ReferenceRegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntries;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import com.faboslav.friendsandfoes.fabric.mixin.PointOfInterestTypesAccessor;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Collection;
import java.util.function.Supplier;

public class FabricResourcefulRegistry<T> implements ResourcefulRegistry<T>
{
	private final RegistryEntries<T> entries = new RegistryEntries<>();
	private final Registry<T> registry;
	private final String id;

	public FabricResourcefulRegistry(Registry<T> registry, String id) {
		this.registry = registry;
		this.id = id;
	}

	@Override
	public <I extends T> RegistryEntry<I> register(String id, Supplier<I> supplier) {
		var registryEntry = FabricRegistryEntry.of(this.registry, Identifier.of(this.id, id), supplier);
		I value = registryEntry.get();
		if (value instanceof PointOfInterestType poiType) {
			PointOfInterestTypesAccessor.callRegisterStates(
				(net.minecraft.registry.entry.RegistryEntry<PointOfInterestType>) registry.entryOf(registry.getKey(value).orElseThrow()),
				poiType.blockStates()
			);
		}

		return entries.add(registryEntry);
	}

	@Override
	public ReferenceRegistryEntry<T> registerReference(String id, Supplier<T> supplier) {
		return entries.add(FabricHolderRegistryEntry.of(this.registry, Identifier.of(this.id, id), supplier));
	}

	@Override
	public Collection<RegistryEntry<T>> getEntries() {
		return this.entries.getEntries();
	}

	@Override
	public void init() {
	}
}
