package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = Block.class, priority = 10000)
public abstract class LightningRodBlockBlockMixin extends LightningRodAbstractBlockMixin
{
}
