package com.faboslav.friendsandfoes.fabric.mixin;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PointOfInterestTypes.class)
public interface PointOfInterestTypesAccessor
{
	@Invoker("registerStates")
	static void callRegisterStates(RegistryEntry<PointOfInterestType> poiType) {
		throw new UnsupportedOperationException();
	}
}
