package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.effect.LongReachStatusEffect;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Inspired by Reach Entity Attributes library
 *
 * @author JamiesWhiteShirt
 * <a href="https://github.com/JamiesWhiteShirt/reach-entity-attributes">https://github.com/JamiesWhiteShirt/reach-entity-attributes</a>
 */
@Mixin(ServerPlayerInteractionManager.class)
abstract class ServerPlayerInteractionManagerMixin
{
	@Shadow
	@Final
	protected ServerPlayerEntity player;

	@Redirect(
		method = "processBlockBreakingAction",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;MAX_BREAK_SQUARED_DISTANCE:D", opcode = Opcodes.GETSTATIC
		)
	)
	private double getActualReachDistance() {
		return LongReachStatusEffect.getModifiedSquaredReachDistance(this.player, ServerPlayNetworkHandler.MAX_BREAK_SQUARED_DISTANCE);
	}
}
