package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.JukeboxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(JukeboxBlockEntity.class)
public interface JukeboxBlockEntityAccessor
{
	@Invoker
	static boolean callIsPlayingRecord(BlockState state, JukeboxBlockEntity blockEntity) {
		throw new UnsupportedOperationException();
	}
}
