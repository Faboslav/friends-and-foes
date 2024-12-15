package com.faboslav.friendsandfoes.common.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin({ModelPart.class})
public interface ModelPartAccessor
{
	@Accessor("cubes")
	List<ModelPart.Cube> getCuboids();

	@Accessor("children")
	Map<String, ModelPart> getChildren();
}