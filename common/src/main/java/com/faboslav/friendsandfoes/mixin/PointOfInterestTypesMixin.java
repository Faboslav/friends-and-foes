package com.faboslav.friendsandfoes.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Set;

@Mixin(PointOfInterestTypes.class)
public final class PointOfInterestTypesMixin
{
	@ModifyVariable(
		method = "register",
		ordinal = 0,
		at = @At(
			value = "LOAD"
		)
	)
	private static PointOfInterestType friendsandfoes_modifyBeehivePointOfInterest(
		PointOfInterestType pointOfInterestType,
		Registry<PointOfInterestType> registry,
		RegistryKey<PointOfInterestType> key,
		Set<BlockState> states,
		int ticketCount,
		int searchDistance
	) {
		if (key != PointOfInterestTypes.BEEHIVE) {
			return pointOfInterestType;
		}

		ticketCount = 1;

		return new PointOfInterestType(
			pointOfInterestType.blockStates(),
			ticketCount,
			pointOfInterestType.searchDistance()
		);
	}
}
