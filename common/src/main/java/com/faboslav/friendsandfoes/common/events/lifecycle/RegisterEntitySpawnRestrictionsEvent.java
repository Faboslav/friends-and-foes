package com.faboslav.friendsandfoes.common.events.lifecycle;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record RegisterEntitySpawnRestrictionsEvent(Registrar registrar)
{
	public static final EventHandler<RegisterEntitySpawnRestrictionsEvent> EVENT = new EventHandler<>();

	public <T extends MobEntity> void register(
		EntityType<T> entityType,
		SpawnRestriction.Location location,
		Heightmap.Type heightmap,
		SpawnRestriction.SpawnPredicate<T> predicate
	) {
		registrar.register(entityType, new Placement<>(predicate, location, heightmap));
	}

	public record Placement<T extends MobEntity>(
		SpawnRestriction.SpawnPredicate<T> predicate,
		SpawnRestriction.Location location,
		Heightmap.Type heightmap
	)
	{
	}

	@FunctionalInterface
	public interface Registrar
	{
		<T extends MobEntity> void register(EntityType<T> type, Placement<T> placement);
	}
}
