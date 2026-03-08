package com.faboslav.friendsandfoes.neoforge.modcompat.curios;

//? if curios {
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

//? >= 1.21.4 {
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
//?} else {
/*import net.minecraft.world.entity.LivingEntity;
*///?}

@SuppressWarnings({"all"})
public class CuriosTotemRenderer implements ICurioRenderer
{
	@Override
	//? >= 1.21.4 {
	public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(
		ItemStack itemStack,
		SlotContext slotContext,
		PoseStack poseStack,
		MultiBufferSource multiBufferSource,
		int packedLight,
		S renderState,
		RenderLayerParent<S, M> renderLayerParent,
		EntityRendererProvider.Context context,
		float yRotation,
		float xRotation
	)
	//?} else {
	/*public <T extends LivingEntity, M extends EntityModel<T>> void render(
		ItemStack itemStack,
		SlotContext slotContext,
		PoseStack poseStack,
		RenderLayerParent<T, M> featureRendererContext,
		MultiBufferSource multiBufferSource,
		int packedLight,
		float limbSwing,
		float limbSwingAmount,
		float partialTicks,
		float ticks,
		float headYaw,
		float headPitch
	)
	*///?}
	{
		var livingEntity = slotContext.entity();

		ICurioRenderer.translateIfSneaking(poseStack, livingEntity);
		ICurioRenderer.rotateIfSneaking(poseStack, livingEntity);

		poseStack.scale(0.35F, 0.35F, 0.35F);
		poseStack.translate(0.0F, 1.1F, -0.4F);
		poseStack.mulPose(Direction.DOWN.getRotation());

		Minecraft.getInstance().getItemRenderer().renderStatic(
			itemStack,
			ItemDisplayContext.FIXED,
			packedLight,
			OverlayTexture.NO_OVERLAY,
			poseStack,
			multiBufferSource,
			null,
			0
		);
	}
}
//?}