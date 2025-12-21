package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.illager.SpellcasterIllager;

//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?}

//? if <= 1.21.8 {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?}

//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.IceologerRenderState;
//?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? if >=1.21.3 {
public class IceologerEntityRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T, IceologerRenderState>
//?} else {
/*public final class IceologerEntityRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T>
*///?}
{
	private static final Identifier TEXTURE = FriendsAndFoes.makeID("textures/entity/illager/iceologer.png");

	public IceologerEntityRenderer(Context context) {
		super(context, new IllagerModel<>(context.bakeLayer(FriendsAndFoesEntityModelLayers.ICEOLOGER_LAYER)), 0.5F);

		//? if >= 1.21.9 {
		this.addLayer(new ItemInHandLayer<>(this)
		{
			public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, IceologerRenderState renderState, float f, float g) {
				if (renderState.isCastingSpell) {
					super.submit(poseStack, submitNodeCollector, i, renderState, f, g);
				}

			}
		});
		//?} else if >= 1.21.3 {
		/*this.addLayer(new ItemInHandLayer<>(this)
		{
			public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, IceologerRenderState renderState, float f, float g) {
				if (renderState.isCastingSpell) {
					super.render(poseStack, multiBufferSource, i, renderState, f, g);
				}
			}
		});
		*///?} else {
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

	//? if >=1.21.3 {
	@Override
	public IceologerRenderState createRenderState() {
		return new IceologerRenderState();
	}

	@Override
	public void extractRenderState(T iceologer, IceologerRenderState renderState, float partialTick) {
		super.extractRenderState(iceologer, renderState, partialTick);
		renderState.isCastingSpell = iceologer.isCastingSpell();
	}
	//?}

	@Override
	//? if >=1.21.3 {
	public Identifier getTextureLocation(IceologerRenderState renderState)
	//?} else {
	/*public ResourceLocation getTextureLocation(T iceologer)
	*///?}
	{
		return TEXTURE;
	}
}