package com.faboslav.friendsandfoes.mixin;

import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.structure.StructureType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(StructureType.class)
public interface StructureFeatureAccessor
{
	@Accessor("STRUCTURE_TO_GENERATION_STEP")
	static Map<StructureType<?>, GenerationStep.Feature> getStructureToGenerationStep() {
		throw new UnsupportedOperationException();
	}
}
