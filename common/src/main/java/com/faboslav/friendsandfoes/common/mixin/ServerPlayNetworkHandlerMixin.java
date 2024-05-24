package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.effect.LongReachStatusEffect;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Inspired by Reach Entity Attributes library
 *
 * @author JamiesWhiteShirt
 * <a href="https://github.com/JamiesWhiteShirt/reach-entity-attributes">https://github.com/JamiesWhiteShirt/reach-entity-attributes</a>
 */
@Mixin(ServerPlayNetworkHandler.class)
abstract class ServerPlayNetworkHandlerMixin implements ServerPlayPacketListener
{
	@Shadow
	public ServerPlayerEntity player;

	@Redirect(
		method = "onPlayerInteractBlock(Lnet/minecraft/network/packet/c2s/play/PlayerInteractBlockC2SPacket;)V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;MAX_BREAK_SQUARED_DISTANCE:D",
			opcode = Opcodes.GETSTATIC
		)
	)
	private double getActualReachDistance() {
		return LongReachStatusEffect.getModifiedSquaredReachDistance(this.player, (float) ServerPlayNetworkHandler.MAX_BREAK_SQUARED_DISTANCE);
	}

	@ModifyConstant(
		method = "onPlayerInteractBlock(Lnet/minecraft/network/packet/c2s/play/PlayerInteractBlockC2SPacket;)V",
		require = 1,
		allow = 1,
		constant = @Constant(doubleValue = 64.0)
	)
	private double getActualReachDistance(final double currentReachDistance) {
		return LongReachStatusEffect.getModifiedSquaredReachDistance(this.player, currentReachDistance);
	}
}
