package com.faboslav.friendsandfoes.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(PoiTypes.class)
public final class PointOfInterestTypesMixin
{
	@ModifyVariable(
		method = "register",
		ordinal = 0,
		at = @At(
			value = "LOAD"
		)
	)
	private static PoiType friendsandfoes_modifyBeehivePointOfInterest(
		PoiType pointOfInterestType,
		Registry<PoiType> registry,
		ResourceKey<PoiType> key,
		Set<BlockState> states,
		int ticketCount,
		int searchDistance
	) {
		if (key != PoiTypes.BEEHIVE) {
			return pointOfInterestType;
		}

		ticketCount = 1;

		return new PoiType(
			pointOfInterestType.matchingStates(),
			ticketCount,
			pointOfInterestType.validRange()
		);
	}
}
