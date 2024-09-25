package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.block.Oxidizable;
import net.minecraft.block.Degradable;
import net.minecraft.block.LightningRodBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LightningRodBlock.class, priority = 10000)
public abstract class LightningRodBlockDegradableMixin implements Degradable
{
	@Override
	public Enum<Oxidizable.OxidationLevel> getDegradationLevel() {
		return Oxidizable.OxidationLevel.UNAFFECTED;
	}
}
