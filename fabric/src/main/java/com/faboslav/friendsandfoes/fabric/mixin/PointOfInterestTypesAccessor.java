package com.faboslav.friendsandfoes.fabric.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(PointOfInterestTypes.class)
public interface PointOfInterestTypesAccessor
{
	@Invoker("registerStates")
	static void callRegisterStates(RegistryEntry<PointOfInterestType> poiTypeEntry, Set<BlockState> states) {
		throw new UnsupportedOperationException();
	}
}
