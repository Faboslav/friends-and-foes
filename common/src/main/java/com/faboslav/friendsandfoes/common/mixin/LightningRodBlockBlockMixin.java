package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = Block.class, priority = 1001)
public abstract class LightningRodBlockBlockMixin extends LightningRodAbstractBlockMixin
{
}
