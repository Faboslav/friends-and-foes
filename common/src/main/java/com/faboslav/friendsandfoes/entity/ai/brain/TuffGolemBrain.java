package com.faboslav.friendsandfoes.entity.ai.brain;

import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.task.tuffgolem.*;
import com.faboslav.friendsandfoes.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class TuffGolemBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super TuffGolemEntity>>> SENSORS;
	private static final UniformIntProvider SLEEP_COOLDOWN_PROVIDER;

	public TuffGolemBrain() {
	}

	public static Brain<?> create(Dynamic<?> dynamic) {
		Brain.Profile<TuffGolemEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
		Brain<TuffGolemEntity> brain = profile.deserialize(dynamic);

		addCoreActivities(brain);
		addHomeActivities(brain);
		addIdleActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();

		return brain;
	}

	private static void addCoreActivities(Brain<TuffGolemEntity> brain) {
		brain.setTaskList(Activity.CORE,
			0,
			ImmutableList.of(
				new TuffGolemLookAroundTask(45, 90),
				new TuffGolemWanderAroundTask(),
				new TemptationCooldownTask(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get())
			));
	}

	private static void addHomeActivities(Brain<TuffGolemEntity> brain) {
		brain.setTaskList(
			FriendsAndFoesActivities.TUFF_GOLEM_HOME.get(),
			ImmutableList.of(
				Pair.of(0, new TuffGolemGoToHomePositionTask()),
				Pair.of(1, new TuffGolemSleepTask())
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_ABSENT),
				Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT)
			)
		);
	}

	private static void addIdleActivities(Brain<TuffGolemEntity> brain) {
		brain.setTaskList(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0,
					new RandomTask(
						ImmutableList.of(
							Pair.of(FollowMobWithIntervalTask.follow(EntityType.PLAYER, 6.0F, UniformIntProvider.create(30, 60)), 3),
							Pair.of(FollowMobWithIntervalTask.follow(FriendsAndFoesEntityTypes.COPPER_GOLEM.get(), 6.0F, UniformIntProvider.create(30, 60)), 2),
							Pair.of(FollowMobWithIntervalTask.follow(FriendsAndFoesEntityTypes.TUFF_GOLEM.get(), 6.0F, UniformIntProvider.create(30, 60)), 2),
							Pair.of(FollowMobWithIntervalTask.follow(EntityType.IRON_GOLEM, 6.0F, UniformIntProvider.create(30, 60)), 1)
						)
					)
				),
				Pair.of(1,
					new RandomTask(
						ImmutableMap.of(
							MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT
						),
						ImmutableList.of(
							Pair.of(TaskTriggerer.runIf(TuffGolemBrain::isNotImmobilized), new TuffGolemWaitTask(60, 80), 2),
							Pair.of(TaskTriggerer.runIf(TuffGolemBrain::isNotImmobilized), TuffGolemStrollTask.create(0.6F), 1),
							Pair.of(TaskTriggerer.runIf(TuffGolemBrain::isNotImmobilized), GoTowardsLookTargetTask.create(0.6F, 2), 1)
						)
					)
				)
			),
			ImmutableSet.of(
				Pair.of(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), MemoryModuleState.VALUE_PRESENT)
			)
		);
	}

	public static void updateActivities(TuffGolemEntity tuffGolem) {
		tuffGolem.getBrain().resetPossibleActivities(
			ImmutableList.of(
				FriendsAndFoesActivities.TUFF_GOLEM_HOME.get(),
				Activity.IDLE
			)
		);
	}

	public static void resetSleepCooldown(TuffGolemEntity tuffGolem) {
		tuffGolem.getBrain().forget(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get());
	}

	public static void setSleepCooldown(TuffGolemEntity tuffGolem) {
		tuffGolem.getBrain().remember(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), SLEEP_COOLDOWN_PROVIDER.get(tuffGolem.getRandom()));
	}

	private static boolean isNotImmobilized(TuffGolemEntity tuffGolem) {
		return tuffGolem.isNotImmobilized();
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
		SLEEP_COOLDOWN_PROVIDER = UniformIntProvider.create(2400, 7200);
	}
}
