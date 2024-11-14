package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class IceologerEntityRenderer<T extends SpellcastingIllagerEntity> extends IllagerEntityRenderer<T>
{
	public IceologerEntityRenderer(Context context) {
		super(context, new IllagerEntityModel<>(context.getPart(FriendsAndFoesEntityModelLayers.ICEOLOGER_LAYER)), 0.5F);

		this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer())
		{
			public void render(
				MatrixStack matrixStack,
				VertexConsumerProvider vertexConsumerProvider,
				int i,
				T spellcastingIllagerEntity,
				float f,
				float g,
				float h,
				float j,
				float k,
				float l
			) {
				if (spellcastingIllagerEntity.isSpellcasting()) {
					super.render(matrixStack, vertexConsumerProvider, i, spellcastingIllagerEntity, f, g, h, j, k, l);
				}

			}
		});

		this.model.getHat().visible = true;
	}

	@Override
	public Identifier getTexture(T entity) {
			return FriendsAndFoes.makeID("textures/entity/illager/iceologer.png");
	}
}