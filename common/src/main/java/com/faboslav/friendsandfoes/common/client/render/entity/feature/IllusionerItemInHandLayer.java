package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;

//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?}

//? if <=1.21.8 {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?}

//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.IllusionerRenderState;
//?} else {
/*import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.entity.monster.illager.SpellcasterIllager;
*///?}

//? if >=1.21.3 {
public final class IllusionerItemInHandLayer extends ItemInHandLayer<IllusionerRenderState, IllagerModel<IllusionerRenderState>>
//?} else {
/*public final class IllusionerItemInHandLayer<T extends SpellcasterIllager> extends ItemInHandLayer<T, IllagerModel<T>>
*///?}
{
	//? if >=1.21.3 {
	public IllusionerItemInHandLayer(RenderLayerParent<IllusionerRenderState, IllagerModel<IllusionerRenderState>> renderer) {
		super(renderer);
	}
	//?} else {
	/*public IllusionerItemInHandLayer(RenderLayerParent<T, IllagerModel<T>> renderer, ItemInHandRenderer itemInHandRenderer) {
		super(renderer, itemInHandRenderer);
	}
	*///?}

	//? if >=1.21.9 {
	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, IllusionerRenderState renderState, float f, float g) {
		if (renderState.isCastingSpell || renderState.isAggressive) {
			super.submit(poseStack, submitNodeCollector, i, renderState, f, g);
		}
	}
	//?} else if >=1.21.3 {
	/*@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, IllusionerRenderState renderState, float f, float g) {
		if (renderState.isCastingSpell || renderState.isAggressive) {
			super.render(poseStack, multiBufferSource, i, renderState, f, g);
		}
	}
	*///?} else {
	/*@Override
	public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, T illusioner, float f, float g, float h, float j, float k, float l) {
		if (illusioner.isCastingSpell() || illusioner.isAggressive()) {
			super.render(matrixStack, vertexConsumerProvider, i, illusioner, f, g, h, j, k, l);
		}
	}
	*///?}
}
