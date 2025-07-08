package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityRenderers;
import com.faboslav.friendsandfoes.common.util.PlayerSkinProvider;
import com.google.common.collect.ImmutableMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.UUID;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.Entity;

//? >= 1.21.5 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.PlayerIllusionRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
//?}

@Mixin(EntityRenderDispatcher.class)
@SuppressWarnings({"unchecked"})
public abstract class EntityRenderDispatcherMixin
{
	@Unique
	private Map<PlayerSkin.Model, EntityRenderer<? extends PlayerIllusionEntity/*? >=1.21.3 {*/, ?/*?}*/>> friendsandfoes$illusionModelRenderers = ImmutableMap.of();

	@Inject(
		method = "getRenderer(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;",
		at = @At("HEAD"),
		cancellable = true
	)
	public <T extends Entity> void friendsandfoes$getRenderer(
		T entity,
		CallbackInfoReturnable<EntityRenderer<? super T/*? >=1.21.3 {*/, ?/*?}*/>> cir
	) {
		if (entity instanceof PlayerIllusionEntity) {
			PlayerIllusionEntity playerIllusion = (PlayerIllusionEntity) entity;
			UUID uuid = playerIllusion.getPlayerUuid();

			if (uuid == null) {
				uuid = playerIllusion.getUUID();
			}

			PlayerSkin.Model model = PlayerSkinProvider.getSkinTextures(uuid).model();
			EntityRenderer<? extends PlayerIllusionEntity/*? >=1.21.3 {*/, ?/*?}*/> entityRenderer = this.friendsandfoes$illusionModelRenderers.get(model);
			entityRenderer = entityRenderer != null ? entityRenderer:this.friendsandfoes$illusionModelRenderers.get(PlayerSkin.Model.WIDE);
			cir.setReturnValue((EntityRenderer<? super T/*? >=1.21.3 {*/, ?/*?}*/>) entityRenderer);
		}
	}

	//? >= 1.21.5 {
	@Inject(
		method = "getRenderer(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;)Lnet/minecraft/client/renderer/entity/EntityRenderer;",
		at = @At("HEAD"),
		cancellable = true
	)
	public <S extends EntityRenderState> void friendsandfoes$getRendererBasedOnRenderState(
		EntityRenderState renderState, CallbackInfoReturnable<EntityRenderer<?, ? super S>> cir
	) {
		if (renderState instanceof PlayerIllusionRenderState playerIllusionRenderState) {
			PlayerSkin.Model model = playerIllusionRenderState.skin.model();
			EntityRenderer<? extends PlayerIllusionEntity/*? >=1.21.3 {*/, ?/*?}*/> entityRenderer = this.friendsandfoes$illusionModelRenderers.get(model);
			entityRenderer = entityRenderer != null ? entityRenderer:this.friendsandfoes$illusionModelRenderers.get(PlayerSkin.Model.WIDE);
			cir.setReturnValue((EntityRenderer<?, ? super S>) entityRenderer);
		}
	}
	//?}

	@ModifyVariable(
		method = "onResourceManagerReload",
		ordinal = 0,
		at = @At(
			value = "LOAD"
		)
	)
	public EntityRendererProvider.Context friendsandfoes$reload(
		EntityRendererProvider.Context context
	) {
		this.friendsandfoes$illusionModelRenderers = FriendsAndFoesEntityRenderers.reloadPlayerIllusionRenderers(context);
		return context;
	}
}