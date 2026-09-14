//? if < 1.21.5 {
/*package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.gametest.framework.GameTestRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GameTestRegistry.class)
public class GameTestRegistryMixin
{
	@ModifyArg(
		method = "turnMethodIntoTestFunction",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/gametest/framework/TestFunction;<init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/world/level/block/Rotation;IJZZIIZLjava/util/function/Consumer;)V"),
		index = 2
	)
	private static String turnMethodIntoTestFunctionHook(String string) {
		if (string.contains("clientgametest")) {
			return "clientgametest:empty";
		}

		return string;
	}
}
*///?}