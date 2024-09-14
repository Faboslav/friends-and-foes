package com.faboslav.friendsandfoes.common.events.lifecycle;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record RegisterReloadListenerEvent(BiConsumer<Identifier, ResourceReloader> registrar)
{

	public static final EventHandler<RegisterReloadListenerEvent> EVENT = new EventHandler<>();

	public void register(Identifier id, ResourceReloader listener) {
		registrar.accept(id, listener);
	}
}
