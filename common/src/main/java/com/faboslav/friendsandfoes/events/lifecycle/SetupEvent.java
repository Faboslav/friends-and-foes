package com.faboslav.friendsandfoes.events.lifecycle;

import com.faboslav.friendsandfoes.events.base.EventHandler;

import java.util.function.Consumer;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 *
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record SetupEvent(Consumer<Runnable> enqueue)
{

	public static final EventHandler<SetupEvent> EVENT = new EventHandler<>();

	public void enqueueWork(Runnable runnable) {
		enqueue.accept(runnable);
	}
}
