package com.faboslav.friendsandfoes.common.entity.ai.brain;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;

public final class PenguinBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super CrabEntity>>> SENSORS;

	public static Brain<?> create(Dynamic<?> dynamic) {
		Brain.Profile<CrabEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
		Brain<CrabEntity> brain = profile.deserialize(dynamic);

		addCoreActivities(brain);
		addIdleActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();

		return brain;
	}

	private static void addCoreActivities(Brain<CrabEntity> brain) {
		brain.setTaskList(
			Activity.CORE,
			0,
			ImmutableList.of(
				new WalkTask(2.0f),
				new LookAroundTask(45, 90),
				new WanderAroundTask(),
				new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
			)
		);
	}

	private static void addIdleActivities(Brain<CrabEntity> brain) {
		brain.setTaskList(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, new TemptTask(crab -> 1.25f)),
				Pair.of(1, new BreedTask(FriendsAndFoesEntityTypes.PENGUIN.get(), 1.0f)),
				Pair.of(2, new WalkTowardClosestAdultTask(UniformIntProvider.create(5, 16), 1.25f)),
				Pair.of(3, new RandomTask(
					ImmutableList.of(
						Pair.of(new NoPenaltyStrollTask(1.0f), 2),
						Pair.of(new GoTowardsLookTarget(1.0f, 3), 2),
						Pair.of(new WaitTask(30, 60), 1)))
				)
			),
			ImmutableSet.of(
			));
	}

	public static void updateActivities(PenguinEntity penguin) {
		penguin.getBrain().resetPossibleActivities(
			ImmutableList.of(
				Activity.IDLE
			)
		);
	}

	public static void updateMemories(PenguinEntity penguin) {
	}

	static {
		SENSORS = List.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS,
			SensorType.NEAREST_ADULT
		);
		MEMORY_MODULES = List.of(
			MemoryModuleType.TEMPTING_PLAYER,
			MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
			MemoryModuleType.IS_TEMPTED,
			MemoryModuleType.VISIBLE_MOBS,
			MemoryModuleType.PATH,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.BREED_TARGET,
			MemoryModuleType.IS_PANICKING,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			MemoryModuleType.NEAREST_VISIBLE_PLAYER,
			MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM
		);
	}
}

