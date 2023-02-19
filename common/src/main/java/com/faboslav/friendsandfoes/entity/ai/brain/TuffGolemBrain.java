package com.faboslav.friendsandfoes.entity.ai.brain;

import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.task.TuffGolemGoToHomePositionTask;
import com.faboslav.friendsandfoes.entity.ai.brain.task.TuffGolemSleepTask;
import com.faboslav.friendsandfoes.entity.ai.brain.task.TuffGolemWalkTask;
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
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;

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
				new LookAroundTask(100, 200),
				new TuffGolemWalkTask(0.6F),
				new WanderAroundTask(),
				new TemptationCooldownTask(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get())
			));
	}

	private static void addHomeActivities(Brain<TuffGolemEntity> brain) {
		brain.setTaskList(
			FriendsAndFoesActivities.TUFF_GOLEM_HOME.get(),
			ImmutableList.of(
				Pair.of(0, new TuffGolemGoToHomePositionTask()),
				Pair.of(0, new TuffGolemSleepTask())

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
					new ConditionalTask(
						entity -> {
							TuffGolemEntity tuffGolem = (TuffGolemEntity) entity;
							return tuffGolem.isInStandingPose() && tuffGolem.isGlued() == false;
						},
						makeRandomWanderTask()
					)
				)
			),
			ImmutableSet.of(
				Pair.of(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), MemoryModuleState.VALUE_PRESENT)
			)
		);
	}

	private static RandomTask<TuffGolemEntity> makeRandomWanderTask() {
		return new RandomTask(
			ImmutableList.of(
				Pair.of(new StrollTask(0.6F), 2),
				Pair.of(new GoTowardsLookTarget(1.0F, 0), 2),
				Pair.of(new WaitTask(30, 60), 2)
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

	public static void setSleepCooldown(TuffGolemEntity tuffGolem) {
		tuffGolem.getBrain().remember(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), SLEEP_COOLDOWN_PROVIDER.get(tuffGolem.getRandom()));
	}

	static {
		SENSORS = List.of(
		);
		MEMORY_MODULES = List.of(
			MemoryModuleType.PATH,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get()
		);
		SLEEP_COOLDOWN_PROVIDER = UniformIntProvider.create(2400, 7200);
	}
}
