package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(value = Block.class, priority = 1001)
public abstract class LightningRodBlockBlockMixin extends LightningRodAbstractBlockMixin
{
}
