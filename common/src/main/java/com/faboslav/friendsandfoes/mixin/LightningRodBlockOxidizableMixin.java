package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.block.Oxidizable;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.block.RodBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LightningRodBlock.class)
public abstract class LightningRodBlockOxidizableMixin extends RodBlock implements Oxidizable
{
	public LightningRodBlockOxidizableMixin(Settings settings) {
		super(settings);
	}
}