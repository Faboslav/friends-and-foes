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

@Environment(EnvType.CLIENT)
public final class IceologerEntityRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T>
{
	public IceologerEntityRenderer(Context context) {
		super(context, new IllagerModel<>(context.bakeLayer(FriendsAndFoesEntityModelLayers.ICEOLOGER_LAYER)), 0.5F);

		this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer())
		{
			public void render(
				PoseStack matrixStack,
				MultiBufferSource vertexConsumerProvider,
				int i,
				T spellcastingIllagerEntity,
				float f,
				float g,
				float h,
				float j,
				float k,
				float l
			) {
				if (spellcastingIllagerEntity.isCastingSpell()) {
					super.render(matrixStack, vertexConsumerProvider, i, spellcastingIllagerEntity, f, g, h, j, k, l);
				}

			}
		});

		this.model.getHat().visible = true;
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
			return FriendsAndFoes.makeID("textures/entity/illager/iceologer.png");
	}
}