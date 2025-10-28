package com.faboslav.friendsandfoes.common.entity.ai.brain;

import com.faboslav.friendsandfoes.common.entity.RascalEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.rascal.RascalFindInteractionTargetTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.rascal.RascalWaitForPlayerTask;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class RascalBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super RascalEntity>>> SENSORS;
	public final static int NOD_COOLDOWN = 10;
	private static final UniformInt NOD_COOLDOWN_PROVIDER;
	private static final UniformInt AVOID_MEMORY_DURATION;

	public RascalBrain() {
	}

	public static Brain<?> create(Dynamic<?> dynamic) {
		Brain.Provider<RascalEntity> profile = Brain.provider(MEMORY_MODULES, SENSORS);
		Brain<RascalEntity> brain = profile.makeBrain(dynamic);

		addCoreActivities(brain);
		addIdleActivities(brain);
		addWaitActivities(brain);
		addAvoidActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();

		return brain;
	}

	private static void addCoreActivities(Brain<RascalEntity> brain) {
		brain.addActivity(Activity.CORE,
			0,
			ImmutableList.of(
				new Swim/*? if >=1.21.3 {*/<>/*?}*/(0.8F),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink(),
				new CountDownCooldownTicks(FriendsAndFoesMemoryModuleTypes.RASCAL_NOD_COOLDOWN.get())
			)
		);
	}

	private static void addIdleActivities(Brain<RascalEntity> brain) {
		brain.addActivity(
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
		brain.addActivityAndRemoveMemoryWhenStopped(
			FriendsAndFoesActivities.RASCAL_WAIT.get(),
			10,
			ImmutableList.of(
				new RascalWaitForPlayerTask()
			), MemoryModuleType.INTERACTION_TARGET
		);
	}

	private static void addAvoidActivities(Brain<RascalEntity> brain) {
		brain.addActivityAndRemoveMemoryWhenStopped(
			Activity.AVOID,
			10,
			ImmutableList.of(
				SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.0F, 32, true)
			),
			MemoryModuleType.AVOID_TARGET
		);
	}

	public static void updateActivities(RascalEntity rascal) {
		rascal.getBrain().setActiveActivityToFirstValid(
			ImmutableList.of(
				FriendsAndFoesActivities.RASCAL_WAIT.get(),
				Activity.AVOID,
				Activity.IDLE
			)
		);
	}

	private static RunOne<RascalEntity> makeRandomWanderTask() {
		return new RunOne(
			ImmutableList.of(
				Pair.of(RandomStroll.stroll(0.6F), 2),
				Pair.of(new DoNothing(30, 60), 1)
			)
		);
	}

	public static void setNodCooldown(RascalEntity rascal) {
		rascal.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.RASCAL_NOD_COOLDOWN.get(), NOD_COOLDOWN_PROVIDER.sample(rascal.getRandom()));
		onCooldown(rascal);
	}

	public static boolean shouldRunAway(RascalEntity rascal) {
		return rascal.getBrain().getMemoryInternal(FriendsAndFoesMemoryModuleTypes.RASCAL_NOD_COOLDOWN.get()).isPresent();
	}

	public static void onCooldown(RascalEntity rascal) {
		if (!shouldRunAway(rascal)) {
			return;
		}

		if (rascal.hasCustomName()) {
			return;
		}

		LivingEntity nearestTarget = rascal.getBrain().getMemoryInternal(MemoryModuleType.INTERACTION_TARGET).orElse(null);

		if (nearestTarget == null) {
			return;
		}

		runAwayFrom(rascal, nearestTarget);
	}

	private static void runAwayFrom(RascalEntity rascal, LivingEntity target) {
		rascal.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
		rascal.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		rascal.getBrain().eraseMemory(MemoryModuleType.INTERACTION_TARGET);
		rascal.getBrain().setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, target, AVOID_MEMORY_DURATION.sample(rascal.getRandom()));
	}

	static {
		SENSORS = List.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS
		);
		MEMORY_MODULES = List.of(
			MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
			MemoryModuleType.PATH,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.AVOID_TARGET,
			MemoryModuleType.INTERACTION_TARGET,
			MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
			MemoryModuleType.NEAREST_PLAYERS,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			FriendsAndFoesMemoryModuleTypes.RASCAL_NOD_COOLDOWN.get()
		);
		NOD_COOLDOWN_PROVIDER = TimeUtil.rangeOfSeconds(NOD_COOLDOWN, NOD_COOLDOWN);
		AVOID_MEMORY_DURATION = TimeUtil.rangeOfSeconds(NOD_COOLDOWN, NOD_COOLDOWN);
	}
}