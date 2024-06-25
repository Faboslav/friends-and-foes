package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.block.Oxidizable;
import net.minecraft.block.Degradable;
import net.minecraft.block.LightningRodBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LightningRodBlock.class)
public abstract class LightningRodBlockDegradableMixin implements Degradable
{
	@Override
	public Enum<Oxidizable.OxidationLevel> getDegradationLevel() {
		return Oxidizable.OxidationLevel.UNAFFECTED;
	}
}
