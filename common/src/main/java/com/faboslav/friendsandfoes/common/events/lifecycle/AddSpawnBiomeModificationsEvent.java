package com.faboslav.friendsandfoes.common.events.lifecycle;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record AddSpawnBiomeModificationsEvent(Registrar registrar)
{
	public static final EventHandler<AddSpawnBiomeModificationsEvent> EVENT = new EventHandler<>();

	public void add(
		TagKey<Biome> tag,
		MobCategory spawnGroup,
		EntityType<?> entityType,
		int weight,
		int minGroupSize,
		int maxGroupSize
	) {
		registrar.add(tag, spawnGroup, entityType, weight, minGroupSize, maxGroupSize);
	}

	@FunctionalInterface
	public interface Registrar
	{
		void add(
			TagKey<Biome> tag,
			MobCategory spawnGroup,
			EntityType<?> entityType,
			int weight,
			int minGroupSize,
			int maxGroupSize
		);
	}
}
