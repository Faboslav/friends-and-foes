package com.faboslav.friendsandfoes.common.mixin;

import com.mojang.serialization.Lifecycle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.server.integrated.IntegratedServerLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(IntegratedServerLoader.class)
public abstract class IntegratedServerLoaderMixin
{
	@Inject(
		method = "tryLoad",
		at = @At("HEAD"),
		cancellable = true
	)
	private static void alwaysTreatAsStable(
		MinecraftClient client,
		CreateWorldScreen parent,
		Lifecycle lifecycle,
		Runnable loader,
		boolean bypassWarnings,
		CallbackInfo ci
	) {
		if (lifecycle != Lifecycle.stable()) {
			loader.run();
			ci.cancel();
		}
	}
}
