package com.faboslav.friendsandfoes.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.IllusionerEntityRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.IllusionerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Environment(EnvType.CLIENT)
@Mixin({IllusionerEntityRenderer.class})
public abstract class IllusionerEntityRendererMixin extends IllagerEntityRenderer<IllusionerEntity>
{
	protected IllusionerEntityRendererMixin(
		EntityRendererFactory.Context ctx,
		IllagerEntityModel<IllusionerEntity> model,
		float shadowRadius
	) {
		super(ctx, model, shadowRadius);
	}

	@Overwrite
	public void render(
		IllusionerEntity mobEntity,
		float f,
		float g,
		MatrixStack matrixStack,
		VertexConsumerProvider vertexConsumerProvider,
		int i
	) {
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	@Overwrite
	public boolean isVisible(IllusionerEntity illusioner) {
		return super.isVisible(illusioner);
	}
}
