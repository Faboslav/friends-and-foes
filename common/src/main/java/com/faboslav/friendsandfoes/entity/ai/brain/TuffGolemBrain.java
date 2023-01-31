package com.faboslav.friendsandfoes.entity.ai.brain;

import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.entity.WildfireEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
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
		addIdleActivities(brain);
		addAvoidActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();

		return brain;
	}

	private static void addCoreActivities(Brain<TuffGolemEntity> brain) {
		brain.setTaskList(Activity.CORE,
			0,
			ImmutableList.of(
				new LookAroundTask(45, 90),
				new WanderAroundTask(),
				new TemptationCooldownTask(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get())
			));
	}

	private static void addIdleActivities(Brain<TuffGolemEntity> brain) {
		brain.setTaskList(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, makeRandomWanderTask())
			)
		);
	}

	private static void addAvoidActivities(Brain<TuffGolemEntity> brain) {
		brain.setTaskList(
			Activity.AVOID,
			10,
			ImmutableList.of(
				GoToRememberedPositionTask.toEntity(MemoryModuleType.AVOID_TARGET, 1.4F, 16, true),
				makeRandomWanderTask()
			),
			MemoryModuleType.AVOID_TARGET
		);
	}

	private static RandomTask<TuffGolemEntity> makeRandomWanderTask() {
		return new RandomTask(
			ImmutableList.of(
				Pair.of(new StrollTask(0.6F), 2),
				Pair.of(new GoTowardsLookTarget(1.0F, 3), 2),
				Pair.of(new WaitTask(30, 60), 1)
			)
		);
	}

	public static void updateActivities(TuffGolemEntity tuffGolem) {
		tuffGolem.getBrain().resetPossibleActivities(ImmutableList.of(
			Activity.FIGHT,
			Activity.AVOID,
			Activity.IDLE
		));
	}

	public static void setSleepCooldown(TuffGolemEntity tuffGolem) {
		tuffGolem.getBrain().remember(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), SLEEP_COOLDOWN_PROVIDER.get(tuffGolem.getRandom()));
	}

	static {
		SENSORS = List.of(
			SensorType.NEAREST_PLAYERS,
			SensorType.HURT_BY
		);
		MEMORY_MODULES = List.of(
			MemoryModuleType.PATH,
			MemoryModuleType.MOBS,
			MemoryModuleType.VISIBLE_MOBS,
			MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
			MemoryModuleType.ATTACK_TARGET,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.AVOID_TARGET,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE
		);
		SLEEP_COOLDOWN_PROVIDER = UniformIntProvider.create(2400, 6000);
	}
}
