package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.SpellcasterIllager;

//? >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.IllusionerRenderState;
//?}

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
//? >=1.21.3 {
public class IllusionerEntityRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T, IllusionerRenderState>
//?} else {
/*public final class IllusionerEntityRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T>
*///?}
{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/illusioner/illusioner.png");

	public IllusionerEntityRenderer(Context context) {
		super(context, new IllagerModel<>(context.bakeLayer(FriendsAndFoesEntityModelLayers.ILLUSIONER_LAYER)), 0.5F);

		//? >=1.21.3 {
		this.addLayer(new ItemInHandLayer<>(this)
		{
			public void render(
				PoseStack poseStack,
				MultiBufferSource multiBufferSource,
				int i,
				IllusionerRenderState illusionerRenderState,
				float f,
				float g
			) {
				if (illusionerRenderState.isCastingSpell || illusionerRenderState.isAggressive) {
					super.render(poseStack, multiBufferSource, i, illusionerRenderState, f, g);
				}

			}
		});
		//?} else {
				/*this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer())
		{
			public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, T spellcastingIllagerEntity, float f, float g, float h, float j, float k, float l) {
				if (spellcastingIllagerEntity.isCastingSpell()) {
					super.render(matrixStack, vertexConsumerProvider, i, spellcastingIllagerEntity, f, g, h, j, k, l);
				}

			}
		});
		*///?}

		this.model.getHat().visible = true;
	}

	//? >=1.21.3 {
	@Override
	public IllusionerRenderState createRenderState() {
		return new IllusionerRenderState();
	}

	@Override
	public void extractRenderState(T iceologer, IllusionerRenderState renderState, float partialTick) {
		super.extractRenderState(iceologer, renderState, partialTick);
		renderState.isCastingSpell = iceologer.isCastingSpell();
	}
	//?}

	@Override
	//? >=1.21.3 {
	public ResourceLocation getTextureLocation(IllusionerRenderState renderState)
	//?} else {
	/*public ResourceLocation getTextureLocation(T illusioner)
	*///?}
	{
		return TEXTURE;
	}
}