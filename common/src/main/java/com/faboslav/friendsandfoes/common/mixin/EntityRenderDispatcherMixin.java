package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityRenderers;
import com.google.common.collect.ImmutableMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.Entity;

@Mixin(EntityRenderDispatcher.class)
@SuppressWarnings({"unchecked"})
public abstract class EntityRenderDispatcherMixin
{
	@Unique
	private Map<PlayerSkin.Model, EntityRenderer<? extends PlayerIllusionEntity/*? >=1.21.3 {*/, ?/*?}*/>> friendsandfoes$illusionModelRenderers = ImmutableMap.of();

	@Inject(
		method = "getRenderer",
		at = @At("HEAD"),
		cancellable = true
	)
	public <T extends Entity> void friendsandfoes$getRenderer(
		T entity,
		CallbackInfoReturnable<EntityRenderer<? super T/*? >=1.21.3 {*/, ?/*?}*/>> cir
	) {
		if (entity instanceof PlayerIllusionEntity) {
			PlayerSkin.Model model = ((PlayerIllusionEntity) entity).getSkinTextures().model();
			EntityRenderer<? extends PlayerIllusionEntity/*? >=1.21.3 {*/, ?/*?}*/> entityRenderer = this.friendsandfoes$illusionModelRenderers.get(model);
			entityRenderer = entityRenderer != null ? entityRenderer:this.friendsandfoes$illusionModelRenderers.get(PlayerSkin.Model.WIDE);
			cir.setReturnValue((EntityRenderer<? super T/*? >=1.21.3 {*/, ?/*?}*/>) entityRenderer);
		}
	}

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