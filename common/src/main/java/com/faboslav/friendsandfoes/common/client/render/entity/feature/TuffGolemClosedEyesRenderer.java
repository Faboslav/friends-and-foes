package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.TuffGolemEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.client.renderer.MultiBufferSource;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;

public final class TuffGolemClosedEyesRenderer extends RenderLayer<TuffGolemEntity, TuffGolemEntityModel<TuffGolemEntity>>

{
	private static final ResourceLocation CLOSED_EYES_TEXTURE = FriendsAndFoes.makeID("textures/entity/tuff_golem/closed_eyes.png");

	public TuffGolemClosedEyesRenderer(RenderLayerParent<TuffGolemEntity, TuffGolemEntityModel<TuffGolemEntity>> featureRendererContext) {
		super(featureRendererContext);
	}

	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, TuffGolemEntity tuffGolem, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch)

	{

		if (tuffGolem.isInvisible() || !tuffGolem.isInSleepingPose()) {
			return;
		}

		renderColoredCutoutModel(
			this.getParentModel(),
			CLOSED_EYES_TEXTURE,
			poseStack,

			bufferSource,

			packedLight,

			tuffGolem,

			-1

		);
	}
}

