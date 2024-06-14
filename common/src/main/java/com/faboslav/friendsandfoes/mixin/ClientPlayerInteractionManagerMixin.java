package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.entity.effect.LongReachStatusEffect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
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
@Mixin(ClientPlayerInteractionManager.class)
abstract class ClientPlayerInteractionManagerMixin
{
	@Shadow
	@Final
	private MinecraftClient client;

	@ModifyConstant(
		method = "getReachDistance()F",
		require = 2,
		allow = 2,
		constant = {@Constant(floatValue = 5.0F), @Constant(floatValue = 4.5F)}
	)
	private float getActualReachDistance(final float currentReachDistance) {
		return (float) LongReachStatusEffect.getModifiedReachDistance(this.client.player, currentReachDistance);
	}
}
