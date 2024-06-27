package com.faboslav.friendsandfoes.events.lifecycle;

import com.faboslav.friendsandfoes.events.base.EventHandler;
import net.minecraft.block.Block;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record RegisterOxidizablesEvent(Registrar registrar)
{
	public static final EventHandler<RegisterOxidizablesEvent> EVENT = new EventHandler<>();

	public void register(Block before, Block after) {
		registrar.register(before, after);
	}

	@FunctionalInterface
	public interface Registrar
	{
		void register(Block before, Block after);
	}
}
