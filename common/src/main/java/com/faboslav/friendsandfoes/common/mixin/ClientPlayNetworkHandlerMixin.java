package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.util.UpdateChecker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPacketListener.class)
public abstract class ClientPlayNetworkHandlerMixin
{
	@Inject(method = "handleLogin", at = @At("TAIL"))
	private void friendsandfoes_showUpdateMessage(ClientboundLoginPacket arg, CallbackInfo ci) {
		Minecraft client = ((ClientCommonNetworkHandlerAccessor) this).getClient();
		if (client.player == null) {
			return;
		}

		UpdateChecker.checkForNewUpdatesInGame(client.player);
	}
}