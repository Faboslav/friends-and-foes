package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.client.render.entity.model.PlayerIllusionEntityModel;
import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class PlayerIllusionCapeFeatureRenderer extends RenderLayer<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>>
{
	public PlayerIllusionCapeFeatureRenderer(RenderLayerParent<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>> arg) {
		super(arg);
	}

	public void render(
		PoseStack arg,
		MultiBufferSource arg2,
		int i,
		PlayerIllusionEntity arg3,
		float f,
		float g,
		float h,
		float j,
		float k,
		float l
	) {
		if (!arg3.isInvisible() && arg3.isPartVisible(PlayerModelPart.CAPE)) {
			PlayerSkin skinTextures = arg3.getSkinTextures();
			if (skinTextures.capeTexture() != null) {
				ItemStack itemStack = arg3.getItemBySlot(EquipmentSlot.CHEST);
				if (!itemStack.is(Items.ELYTRA)) {
					arg.pushPose();
					arg.translate(0.0F, 0.0F, 0.125F);
					double d = Mth.lerp(h, arg3.prevCapeX, arg3.capeX) - Mth.lerp(h, arg3.xo, arg3.getX());
					double e = Mth.lerp(h, arg3.prevCapeY, arg3.capeY) - Mth.lerp(h, arg3.yo, arg3.getY());
					double m = Mth.lerp(h, arg3.prevCapeZ, arg3.capeZ) - Mth.lerp(h, arg3.zo, arg3.getZ());
					float n = Mth.rotLerp(h, arg3.yBodyRotO, arg3.yBodyRot);
					double o = Mth.sin(n * 0.017453292F);
					double p = -Mth.cos(n * 0.017453292F);
					float q = (float) e * 10.0F;
					q = Mth.clamp(q, -6.0F, 32.0F);
					float r = (float) (d * o + m * p) * 100.0F;
					r = Mth.clamp(r, 0.0F, 150.0F);
					float s = (float) (d * p - m * o) * 100.0F;
					s = Mth.clamp(s, -20.0F, 20.0F);
					if (r < 0.0F) {
						r = 0.0F;
					}

					float t = Mth.lerp(h, arg3.prevStrideDistance, arg3.strideDistance);
					q += Mth.sin(Mth.lerp(h, arg3.walkDistO, arg3.walkDist) * 6.0F) * 32.0F * t;
					if (arg3.isCrouching()) {
						q += 25.0F;
					}

					arg.mulPose(Axis.XP.rotationDegrees(6.0F + r / 2.0F + q));
					arg.mulPose(Axis.ZP.rotationDegrees(s / 2.0F));
					arg.mulPose(Axis.YP.rotationDegrees(180.0F - s / 2.0F));
					VertexConsumer vertexConsumer = arg2.getBuffer(RenderType.entitySolid(skinTextures.capeTexture()));
					this.getParentModel().renderCloak(arg, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
					arg.popPose();
				}
			}
		}
	}
}
