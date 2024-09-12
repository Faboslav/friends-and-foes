package com.faboslav.friendsandfoes.forge.modcompat.curios;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CuriosTotemRenderer implements ICurioRenderer
{
	@Override
	public <T extends LivingEntity, M extends EntityModel<T>> void render(
		ItemStack itemStack,
		SlotContext slotContext,
		MatrixStack matrices,
		FeatureRendererContext<T, M> featureRendererContext,
		VertexConsumerProvider vertexConsumers,
		int light,
		float limbSwing,
		float limbSwingAmount,
		float partialTicks,
		float ticks,
		float headYaw,
		float headPitch
	) {
		var livingEntity = slotContext.entity();
		ICurioRenderer.translateIfSneaking(matrices, livingEntity);
		ICurioRenderer.rotateIfSneaking(matrices, livingEntity);

		matrices.scale(0.35F, 0.35F, 0.35F);
		matrices.translate(0.0F, 1.1F, -0.4F);
		matrices.multiply(Direction.DOWN.getRotationQuaternion());

		MinecraftClient.getInstance().getItemRenderer().renderItem(
			itemStack,
			ModelTransformationMode.FIXED,
			light,
			OverlayTexture.DEFAULT_UV,
			matrices,
			vertexConsumers,
			null,
			0
		);
	}
}