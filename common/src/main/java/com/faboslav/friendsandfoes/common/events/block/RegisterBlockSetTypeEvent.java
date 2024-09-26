package com.faboslav.friendsandfoes.common.events.block;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.block.BlockSetType;

import java.util.function.Consumer;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record RegisterBlockSetTypeEvent(Consumer<BlockSetType> registrar)
{
	public static final EventHandler<RegisterBlockSetTypeEvent> EVENT = new EventHandler<>();

	public void register(BlockSetType blockSetType) {
		registrar.accept(blockSetType);
	}
}