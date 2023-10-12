package com.faboslav.friendsandfoes.client.render.entity.feature;


import com.faboslav.friendsandfoes.client.render.entity.model.PlayerIllusionEntityModel;
import com.faboslav.friendsandfoes.entity.PlayerIllusionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public final class CapeFeatureRenderer extends FeatureRenderer<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>>
{
	public CapeFeatureRenderer(FeatureRendererContext<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>> featureRendererContext) {
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
		if (playerIllusionEntity.canRenderCapeTexture() && !playerIllusionEntity.isInvisible() && playerIllusionEntity.isPartVisible(PlayerModelPart.CAPE) && playerIllusionEntity.getCapeTexture() != null) {
			ItemStack itemStack = playerIllusionEntity.getEquippedStack(EquipmentSlot.CHEST);
			if (!itemStack.isOf(Items.ELYTRA)) {
				matrixStack.push();
				matrixStack.translate(0.0, 0.0, 0.125);
				double d = MathHelper.lerp(h, playerIllusionEntity.prevCapeX, playerIllusionEntity.capeX) - MathHelper.lerp(h, playerIllusionEntity.prevX, playerIllusionEntity.getX());
				double e = MathHelper.lerp(h, playerIllusionEntity.prevCapeY, playerIllusionEntity.capeY) - MathHelper.lerp(h, playerIllusionEntity.prevY, playerIllusionEntity.getY());
				double m = MathHelper.lerp(h, playerIllusionEntity.prevCapeZ, playerIllusionEntity.capeZ) - MathHelper.lerp(h, playerIllusionEntity.prevZ, playerIllusionEntity.getZ());
				float n = playerIllusionEntity.prevBodyYaw + (playerIllusionEntity.bodyYaw - playerIllusionEntity.prevBodyYaw);
				double o = MathHelper.sin(n * 0.017453292F);
				double p = -MathHelper.cos(n * 0.017453292F);
				float q = (float) e * 10.0F;
				q = MathHelper.clamp(q, -6.0F, 32.0F);
				float r = (float) (d * o + m * p) * 100.0F;
				r = MathHelper.clamp(r, 0.0F, 150.0F);
				float s = (float) (d * p - m * o) * 100.0F;
				s = MathHelper.clamp(s, -20.0F, 20.0F);
				if (r < 0.0F) {
					r = 0.0F;
				}

				float t = MathHelper.lerp(h, playerIllusionEntity.prevStrideDistance, playerIllusionEntity.strideDistance);
				q += MathHelper.sin(MathHelper.lerp(h, playerIllusionEntity.prevHorizontalSpeed, playerIllusionEntity.horizontalSpeed) * 6.0F) * 32.0F * t;
				if (playerIllusionEntity.isInSneakingPose()) {
					q += 25.0F;
				}

				matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(6.0F + r / 2.0F + q));
				matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(s / 2.0F));
				matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F - s / 2.0F));
				VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(playerIllusionEntity.getCapeTexture()));
				this.getContextModel().renderCape(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
				matrixStack.pop();
			}
		}
	}
}