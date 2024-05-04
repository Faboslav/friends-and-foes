package com.faboslav.friendsandfoes.entity.ai.brain;

import com.faboslav.friendsandfoes.entity.CrabEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;

public final class CrabBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super CrabEntity>>> SENSORS;
	private static final UniformIntProvider WAVE_COOLDOWN_PROVIDER;

	public static Brain<?> create(Dynamic<?> dynamic) {
		Brain.Profile<CrabEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
		Brain<CrabEntity> brain = profile.deserialize(dynamic);

		addCoreActivities(brain);
		addIdleActivities(brain);
		//addWaveActivities(brain);

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
				new StayAboveWaterTask(0.8f),
				new WalkTask(2.0f),
				new LookAroundTask(45, 90),
				new WanderAroundTask(),
				new TemptationCooldownTask(FriendsAndFoesMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get())
			)
		);
	}

	private static void addWaveActivities(Brain<CrabEntity> brain) {
		brain.setTaskList(
			FriendsAndFoesActivities.CRAB_WAVE.get(),
			ImmutableList.of(

			),
			ImmutableSet.of(
				Pair.of(FriendsAndFoesMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT)
			)
		);
	}

	private static void addIdleActivities(Brain<CrabEntity> brain) {
		brain.setTaskList(
			Activity.IDLE,
			ImmutableList.of(
				//Pair.of(0, new TimeLimitedTask<LivingEntity>(new FollowMobTask(EntityType.PLAYER, 6.0f), UniformIntProvider.create(30, 60))),
				Pair.of(0, new RandomTask(
					ImmutableList.of(
						Pair.of(new StrollTask(1.0f), 2),
						Pair.of(new GoTowardsLookTarget(1.0f, 3), 2),
						Pair.of(new WaitTask(30, 60), 1)))
				)
			),
			ImmutableSet.of(
				//Pair.of(FriendsAndFoesMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get(), MemoryModuleState.VALUE_PRESENT)
			));
	}

	public static void updateActivities(CrabEntity crab) {
		crab.getBrain().resetPossibleActivities(
			ImmutableList.of(
				//FriendsAndFoesActivities.CRAB_WAVE.get(),
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
			MemoryModuleType.ATE_RECENTLY,
			MemoryModuleType.BREED_TARGET,
			MemoryModuleType.AVOID_TARGET,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
			FriendsAndFoesMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get()
		);
		WAVE_COOLDOWN_PROVIDER = TimeHelper.betweenSeconds(20, 40);
	}
}

