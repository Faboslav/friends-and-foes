package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.IllusionerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Illusioner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? >=1.21.3 {
import net.minecraft.client.renderer.entity.state.IllusionerRenderState;
//?}

@Environment(EnvType.CLIENT)
@Mixin({IllusionerRenderer.class})
//? >=1.21.3 {
public abstract class IllusionerEntityRendererMixin extends IllagerRenderer<Illusioner, IllusionerRenderState>
//?} else {
/*public abstract class IllusionerEntityRendererMixin extends IllagerRenderer<Illusioner>
*///?}
{
	private static final ResourceLocation friendsandfoes$ILLUSIONER_TEXTURE = FriendsAndFoes.makeID("textures/entity/illusioner/illusioner.png");

	//? >=1.21.3 {
	protected IllusionerEntityRendererMixin(
		EntityRendererProvider.Context context,
		IllagerModel<IllusionerRenderState> model,
		float shadowRadius
	) {
		super(context, model, shadowRadius);
	}
	//?} else {
	/*protected IllusionerEntityRendererMixin(
		EntityRendererProvider.Context ctx,
		IllagerModel<Illusioner> model,
		float shadowRadius
	) {
		super(ctx, model, shadowRadius);
	}
	*///?}

	//? >=1.21.3 {
	@Inject(
		at = @At("HEAD"),
		method = "getTextureLocation*",
		cancellable = true
	)
	public ResourceLocation getTextureLocation(IllusionerRenderState illusionerRenderState, CallbackInfoReturnable<ResourceLocation> cir) {
		if (FriendsAndFoes.getConfig().enableIllusioner) {
			cir.setReturnValue(friendsandfoes$ILLUSIONER_TEXTURE);
		}

		return cir.getReturnValue();
	}
	//?} else {
		/*@Inject(
		at = @At("HEAD"),
		method = "getTextureLocation*",
		cancellable = true
	)
	public void getTextureLocation(Illusioner illusioner, CallbackInfoReturnable<ResourceLocation> cir) {
		if (FriendsAndFoes.getConfig().enableIllusioner) {
			cir.setReturnValue(friendsandfoes$ILLUSIONER_TEXTURE);
		}
	}
	*///?}
}
