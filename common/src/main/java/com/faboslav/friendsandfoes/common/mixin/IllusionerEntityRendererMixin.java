package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.IllusionerRenderer;
import net.minecraft.world.entity.monster.Illusioner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin({IllusionerRenderer.class})
public abstract class IllusionerEntityRendererMixin extends IllagerRenderer<Illusioner>
{
	protected IllusionerEntityRendererMixin(
		EntityRendererProvider.Context ctx,
		IllagerModel<Illusioner> model,
		float shadowRadius
	) {
		super(ctx, model, shadowRadius);
	}

	@Inject(
		at = @At("HEAD"),
		method = "render(Lnet/minecraft/world/entity/monster/Illusioner;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
		cancellable = true
	)
	public void friendsandfoes_render(
		Illusioner mobEntity,
		float f,
		float g,
		PoseStack matrixStack,
		MultiBufferSource vertexConsumerProvider,
		int i,
		CallbackInfo ci
	) {
		if (FriendsAndFoes.getConfig().enableIllusioner) {
			super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
			ci.cancel();
		}
	}

	@Inject(
		at = @At("HEAD"),
		method = "isBodyVisible(Lnet/minecraft/world/entity/monster/Illusioner;)Z",
		cancellable = true
	)
	protected void friendsandfoes_isVisible(
		Illusioner illusioner,
		CallbackInfoReturnable<Boolean> callbackInfo
	) {
		if (FriendsAndFoes.getConfig().enableIllusioner) {
			callbackInfo.setReturnValue(super.isBodyVisible(illusioner));
		}
	}
}
