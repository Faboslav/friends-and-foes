package com.faboslav.friendsandfoes.common.events.lifecycle;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import java.util.function.BiConsumer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record RegisterEntityAttributesEvent(
	BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> attributes)
{
	public static final EventHandler<RegisterEntityAttributesEvent> EVENT = new EventHandler<>();

	public void register(EntityType<? extends LivingEntity> entityType, AttributeSupplier.Builder builder) {
		attributes.accept(entityType, builder);
	}
}
