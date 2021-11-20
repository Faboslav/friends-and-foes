package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.util.OxiditableHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Oxidizable.class)
public interface OxidizableMixin
{
    @Inject(method = "getDecreasedOxidationBlock", at = @At("HEAD"), cancellable = true)
    private static void oxidized$customDecreasedOxidationBlocks(
            Block block,
            CallbackInfoReturnable<Optional<Block>> cir
    ) {
        Optional<Block> optionalBlock = Optional.ofNullable(OxiditableHelper.OXIDATION_LEVEL_DECREASES.get(block));

        if (optionalBlock.isPresent()) {
            cir.setReturnValue(optionalBlock);
        }
    }

    @Inject(method = "getIncreasedOxidationBlock", at = @At("HEAD"), cancellable = true)
    private static void oxidized$customIncreasedOxidationBlocks(
            Block block,
            CallbackInfoReturnable<Optional<Block>> cir
    ) {
        Optional<Block> optionalBlock = Optional.ofNullable(OxiditableHelper.OXIDATION_LEVEL_INCREASES.get(block));

        if (optionalBlock.isPresent()) {
            cir.setReturnValue(optionalBlock);
        }
    }
}
