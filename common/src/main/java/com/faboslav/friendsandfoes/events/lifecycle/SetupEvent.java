package com.faboslav.friendsandfoes.events.lifecycle;

import com.faboslav.friendsandfoes.events.base.EventHandler;

import java.util.function.Consumer;

public record SetupEvent(Consumer<Runnable> enqueue)
{

	public static final EventHandler<SetupEvent> EVENT = new EventHandler<>();

	public void enqueueWork(Runnable runnable) {
		enqueue.accept(runnable);
	}
}
