package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.block.LightningRodBlock;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.RodBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LightningRodBlock.class, priority = 10000)
public abstract class LightningRodBlockOxidizableMixin extends RodBlock implements Oxidizable
{
	public LightningRodBlockOxidizableMixin(Settings settings) {
		super(settings);
	}
}