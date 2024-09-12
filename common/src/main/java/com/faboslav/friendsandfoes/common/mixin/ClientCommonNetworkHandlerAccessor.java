package com.faboslav.friendsandfoes.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(ClientCommonNetworkHandler.class)
public interface ClientCommonNetworkHandlerAccessor
{
	@Accessor
	MinecraftClient getClient();
}
