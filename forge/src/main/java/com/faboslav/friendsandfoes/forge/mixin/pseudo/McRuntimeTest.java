package com.faboslav.friendsandfoes.forge.mixin.pseudo;

import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LevelSummary.class, remap = false)
public class McRuntimeTest
{
	@Inject(method = "isExperimental", at = @At(value = "RETURN"), cancellable = true)
	public void friendsandfoes$isExperimental(CallbackInfoReturnable<Boolean> cir) {
		if (friendsandfoes$isClassAvailable("me.earth.mc_runtime_test.McRuntimeTest")) {
			cir.setReturnValue(false);
		}
	}

	private boolean friendsandfoes$isClassAvailable(String className) {
		String classPath = className.replace('.', '/') + ".class";
		return getClass().getClassLoader().getResource(classPath) != null;
	}
}
