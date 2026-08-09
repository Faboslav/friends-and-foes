package com.faboslav.friendsandfoes.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.level.block.LightningRodBlock;

//? if <=1.21.8 {
import com.faboslav.friendsandfoes.common.block.FriendsAndFoesOxidizable;

import java.util.Optional;
import net.minecraft.world.level.block.RodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(value = LightningRodBlock.class, priority = 1002)
public abstract class LightningRodBlockOxidizableMixin extends RodBlock implements WeatheringCopper
{
	public LightningRodBlockOxidizableMixin(Properties settings) {
		super(settings);
	}

	@Override
	public Optional<BlockState> getNext(BlockState state) {
		return FriendsAndFoesOxidizable.getNext(state.getBlock()).map((block) -> block.withPropertiesOf(state));
	}
}
//?} else {
/*@Mixin(LightningRodBlock.class)
public abstract class LightningRodBlockOxidizableMixin
{
}
*///?}