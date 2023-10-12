package com.faboslav.friendsandfoes.entity.ai.brain;

import com.faboslav.friendsandfoes.entity.RascalEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.task.rascal.RascalFindInteractionTargetTask;
import com.faboslav.friendsandfoes.entity.ai.brain.task.rascal.RascalWaitForPlayerTask;
import com.faboslav.friendsandfoes.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class RascalBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super RascalEntity>>> SENSORS;
	public final static int NOD_COOLDOWN = 10;
	private static final UniformIntProvider NOD_COOLDOWN_PROVIDER;
	private static final UniformIntProvider AVOID_MEMORY_DURATION;

	public RascalBrain() {
	}

	public static Brain<?> create(Dynamic<?> dynamic) {
		Brain.Profile<RascalEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
		Brain<RascalEntity> brain = profile.deserialize(dynamic);

		addCoreActivities(brain);
		addIdleActivities(brain);
		addWaitActivities(brain);
		addAvoidActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();

		return brain;
	}

	private static void addCoreActivities(Brain<RascalEntity> brain) {
		brain.setTaskList(Activity.CORE,
			0,
			ImmutableList.of(
				new StayAboveWaterTask(0.8F),
				new LookAroundTask(45, 90),
				new WanderAroundTask(),
				new TemptationCooldownTask(FriendsAndFoesMemoryModuleTypes.RASCAL_NOD_COOLDOWN.get())
			)
		);
	}

	private static void addIdleActivities(Brain<RascalEntity> brain) {
		brain.setTaskList(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, RascalFindInteractionTargetTask.create(6)),
				Pair.of(0, makeRandomWanderTask())
			)
		);
	}

	private static void addWaitActivities(
		Brain<RascalEntity> brain
	) {
		brain.setTaskList(
			FriendsAndFoesActivities.RASCAL_WAIT.get(),
			10,
			ImmutableList.of(
				new RascalWaitForPlayerTask()
			), MemoryModuleType.INTERACTION_TARGET
		);
	}

	private static void addAvoidActivities(Brain<RascalEntity> brain) {
		brain.setTaskList(
			Activity.AVOID,
			10,
			ImmutableList.of(
				GoToRememberedPositionTask.createEntityBased(MemoryModuleType.AVOID_TARGET, 1.0F, 32, true)
			),
			MemoryModuleType.AVOID_TARGET
		);
	}

	public static void updateActivities(RascalEntity rascal) {
		rascal.getBrain().resetPossibleActivities(
			ImmutableList.of(
				FriendsAndFoesActivities.RASCAL_WAIT.get(),
				Activity.AVOID,
				Activity.IDLE
			)
		);
	}

	private static RandomTask<RascalEntity> makeRandomWanderTask() {
		return new RandomTask(
			ImmutableList.of(
				Pair.of(StrollTask.create(0.6F), 2),
				Pair.of(new WaitTask(30, 60), 1)
			)
		);
	}

	public static void setNodCooldown(RascalEntity rascal) {
		rascal.getBrain().remember(FriendsAndFoesMemoryModuleTypes.RASCAL_NOD_COOLDOWN.get(), NOD_COOLDOWN_PROVIDER.get(rascal.getRandom()));
		onCooldown(rascal);
	}

	public static boolean shouldRunAway(RascalEntity rascal) {
		return rascal.getBrain().getOptionalRegisteredMemory(FriendsAndFoesMemoryModuleTypes.RASCAL_NOD_COOLDOWN.get()).isPresent();
	}

	public static void onCooldown(RascalEntity rascal) {
		if (shouldRunAway(rascal) == false) {
			return;
		}

		if (rascal.hasCustomName()) {
			return;
		}

		LivingEntity nearestTarget = rascal.getBrain().getOptionalRegisteredMemory(MemoryModuleType.INTERACTION_TARGET).orElse(null);

		if (nearestTarget == null) {
			return;
		}

		runAwayFrom(rascal, nearestTarget);
	}

	private static void runAwayFrom(RascalEntity rascal, LivingEntity target) {
		rascal.getBrain().forget(MemoryModuleType.LOOK_TARGET);
		rascal.getBrain().forget(MemoryModuleType.WALK_TARGET);
		rascal.getBrain().forget(MemoryModuleType.INTERACTION_TARGET);
		rascal.getBrain().remember(MemoryModuleType.AVOID_TARGET, target, AVOID_MEMORY_DURATION.get(rascal.getRandom()));
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
			MemoryModuleType.AVOID_TARGET,
			MemoryModuleType.INTERACTION_TARGET,
			MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
			MemoryModuleType.NEAREST_PLAYERS,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			FriendsAndFoesMemoryModuleTypes.RASCAL_NOD_COOLDOWN.get()
		);
		NOD_COOLDOWN_PROVIDER = TimeHelper.betweenSeconds(NOD_COOLDOWN, NOD_COOLDOWN);
		AVOID_MEMORY_DURATION = TimeHelper.betweenSeconds(NOD_COOLDOWN, NOD_COOLDOWN);
	}
}