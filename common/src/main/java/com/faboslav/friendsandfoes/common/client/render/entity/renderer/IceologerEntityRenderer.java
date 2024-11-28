package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.util.Identifier;

/*? >=1.21.2 {*/
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import com.faboslav.friendsandfoes.common.client.render.entity.state.IceologerEntityRenderState;
/*?}*/

@Environment(EnvType.CLIENT)
/*? >=1.21.2 {*/
public final class IceologerEntityRenderer<T extends SpellcastingIllagerEntity> extends IllagerEntityRenderer<T, IceologerEntityRenderState>
/*?} else {*/
/*public final class IceologerEntityRenderer<T extends SpellcastingIllagerEntity> extends IllagerEntityRenderer<T>
*//*?}*/
{
	public static final Identifier TEXTURE = FriendsAndFoes.makeID("textures/entity/illager/iceologer.png");

	/*? >=1.21.2 {*/
	public IceologerEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new IllagerEntityModel<>(context.getPart(FriendsAndFoesEntityModelLayers.ICEOLOGER_LAYER)), 0.5F);
		this.model.getHat().visible = true;

		this.addFeature(new HeldItemFeatureRenderer<>(this, context.getItemRenderer())
		{
			public void render(
				MatrixStack matrixStack,
				VertexConsumerProvider vertexConsumerProvider,
				int i,
				IceologerEntityRenderState iceologerEntityRenderState,
				float f,
				float g
			) {
				if (iceologerEntityRenderState.spellcasting) {
					super.render(matrixStack, vertexConsumerProvider, i, iceologerEntityRenderState, f, g);
				}

			}
		});
	}

	@Override
	public Identifier getTexture(IceologerEntityRenderState iceologerEntityRenderState) {
		return TEXTURE;
	}

	@Override
	public IceologerEntityRenderState createRenderState() {
		return new IceologerEntityRenderState();
	}

	public void updateRenderState(T spellcastingIllagerEntity, IceologerEntityRenderState iceologerEntityRenderState, float f) {
		super.updateRenderState(spellcastingIllagerEntity, iceologerEntityRenderState, f);
		iceologerEntityRenderState.spellcasting = spellcastingIllagerEntity.isSpellcasting();
	}
	/*?} else {*/
	/*public IceologerEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new IllagerEntityModel<>(context.getPart(FriendsAndFoesEntityModelLayers.ICEOLOGER_LAYER)), 0.5F);
		this.model.getHat().visible = true;

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
	}

	@Override
	public Identifier getTexture(T entity) {
		return TEXTURE;
	}
	*//*?}*/
}