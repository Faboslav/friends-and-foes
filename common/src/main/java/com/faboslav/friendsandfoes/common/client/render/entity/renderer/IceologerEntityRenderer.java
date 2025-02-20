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
import com.faboslav.friendsandfoes.common.client.render.entity.state.IceologerRenderState;
//?}

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
//? >=1.21.3 {
public class IceologerEntityRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T, IceologerRenderState>
//?} else {
/*public final class IceologerEntityRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T>
*///?}
{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/illager/iceologer.png");

	public IceologerEntityRenderer(Context context) {
		super(context, new IllagerModel<>(context.bakeLayer(FriendsAndFoesEntityModelLayers.ICEOLOGER_LAYER)), 0.5F);

		//? >=1.21.3 {
		this.addLayer(new ItemInHandLayer<IceologerRenderState, IllagerModel<IceologerRenderState>>(this, context.getItemRenderer()) {
			public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, IceologerRenderState renderState, float f, float g) {
				if (renderState.isCastingSpell) {
					super.render(poseStack, multiBufferSource, i, renderState, f, g);
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
	//? >=1.21.3 {
	public ResourceLocation getTextureLocation(IceologerRenderState renderState)
	//?} else {
	/*public ResourceLocation getTextureLocation(T iceologer)
	*///?}
	{
		return TEXTURE;
	}
}