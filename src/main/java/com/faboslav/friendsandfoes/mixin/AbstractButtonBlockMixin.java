package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.block.CopperButtonBlock;
import com.faboslav.friendsandfoes.block.OxidizableButtonBlock;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Oxidizable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractButtonBlock.class)
public class AbstractButtonBlockMixin
{
	@Inject(method = "getPressTicks", at = @At("HEAD"), cancellable = true)
	public void getCopperPressTicks(CallbackInfoReturnable<Integer> callbackInfoReturnable) {
		Object buttonBlock = this;

		if (buttonBlock instanceof CopperButtonBlock) {
			callbackInfoReturnable.setReturnValue(CopperButtonBlock.PRESS_TICKS);
		} else if (buttonBlock instanceof OxidizableButtonBlock) {
			OxidizableButtonBlock oxidizableButtonBlock = (OxidizableButtonBlock) buttonBlock;
			Oxidizable.OxidationLevel OxidationLevel = oxidizableButtonBlock.getDegradationLevel();

			int pressTicks = OxidizableButtonBlock.PRESS_TICKS;

			if (OxidationLevel == Oxidizable.OxidationLevel.EXPOSED) {
				pressTicks = OxidizableButtonBlock.EXPOSED_PRESS_TICKS;
			} else if (OxidationLevel == Oxidizable.OxidationLevel.WEATHERED) {
				pressTicks = OxidizableButtonBlock.WEATHERED_PRESS_TICKS;
			} else if (OxidationLevel == Oxidizable.OxidationLevel.OXIDIZED) {
				pressTicks = OxidizableButtonBlock.OXIDIZED_PRESS_TICKS;
			}

			callbackInfoReturnable.setReturnValue(pressTicks);
		}
	}
}
