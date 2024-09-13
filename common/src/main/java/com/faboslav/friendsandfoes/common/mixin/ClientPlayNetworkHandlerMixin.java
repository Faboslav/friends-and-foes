package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.util.UpdateChecker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin
{
	@Inject(method = "onGameJoin", at = @At("TAIL"))
	private void friendsandfoes_showUpdateMessage(GameJoinS2CPacket arg, CallbackInfo ci) {
		MinecraftClient client = ((ClientCommonNetworkHandlerAccessor) this).getClient();
		if (client.player == null) {
			return;
		}

		UpdateChecker.checkForNewUpdatesInGame(client.player);
	}
}