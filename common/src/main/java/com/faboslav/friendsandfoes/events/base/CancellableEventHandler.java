package com.faboslav.friendsandfoes.events.base;

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
public final class CancellableEventHandler<T>
{
	private final List<CancellableFunction<T>> listeners = new ArrayList<>();

	public CancellableEventHandler() {

	}

	public void addListener(CancellableFunction<T> listener) {
		listeners.add(listener);
	}

	public void addListener(CancellableFunctionNoReturn<T> listener) {
		addListener((b, t) -> {
			listener.apply(b, t);
			return false;
		});
	}

	public void addListener(CancellableFunctionOnlyReturn<T> listener) {
		addListener((b, t) -> {
			return listener.apply(t);
		});
	}

	public void addListener(Consumer<T> listener) {
		addListener((t) -> {
			listener.accept(t);
			return false;
		});
	}

	public void removeListener(CancellableFunction<T> listener) {
		listeners.remove(listener);
	}

	public boolean invoke(T event, boolean isCancelled) {
		for (CancellableFunction<T> listener : listeners) {
			if (listener.apply(isCancelled, event)) {
				isCancelled = true;
			}
		}
		return isCancelled;
	}

	public boolean invoke(T event) {
		return invoke(event, false);
	}

	@FunctionalInterface
	public interface CancellableFunction<T>
	{

		boolean apply(boolean cancelled, T event);
	}

	@FunctionalInterface
	public interface CancellableFunctionNoReturn<T>
	{

		void apply(boolean cancelled, T event);
	}

	@FunctionalInterface
	public interface CancellableFunctionOnlyReturn<T>
	{

		boolean apply(T event);
	}
}
