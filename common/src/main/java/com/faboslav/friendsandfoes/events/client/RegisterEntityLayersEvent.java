package com.faboslav.friendsandfoes.events.client;

import com.faboslav.friendsandfoes.events.base.EventHandler;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record RegisterEntityLayersEvent(BiConsumer<EntityModelLayer, Supplier<TexturedModelData>> registrar)
{
	public static final EventHandler<RegisterEntityLayersEvent> EVENT = new EventHandler<>();

	public void register(EntityModelLayer location, Supplier<TexturedModelData> definition) {
		registrar.accept(location, definition);
	}
}
