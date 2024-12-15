package com.faboslav.friendsandfoes.common.events.lifecycle;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

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

	public <T extends Mob> void register(
		EntityType<T> entityType,
		SpawnPlacementType location,
		Heightmap.Types heightmap,
		SpawnPlacements.SpawnPredicate<T> predicate
	) {
		registrar.register(entityType, new Placement<>(predicate, location, heightmap));
	}

	public record Placement<T extends Mob>(
		SpawnPlacements.SpawnPredicate<T> predicate,
		SpawnPlacementType location,
		Heightmap.Types heightmap
	)
	{
	}

	@FunctionalInterface
	public interface Registrar
	{
		<T extends Mob> void register(EntityType<T> type, Placement<T> placement);
	}
}
