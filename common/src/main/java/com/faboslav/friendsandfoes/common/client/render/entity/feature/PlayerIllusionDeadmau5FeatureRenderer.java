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
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;

@Environment(value = EnvType.CLIENT)
public final class PlayerIllusionDeadmau5FeatureRenderer extends RenderLayer<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>>
{
	public PlayerIllusionDeadmau5FeatureRenderer(RenderLayerParent<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>> arg) {
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
		if ("deadmau5".equals(arg3.getName().getString()) && !arg3.isInvisible()) {
			VertexConsumer vertexConsumer = arg2.getBuffer(RenderType.entitySolid(arg3.getSkinTextures().texture()));
			int m = LivingEntityRenderer.getOverlayCoords(arg3, 0.0F);

			for (int n = 0; n < 2; ++n) {
				float o = Mth.lerp(h, arg3.yRotO, arg3.getYRot()) - Mth.lerp(h, arg3.yBodyRotO, arg3.yBodyRot);
				float p = Mth.lerp(h, arg3.xRotO, arg3.getXRot());
				arg.pushPose();
				arg.mulPose(Axis.YP.rotationDegrees(o));
				arg.mulPose(Axis.XP.rotationDegrees(p));
				arg.translate(0.375F * (float) (n * 2 - 1), 0.0F, 0.0F);
				arg.translate(0.0F, -0.375F, 0.0F);
				arg.mulPose(Axis.XP.rotationDegrees(-p));
				arg.mulPose(Axis.YP.rotationDegrees(-o));
				float q = 1.3333334F;
				arg.scale(1.3333334F, 1.3333334F, 1.3333334F);
				this.getParentModel().renderEars(arg, vertexConsumer, i, m);
				arg.popPose();
			}

		}
	}
}
