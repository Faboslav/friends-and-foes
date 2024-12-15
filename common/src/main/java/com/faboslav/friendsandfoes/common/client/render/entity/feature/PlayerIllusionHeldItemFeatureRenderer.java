package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Environment(EnvType.CLIENT)
public final class PlayerIllusionHeldItemFeatureRenderer<T extends PlayerIllusionEntity, M extends EntityModel<T> & ArmedModel & HeadedModel> extends ItemInHandLayer<T, M>
{
	private final ItemInHandRenderer playerHeldItemRenderer;
	private static final float HEAD_YAW = -0.5235988F;
	private static final float HEAD_ROLL = 1.5707964F;

	public PlayerIllusionHeldItemFeatureRenderer(
		RenderLayerParent<T, M> featureRendererContext,
		ItemInHandRenderer heldItemRenderer
	) {
		super(featureRendererContext, heldItemRenderer);
		this.playerHeldItemRenderer = heldItemRenderer;
	}

	protected void renderArmWithItem(
		LivingEntity entity,
		ItemStack stack,
		ItemDisplayContext transformationMode,
		HumanoidArm arm,
		PoseStack matrices,
		MultiBufferSource vertexConsumers,
		int light
	) {
		if (stack.is(Items.SPYGLASS) && entity.getUseItem() == stack && entity.swingTime == 0) {
			this.renderSpyglass(entity, stack, arm, matrices, vertexConsumers, light);
		} else {
			super.renderArmWithItem(entity, stack, transformationMode, arm, matrices, vertexConsumers, light);
		}

	}

	private void renderSpyglass(
		LivingEntity entity,
		ItemStack stack,
		HumanoidArm arm,
		PoseStack matrices,
		MultiBufferSource vertexConsumers,
		int light
	) {
		matrices.pushPose();
		ModelPart modelPart = this.getParentModel().getHead();
		float f = modelPart.xRot;
		modelPart.xRot = Mth.clamp(modelPart.xRot, -0.5235988F, 1.5707964F);
		modelPart.translateAndRotate(matrices);
		modelPart.xRot = f;
		CustomHeadLayer.translateToHead(matrices, false);
		boolean bl = arm == HumanoidArm.LEFT;
		matrices.translate((bl ? -2.5F:2.5F) / 16.0F, -0.0625, 0.0);
		this.playerHeldItemRenderer.renderItem(entity, stack, ItemDisplayContext.HEAD, false, matrices, vertexConsumers, light);
		matrices.popPose();
	}
}
