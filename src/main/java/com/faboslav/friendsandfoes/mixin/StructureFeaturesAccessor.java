package com.faboslav.friendsandfoes.mixin;

import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StructureFeature.class)
public interface StructureFeaturesAccessor
{
	@Invoker
	static <F extends StructureFeature<?>> F invokeRegister(
		String name,
		F structureFeature,
		GenerationStep.Feature step
	) {
		throw new UnsupportedOperationException();
	}
}