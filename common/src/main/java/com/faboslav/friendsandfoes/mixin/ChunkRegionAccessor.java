package com.faboslav.friendsandfoes.mixin;

import net.minecraft.world.ChunkRegion;
import net.minecraft.world.gen.StructureAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkRegion.class)
public interface ChunkRegionAccessor
{
	@Accessor("structureAccessor")
	StructureAccessor getStructureAccessor();
}
