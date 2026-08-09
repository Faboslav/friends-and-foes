
package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.client.render.entity.model.PlayerIllusionEntityModel;
import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.faboslav.friendsandfoes.common.util.PlayerSkinProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.UUID;

public class PlayerIllusionCapeLayer extends RenderLayer<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>>
{
	public PlayerIllusionCapeLayer(RenderLayerParent<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>> renderer) {
		super(renderer);
	}

	public void render(
		PoseStack poseStack,
		MultiBufferSource buffer,
		int packedLight,
		PlayerIllusionEntity playerIllusion,
		float limbSwing,
		float limbSwingAmount,
		float partialTicks,
		float ageInTicks,
		float netHeadYaw,
		float headPitch
	) {
		if (!playerIllusion.isInvisible() && playerIllusion.isPartVisible(PlayerModelPart.CAPE)) {
			PlayerSkin playerSkin = PlayerSkinProvider.getSkinTextures(playerIllusion);
			if (playerSkin.capeTexture() != null) {
				ItemStack itemStack = playerIllusion.getItemBySlot(EquipmentSlot.CHEST);
				if (!itemStack.is(Items.ELYTRA)) {
					poseStack.pushPose();
					poseStack.translate(0.0F, 0.0F, 0.125F);
					double d = Mth.lerp(partialTicks, playerIllusion.prevCapeX, playerIllusion.capeX) - Mth.lerp(partialTicks, playerIllusion.xo, playerIllusion.getX());
					double e = Mth.lerp(partialTicks, playerIllusion.prevCapeY, playerIllusion.capeY) - Mth.lerp(partialTicks, playerIllusion.yo, playerIllusion.getY());
					double f = Mth.lerp(partialTicks, playerIllusion.prevCapeZ, playerIllusion.capeZ) - Mth.lerp(partialTicks, playerIllusion.zo, playerIllusion.getZ());
					float g = Mth.rotLerp(partialTicks, playerIllusion.yBodyRotO, playerIllusion.yBodyRot);
					double h = Mth.sin(g * (float) (Math.PI / 180.0));
					double i = -Mth.cos(g * (float) (Math.PI / 180.0));
					float j = (float)e * 10.0F;
					j = Mth.clamp(j, -6.0F, 32.0F);
					float k = (float)(d * h + f * i) * 100.0F;
					k = Mth.clamp(k, 0.0F, 150.0F);
					float l = (float)(d * i - f * h) * 100.0F;
					l = Mth.clamp(l, -20.0F, 20.0F);
					if (k < 0.0F) {
						k = 0.0F;
					}

					float m = Mth.lerp(partialTicks, playerIllusion.prevStrideDistance, playerIllusion.strideDistance);
					j += Mth.sin(Mth.lerp(partialTicks, playerIllusion.walkDistO, playerIllusion.walkDist) * 6.0F) * 32.0F * m;
					if (playerIllusion.isCrouching()) {
						j += 25.0F;
					}

					poseStack.mulPose(Axis.XP.rotationDegrees(6.0F + k / 2.0F + j));
					poseStack.mulPose(Axis.ZP.rotationDegrees(l / 2.0F));
					poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - l / 2.0F));
					VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(playerSkin.capeTexture()));
					this.getParentModel().renderCloak(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
					poseStack.popPose();
				}
			}
		}
	}
}

