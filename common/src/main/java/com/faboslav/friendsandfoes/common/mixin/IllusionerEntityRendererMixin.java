package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.IllusionerEntityRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin({IllusionerEntityRenderer.class})
public abstract class IllusionerEntityRendererMixin extends IllagerEntityRenderer<IllusionerEntity>
{
	private final Identifier FRIENDSANDFOES_TEXTURE = FriendsAndFoes.makeID("textures/entity/illager/illusioner.png");

	protected IllusionerEntityRendererMixin(
		EntityRendererFactory.Context ctx,
		IllagerEntityModel<IllusionerEntity> model,
		float shadowRadius
	) {
		super(ctx, model, shadowRadius);
	}

	@Inject(
		at = @At("HEAD"),
		method = "render(Lnet/minecraft/entity/mob/IllusionerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
		cancellable = true
	)
	public void friendsandfoes_render(
		IllusionerEntity mobEntity,
		float f,
		float g,
		MatrixStack matrixStack,
		VertexConsumerProvider vertexConsumerProvider,
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
		method = "isVisible(Lnet/minecraft/entity/mob/IllusionerEntity;)Z",
		cancellable = true
	)
	protected void friendsandfoes_isVisible(
		IllusionerEntity illusioner,
		CallbackInfoReturnable<Boolean> callbackInfo
	) {
		if (FriendsAndFoes.getConfig().enableIllusioner) {
			callbackInfo.setReturnValue(super.isVisible(illusioner));
		}
	}

	@Inject(
		at = @At("HEAD"),
		method = "getTexture(Lnet/minecraft/entity/mob/IllusionerEntity;)Lnet/minecraft/util/Identifier;",
		cancellable = true
	)
	protected void friendsandfoes_getTexture(
		IllusionerEntity illusionerEntity,
		CallbackInfoReturnable<Identifier> cir
	) {
		if (FriendsAndFoes.getConfig().enableIllusioner) {
			cir.setReturnValue(FRIENDSANDFOES_TEXTURE);
		}
	}
}
