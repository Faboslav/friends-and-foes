package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.client.render.entity.renderer.PlayerIllusionEntityRenderer;
import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin
{
	private static final Map<String, EntityRendererFactory<PlayerIllusionEntity>> PLAYER_ILLUSION_RENDERER_FACTORIES = ImmutableMap.of("default", context -> new PlayerIllusionEntityRenderer(context, false), "slim", context -> new PlayerIllusionEntityRenderer(context, true));

	@Unique
	private Map<String, EntityRenderer<? extends PlayerIllusionEntity>> illusionModelRenderers = ImmutableMap.of();

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
			String string = ((PlayerIllusionEntity) entity).getModel();
			EntityRenderer<? extends PlayerIllusionEntity> entityRenderer = this.illusionModelRenderers.get(string);
			entityRenderer = entityRenderer != null ? entityRenderer:this.illusionModelRenderers.get("default");
			cir.setReturnValue((EntityRenderer<? super T>) entityRenderer);
		}
	}

	@ModifyVariable(
		method = "reload",
		ordinal = 0,
		at = @At(
			value = "LOAD"
		)
	)
	public EntityRendererFactory.Context friendsandfoes_reload(
		EntityRendererFactory.Context context
	) {
		this.illusionModelRenderers = EntityRenderDispatcherMixin.friendsandfoes_reloadPlayerIllusionRenderers(context);
		return context;
	}

	private static Map<String, EntityRenderer<? extends PlayerIllusionEntity>> friendsandfoes_reloadPlayerIllusionRenderers(
		EntityRendererFactory.Context ctx
	) {
		ImmutableMap.Builder builder = ImmutableMap.builder();

		PLAYER_ILLUSION_RENDERER_FACTORIES.forEach((type, factory) -> {
			try {
				builder.put(type, factory.create(ctx));
			} catch (Exception exception) {
				throw new IllegalArgumentException("Failed to create player illusion model for " + type, exception);
			}
		});

		return builder.build();
	}
}
