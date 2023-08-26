package com.faboslav.friendsandfoes.entity.ai.brain;

import com.faboslav.friendsandfoes.entity.RascalEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;

import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class RascalBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super RascalEntity>>> SENSORS;

	public RascalBrain() {
	}

	public static Brain<?> create(Dynamic<?> dynamic) {
		Brain.Profile<RascalEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
		Brain<RascalEntity> brain = profile.deserialize(dynamic);

		addCoreActivities(brain);
		addHomeActivities(brain);
		addIdleActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();

		return brain;
	}

	private static void addCoreActivities(Brain<RascalEntity> brain) {
	}

	private static void addHomeActivities(Brain<RascalEntity> brain) {

	}

	private static void addIdleActivities(Brain<RascalEntity> brain) {
	}

	public static void updateActivities(RascalEntity rascal) {
		rascal.getBrain().resetPossibleActivities(
			ImmutableList.of(
				FriendsAndFoesActivities.TUFF_GOLEM_HOME.get(),
				Activity.IDLE
			)
		);
	}

	static {
		SENSORS = List.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS
		);
		MEMORY_MODULES = List.of(
			MemoryModuleType.VISIBLE_MOBS,
			MemoryModuleType.PATH,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get()
		);
	}
}
