package com.faboslav.friendsandfoes.neoforge.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FireBlock.class)
public interface FireBlockAccessor
{
	@Invoker("setFlammable")
	void invokeRegisterFlammableBlock(Block block, int burnChance, int spreadChance);
}