package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

//? >=1.21.4 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.TuffGolemRenderState;
import com.faboslav.friendsandfoes.common.client.render.entity.model.TuffGolemEntityModel;
//?} else {
/*import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import net.minecraft.client.model.EntityModel;
*///?}

@Environment(EnvType.CLIENT)
//? >=1.21.3 {
public final class TuffGolemHeldItemFeatureRenderer extends RenderLayer<TuffGolemRenderState, TuffGolemEntityModel>
//?} else {
/*public final class TuffGolemHeldItemFeatureRenderer<T extends TuffGolemEntity, M extends EntityModel<T>> extends RenderLayer<T, M>
*///?}
{
	private final ItemInHandRenderer heldItemRenderer;

	//? >=1.21.3 {
	public TuffGolemHeldItemFeatureRenderer(RenderLayerParent<TuffGolemRenderState, TuffGolemEntityModel> renderer, ItemInHandRenderer heldItemRenderer) {
		super(renderer);
		this.heldItemRenderer = heldItemRenderer;
	}

	//?} else {
	
	/*public TuffGolemHeldItemFeatureRenderer(
		RenderLayerParent<T, M> context,
		ItemInHandRenderer heldItemRenderer
	) {
		super(context);
		this.heldItemRenderer = heldItemRenderer;
	}
	*///?}

	//? >=1.21.3 {
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, TuffGolemRenderState renderState, float yRot, float xRot)
	//?} else {
	/*public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T tuffGolem, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch)
	*///?}
	{
		//? >=1.21.3 {
		var tuffGolem = renderState.tuffGolem;
		var tickDelta = renderState.partialTick;
		var animationProgress = renderState.ageInTicks;
		//?}

		if (
			tuffGolem.isDeadOrDying()
			|| !tuffGolem.isHoldingItem()
		) {
			return;
		}
		ItemStack itemStack = tuffGolem.getItemBySlot(EquipmentSlot.MAINHAND);

		float yItemOffset = 0.2F;
		float levitationOffset = Mth.sin(((float) tuffGolem.tickCount + tickDelta) / 10.0F + 3.1415927F) * 0.1F + 0.1F;
		float yOffset = levitationOffset + (0.85F - yItemOffset * 0.5F);
		float rotationAngle = (float) Math.toDegrees((animationProgress * 0.05F) % (2.0F * (float) Math.PI));
		poseStack.pushPose();
		poseStack.translate(0.0, yOffset, -0.575);
		poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
		poseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle));

		this.heldItemRenderer.renderItem(
			tuffGolem,
			itemStack,
			ItemDisplayContext.GROUND,
			false,
			poseStack,
			bufferSource,
			packedLight
		);
		poseStack.popPose();
	}
}