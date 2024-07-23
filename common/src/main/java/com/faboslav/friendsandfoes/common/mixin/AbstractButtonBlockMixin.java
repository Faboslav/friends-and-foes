package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.block.*;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Oxidizable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractButtonBlock.class)
public final class AbstractButtonBlockMixin
{
	@Inject(method = "getPressTicks", at = @At("HEAD"), cancellable = true)
	public void friendsandfoes_getCopperPressTicks(
		CallbackInfoReturnable<Integer> callbackInfo
	) {
		Object buttonBlock = this;

		if (buttonBlock instanceof CopperButtonBlock) {
			int pressTicks = CopperButtonBlock.PRESS_TICKS;

			if (buttonBlock instanceof OxidizableButtonBlock oxidizableButtonBlock) {
				Oxidizable.OxidationLevel OxidationLevel = oxidizableButtonBlock.getDegradationLevel();

				if (OxidationLevel == Oxidizable.OxidationLevel.EXPOSED) {
					pressTicks = ExposedCopperButtonBlock.PRESS_TICKS;
				} else if (OxidationLevel == Oxidizable.OxidationLevel.WEATHERED) {
					pressTicks = WeatheredCopperButtonBlock.PRESS_TICKS;
				} else if (OxidationLevel == Oxidizable.OxidationLevel.OXIDIZED) {
					pressTicks = OxidizedCopperButtonBlock.PRESS_TICKS;
				}
			} else {
				if (buttonBlock instanceof ExposedCopperButtonBlock) {
					pressTicks = ExposedCopperButtonBlock.PRESS_TICKS;
				} else if (buttonBlock instanceof WeatheredCopperButtonBlock) {
					pressTicks = WeatheredCopperButtonBlock.PRESS_TICKS;
				} else if (buttonBlock instanceof OxidizableButtonBlock) {
					pressTicks = OxidizedCopperButtonBlock.PRESS_TICKS;
				}
			}

			callbackInfo.setReturnValue(pressTicks);
		}
	}
}
