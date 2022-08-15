package com.faboslav.friendsandfoes.mixin.forge;

import net.minecraft.block.Block;
import net.minecraft.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FireBlock.class)
public interface FireBlockAccessor
{
	@Invoker("registerFlammableBlock")
	void invokeRegisterFlammableBlock(Block block, int burnChance, int spreadChance);
}