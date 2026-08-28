//? if <1.21.1 {
/*package com.faboslav.friendsandfoes.common.events.lifecycle;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import java.util.function.Consumer;

public record RegisterBlockSetTypeEvent(Consumer<BlockSetType> registrar)
{
	public static final EventHandler<RegisterBlockSetTypeEvent> EVENT = new EventHandler<>();

	public void register(BlockSetType blockSetType) {
		registrar.accept(blockSetType);
	}
}
*///?}
