package com.faboslav.friendsandfoes.common.events.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public class EventHandler<T>
{

	private final List<Consumer<T>> listeners = new ArrayList<>();

	public EventHandler() {

	}

	public void addListener(Consumer<T> listener) {
		listeners.add(listener);
	}

	public void removeListener(Consumer<T> listener) {
		listeners.remove(listener);
	}

	public void invoke(T event) {
		for (Consumer<T> listener : listeners) {
			listener.accept(event);
		}
	}

}
