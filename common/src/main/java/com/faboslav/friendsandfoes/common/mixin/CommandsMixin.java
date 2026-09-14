package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.commands.Commands;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Commands.class)
public class CommandsMixin {
	@Redirect(
		method = "performCommand",
		at = @At(
			value = "INVOKE", target = "Lorg/slf4j/Logger;isDebugEnabled()Z",
			remap = false
		),
		require = 0
	)
	private boolean performCommandHook(Logger instance) {
		return true;
	}
}