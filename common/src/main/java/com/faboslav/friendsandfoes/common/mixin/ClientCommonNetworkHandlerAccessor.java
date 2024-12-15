package com.faboslav.friendsandfoes.common.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(ClientCommonPacketListenerImpl.class)
public interface ClientCommonNetworkHandlerAccessor
{
	@Accessor("minecraft")
	Minecraft getClient();
}
