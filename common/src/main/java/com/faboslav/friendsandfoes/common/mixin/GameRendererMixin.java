package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.effect.LongReachStatusEffect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.resource.SynchronousResourceReloader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Inspired by Reach Entity Attributes library
 *
 * @author JamiesWhiteShirt
 * <a href="https://github.com/JamiesWhiteShirt/reach-entity-attributes">https://github.com/JamiesWhiteShirt/reach-entity-attributes</a>
 */
@Mixin(GameRenderer.class)
abstract class GameRendererMixin implements SynchronousResourceReloader
{
	@Shadow
	@Final
	private MinecraftClient client;

	@ModifyConstant(
		method = "updateTargetedEntity(F)V",
		require = 1,
		allow = 1,
		constant = @Constant(doubleValue = 6.0)
	)
	private double getActualReachDistance(final double currentReachDistance) {
		return LongReachStatusEffect.getModifiedReachDistance(this.client.player, currentReachDistance);
	}
}
