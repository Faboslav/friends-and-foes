package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.IllusionerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.illager.SpellcasterIllager;

//? if >= 1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?}

//? if <= 1.21.8 {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?}

//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.IllusionerRenderState;
//?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? if >=1.21.3 {
public class IllusionerEntityRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T, IllusionerRenderState>
//?} else {
/*public final class IllusionerEntityRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T>
*///?}
{
	private static final Identifier TEXTURE = FriendsAndFoes.makeID("textures/entity/illusioner/illusioner.png");

	public IllusionerEntityRenderer(Context context) {
		super(context, new IllagerModel<>(context.bakeLayer(FriendsAndFoesEntityModelLayers.ILLUSIONER_LAYER)), 0.5F);

		//? if >= 1.21.9 {
		this.addLayer(new ItemInHandLayer<>(this)
		{
			public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, IllusionerRenderState renderState, float f, float g) {
				if (renderState.isCastingSpell || renderState.isAggressive) {
					super.submit(poseStack, submitNodeCollector, i, renderState, f, g);
				}

			}
		});
		//?} else if >= 1.21.3 {
		/*this.addLayer(new ItemInHandLayer<>(this)
		{
			public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, IllusionerRenderState illusionerRenderState, float f, float g) {
				if (illusionerRenderState.isCastingSpell || illusionerRenderState.isAggressive) {
					super.render(poseStack, multiBufferSource, i, illusionerRenderState, f, g);
				}

			}
		});
		*///?} else {
		/*this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer())
		{
			public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, T illusioner, float f, float g, float h, float j, float k, float l) {
				if (illusioner.isCastingSpell() || illusioner.isAggressive()) {
					super.render(matrixStack, vertexConsumerProvider, i, illusioner, f, g, h, j, k, l);
				}

			}
		});
		*///?}

		this.model.getHat().visible = true;
	}

	//? if >=1.21.3 {
	@Override
	public IllusionerRenderState createRenderState() {
		return new IllusionerRenderState();
	}

	@Override
	public void extractRenderState(T illusioner, IllusionerRenderState renderState, float partialTick) {
		super.extractRenderState(illusioner, renderState, partialTick);
		renderState.isCastingSpell = illusioner.isCastingSpell();
	}
	//?}

	@Override
	//? if >=1.21.3 {
	public Identifier getTextureLocation(IllusionerRenderState renderState)
	//?} else {
	/*public ResourceLocation getTextureLocation(T illusioner)
	*///?}
	{
		return TEXTURE;
	}
}