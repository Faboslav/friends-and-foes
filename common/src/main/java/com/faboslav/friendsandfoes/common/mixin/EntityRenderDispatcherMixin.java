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
public abstract class EntityRenderDispatcherMixin
{
	@Unique
	private Map<PlayerSkin.Model, EntityRenderer<? extends PlayerIllusionEntity>> illusionModelRenderers = ImmutableMap.of();

	@Inject(
		method = "getRenderer",
		at = @At("HEAD"),
		cancellable = true
	)
	public <T extends Entity> void friendsandfoes_getRenderer(
		T entity,
		CallbackInfoReturnable<EntityRenderer<? super T>> cir
	) {
		if (entity instanceof PlayerIllusionEntity) {
			PlayerSkin.Model model = ((PlayerIllusionEntity) entity).getSkinTextures().model();
			EntityRenderer<? extends PlayerIllusionEntity> entityRenderer = this.illusionModelRenderers.get(model);
			entityRenderer = entityRenderer != null ? entityRenderer:this.illusionModelRenderers.get(PlayerSkin.Model.WIDE);
			cir.setReturnValue((EntityRenderer<? super T>) entityRenderer);
		}
	}

	@ModifyVariable(
		method = "onResourceManagerReload",
		ordinal = 0,
		at = @At(
			value = "LOAD"
		)
	)
	public EntityRendererProvider.Context friendsandfoes_reload(
		EntityRendererProvider.Context context
	) {
		this.illusionModelRenderers = FriendsAndFoesEntityRenderers.reloadPlayerIllusionRenderers(context);
		return context;
	}
}