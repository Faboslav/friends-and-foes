package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.client.render.entity.model.PlayerIllusionEntityModel;
import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(value = EnvType.CLIENT)
public final class PlayerIllusionDeadmau5FeatureRenderer extends FeatureRenderer<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>>
{
	public PlayerIllusionDeadmau5FeatureRenderer(FeatureRendererContext<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>> featureRendererContext) {
		super(featureRendererContext);
	}

	public void render(
		MatrixStack matrixStack,
		VertexConsumerProvider vertexConsumerProvider,
		int i,
		PlayerIllusionEntity playerIllusionEntity,
		float f,
		float g,
		float h,
		float j,
		float k,
		float l
	) {
		if ("deadmau5".equals(playerIllusionEntity.getName().getString()) && playerIllusionEntity.hasSkinTexture() && !playerIllusionEntity.isInvisible()) {
			VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(playerIllusionEntity.getSkinTexture()));
			int m = LivingEntityRenderer.getOverlay(playerIllusionEntity, 0.0F);

			for (int n = 0; n < 2; ++n) {
				float o = MathHelper.lerp(h, playerIllusionEntity.prevYaw, playerIllusionEntity.getYaw()) - MathHelper.lerp(h, playerIllusionEntity.prevBodyYaw, playerIllusionEntity.bodyYaw);
				float p = MathHelper.lerp(h, playerIllusionEntity.prevPitch, playerIllusionEntity.getPitch());
				matrixStack.push();
				matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(o));
				matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(p));
				matrixStack.translate(0.375F * (float) (n * 2 - 1), 0.0, 0.0);
				matrixStack.translate(0.0, -0.375, 0.0);
				matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-p));
				matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-o));
				float q = 1.3333334F;
				matrixStack.scale(1.3333334F, 1.3333334F, 1.3333334F);
				this.getContextModel().renderEars(matrixStack, vertexConsumer, i, m);
				matrixStack.pop();
			}

		}
	}
}
