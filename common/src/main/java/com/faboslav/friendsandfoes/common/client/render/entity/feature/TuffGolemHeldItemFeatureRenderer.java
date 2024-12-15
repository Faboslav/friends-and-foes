package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public final class TuffGolemHeldItemFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M>
{
	private final ItemInHandRenderer heldItemRenderer;
	private final ItemRenderer itemRenderer;

	public TuffGolemHeldItemFeatureRenderer(
		RenderLayerParent<T, M> context,
		ItemInHandRenderer heldItemRenderer,
		ItemRenderer itemRenderer
	) {
		super(context);
		this.heldItemRenderer = heldItemRenderer;
		this.itemRenderer = itemRenderer;
	}

	public void render(
		PoseStack matrices,
		MultiBufferSource vertexConsumers,
		int light,
		T tuffGolem,
		float limbAngle,
		float limbDistance,
		float tickDelta,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		if (
			tuffGolem.isDeadOrDying()
			|| !((TuffGolemEntity) tuffGolem).isHoldingItem()
		) {
			return;
		}

		ItemStack itemStack = tuffGolem.getItemBySlot(EquipmentSlot.MAINHAND);
		BakedModel itemBakedModel = this.itemRenderer.getModel(itemStack, null, null, tuffGolem.getId());
		float yItemOffset = itemBakedModel.getTransforms().getTransform(ItemDisplayContext.GROUND).scale.y();
		float levitationOffset = Mth.sin(((float) tuffGolem.tickCount + tickDelta) / 10.0F + 3.1415927F) * 0.1F + 0.1F;
		float yOffset = levitationOffset + (0.85F - yItemOffset * 0.5F);
		float rotationAngle = (float) Math.toDegrees((animationProgress * 0.05F) % (2.0F * (float) Math.PI));
		matrices.pushPose();
		matrices.translate(0.0, yOffset, -0.575);
		matrices.mulPose(Axis.XP.rotationDegrees(180.0F));
		matrices.mulPose(Axis.YP.rotationDegrees(rotationAngle));

		this.heldItemRenderer.renderItem(
			tuffGolem,
			itemStack,
			ItemDisplayContext.GROUND,
			false,
			matrices,
			vertexConsumers,
			light
		);
		matrices.popPose();
	}
}