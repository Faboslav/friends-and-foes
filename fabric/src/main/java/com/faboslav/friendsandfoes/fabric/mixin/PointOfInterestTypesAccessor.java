package com.faboslav.friendsandfoes.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(PoiTypes.class)
public interface PointOfInterestTypesAccessor
{
	@Invoker("registerBlockStates")
	static void callRegisterStates(Holder<PoiType> poiTypeEntry, Set<BlockState> states) {
		throw new UnsupportedOperationException();
	}
}
