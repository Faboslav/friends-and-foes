package com.faboslav.friendsandfoes.events.lifecycle;

import com.faboslav.friendsandfoes.events.base.EventHandler;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public record RegisterReloadListenerEvent(BiConsumer<Identifier, ResourceReloader> registrar)
{

	public static final EventHandler<RegisterReloadListenerEvent> EVENT = new EventHandler<>();

	public void register(Identifier id, ResourceReloader listener) {
		registrar.accept(id, listener);
	}
}
