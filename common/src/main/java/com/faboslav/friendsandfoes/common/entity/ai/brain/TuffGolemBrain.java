package com.faboslav.friendsandfoes.common.entity.ai.brain;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.tuffgolem.TuffGolemGoToHomePositionTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.tuffgolem.TuffGolemLookAroundTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.tuffgolem.TuffGolemSleepTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.tuffgolem.TuffGolemWanderAroundTask;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import java.util.List;

//? if >= 26.1 {
import net.minecraft.world.entity.ai.ActivityData;
//?} else {
/*import com.mojang.serialization.Dynamic;
*///?}

@SuppressWarnings({"rawtypes", "unchecked"})
public final class TuffGolemBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super TuffGolemEntity>>> SENSORS;
	public static final Brain.Provider<TuffGolemEntity> BRAIN_PROVIDER;
	private static final UniformInt SLEEP_COOLDOWN_PROVIDER;

	//? if >= 26.1 {
	public static Brain<TuffGolemEntity> create(TuffGolemEntity tuffGolem, final Brain.Packed packedBrain) {
		return BRAIN_PROVIDER.makeBrain(tuffGolem, packedBrain);
	}
	//?} else {
	/*public static Brain<TuffGolemEntity> create(Dynamic<?> dynamic) {
		Brain<TuffGolemEntity> brain = BRAIN_PROVIDER.makeBrain(dynamic);

		addActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();

		return brain;
	}
	*///?}

	//? if >= 26.1 {
	private static List<ActivityData<TuffGolemEntity>> addActivities(TuffGolemEntity tuffGolem)
	//?} else {
	/*private static void addActivities(Brain<TuffGolemEntity> brain)
	 *///?}
	{
		//? if >= 26.1 {
		return List.of(
			addCoreActivities(),
			addHomeActivities(),
			addIdleActivities()
		);
		//?} else {
		/*addCoreActivities(brain);
		addHomeActivities(brain);
		addIdleActivities(brain);
		*///?}
	}

	//? if >= 26.1 {
	private static ActivityData<TuffGolemEntity> addCoreActivities()
	//?} else {
	/*private static void addCoreActivities(Brain<TuffGolemEntity> brain)
	 *///?}
	{
		//? if >= 26.1 {
		return ActivityData.create(
		//?} else {
		/*brain.addActivity(
		*///?}
			Activity.CORE,
			0,
			ImmutableList.of(
				new TuffGolemLookAroundTask(45, 90),
				new TuffGolemWanderAroundTask(),
				new CountDownCooldownTicks(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get())
			));
	}

	//? if >= 26.1 {
	private static ActivityData<TuffGolemEntity> addHomeActivities()
	//?} else {
	/*private static void addHomeActivities(Brain<TuffGolemEntity> brain)
	 *///?}
	{
		//? if >= 26.1 {
		return ActivityData.create(
		//?} else {
		/*brain.addActivityWithConditions(
		*///?}
			FriendsAndFoesActivities.TUFF_GOLEM_HOME.get(),
			ImmutableList.of(
				Pair.of(0, new TuffGolemGoToHomePositionTask()),
				Pair.of(1, new TuffGolemSleepTask())
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	//? if >= 26.1 {
	private static ActivityData<TuffGolemEntity> addIdleActivities()
	//?} else {
	/*private static void addIdleActivities(Brain<TuffGolemEntity> brain)
	 *///?}
	{
		//? if >= 26.1 {
		return ActivityData.create(
		//?} else {
		/*brain.addActivityWithConditions(
		*///?}
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0,
					new RunOne(
						ImmutableList.of(
							Pair.of(SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60)), 3),
							//? if <= 1.21.8 {
							/*Pair.of(SetEntityLookTargetSometimes.create(FriendsAndFoesEntityTypes.COPPER_GOLEM.get(), 6.0F, UniformInt.of(30, 60)), 2),
							*///?}
							Pair.of(SetEntityLookTargetSometimes.create(FriendsAndFoesEntityTypes.TUFF_GOLEM.get(), 6.0F, UniformInt.of(30, 60)), 2),
							Pair.of(SetEntityLookTargetSometimes.create(EntityType.IRON_GOLEM, 6.0F, UniformInt.of(30, 60)), 1)
						)
					)
				),
				Pair.of(1,
					new RunOne(
						ImmutableMap.of(
							MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT
						),
						ImmutableList.of(
							Pair.of(new DoNothing(60, 80), 2),
							Pair.of(BehaviorBuilder.triggerIf(TuffGolemBrain::isNotImmobilized, RandomStroll.stroll(0.6F)), 1),
							Pair.of(BehaviorBuilder.triggerIf(TuffGolemBrain::isNotImmobilized, SetWalkTargetFromLookTarget.create(0.6F, 2)), 1)
						)
					)
				)
			),
			ImmutableSet.of(
				Pair.of(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), MemoryStatus.VALUE_PRESENT)
			)
		);
	}

	public static void updateActivities(TuffGolemEntity tuffGolem) {
		tuffGolem.getBrain().setActiveActivityToFirstValid(
			ImmutableList.of(
				FriendsAndFoesActivities.TUFF_GOLEM_HOME.get(),
				Activity.IDLE
			)
		);
	}

	public static void resetSleepCooldown(TuffGolemEntity tuffGolem) {
		tuffGolem.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get());
	}

	public static void setSleepCooldown(TuffGolemEntity tuffGolem) {
		tuffGolem.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), SLEEP_COOLDOWN_PROVIDER.sample(tuffGolem.getRandom()));
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
			MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
			MemoryModuleType.PATH,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			FriendsAndFoesMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get()
		);
		BRAIN_PROVIDER = Brain.provider(
			MEMORY_MODULES,
			SENSORS
			//? if >= 26.1 {
			, TuffGolemBrain::addActivities
			//?}
		);
		SLEEP_COOLDOWN_PROVIDER = UniformInt.of(6000, 8000);
	}
}
