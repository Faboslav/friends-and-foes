package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LightningRodBlock.class, priority = 1003)
public abstract class LightningRodBlockDegradableMixin implements ChangeOverTimeBlock
{
	@Override
	public Enum<WeatheringCopper.WeatherState> getAge() {
		return WeatheringCopper.WeatherState.UNAFFECTED;
	}
}
