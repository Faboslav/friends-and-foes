package com.faboslav.friendsandfoes.neoforge.modcompat.curios;

//? if curios: >0 {
/*import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CuriosTotemRenderer implements ICurioRenderer
{
	@Override
	public <T extends LivingEntity, M extends EntityModel<T>> void render(
		ItemStack itemStack,
		SlotContext slotContext,
		PoseStack matrices,
		RenderLayerParent<T, M> featureRendererContext,
		MultiBufferSource vertexConsumers,
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
		matrices.mulPose(Direction.DOWN.getRotation());

		Minecraft.getInstance().getItemRenderer().renderStatic(
			itemStack,
			ItemDisplayContext.FIXED,
			light,
			OverlayTexture.NO_OVERLAY,
			matrices,
			vertexConsumers,
			null,
			0
		);
	}
}
*///?}