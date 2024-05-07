package com.faboslav.friendsandfoes.client.render.entity.renderer;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.model.CrabEntityModel;
import com.faboslav.friendsandfoes.entity.CrabEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class CrabEntityRenderer extends MobEntityRenderer<CrabEntity, CrabEntityModel<CrabEntity>>
{
	private static final float SHADOW_RADIUS = 0.5F;

	public CrabEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CrabEntityModel<>(context.getPart(FriendsAndFoesEntityModelLayers.CRAB_LAYER)), SHADOW_RADIUS);
	}

	@Override
	public Identifier getTexture(CrabEntity entity) {
		return FriendsAndFoes.makeID("textures/entity/crab/crab.png");
	}

	@Override
	public void render(
		CrabEntity crab,
		float f,
		float tickDelta,
		MatrixStack matrixStack,
		VertexConsumerProvider vertexConsumerProvider,
		int i
	) {
		this.shadowRadius = SHADOW_RADIUS * crab.getSize().getScaleModifier();
		super.render(crab, f, tickDelta, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	protected void scale(CrabEntity crab, MatrixStack matrixStack, float f) {
		CrabEntity.CrabSize size = crab.getSize();
		float scaleModifier = size.getScaleModifier();
		matrixStack.scale(scaleModifier, scaleModifier, scaleModifier);
	}
}