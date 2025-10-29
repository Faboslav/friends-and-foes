package com.faboslav.friendsandfoes.common.entity.ai.brain;

import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare.GlareLocateDarkSpotTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.penguin.PenguinSwimWithPlayerTask;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSensorTypes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.axolotl.AxolotlAi;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import java.util.List;
import java.util.function.Predicate;

public final class PenguinBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super PenguinEntity>>> SENSORS;

	public static Brain<?> create(Dynamic<?> dynamic) {
		Brain.Provider<PenguinEntity> profile = Brain.provider(MEMORY_MODULES, SENSORS);
		Brain<PenguinEntity> brain = profile.makeBrain(dynamic);

		addCoreActivities(brain);
		addIdleActivities(brain);
		addLayEggActivities(brain);
		addGuardEggActivities(brain);
		addSwimActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();

		return brain;
	}

	private static void addCoreActivities(Brain<PenguinEntity> brain) {
		brain.addActivity(
			Activity.CORE,
			0,
			ImmutableList.of(
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink(),
				new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
			)
		);
	}

	private static void addLayEggActivities(Brain<PenguinEntity> brain) {
		brain.addActivityWithConditions(
			FriendsAndFoesActivities.PENGUIN_LAY_EGG.get(),
			ImmutableList.of(
			),
			ImmutableSet.of(
				Pair.of(FriendsAndFoesMemoryModuleTypes.PENGUIN_HAS_EGG.get(), MemoryStatus.VALUE_PRESENT)
			)
		);
	}

	private static void addGuardEggActivities(Brain<PenguinEntity> brain) {
		brain.addActivityWithConditions(
			FriendsAndFoesActivities.PENGUIN_GUARD_EGG.get(),
			ImmutableList.of(

			),
			ImmutableSet.of(
				Pair.of(FriendsAndFoesMemoryModuleTypes.PENGUIN_HAS_EGG.get(), MemoryStatus.VALUE_PRESENT)
			)
		);
	}

	private static void addSwimActivities(Brain<PenguinEntity> brain) {
		brain.addActivityWithConditions(
			FriendsAndFoesActivities.PENGUIN_SWIM.get(),
			ImmutableList.of(
				Pair.of(0, new PenguinSwimWithPlayerTask())
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.PENGUIN_HAS_EGG.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.PENGUIN_EGG_POS.get(), MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	/*
	private static void initIdleActivity(Brain<Axolotl> brain) {
		brain.addActivity(Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
				Pair.of(1, new AnimalMakeLove(EntityType.AXOLOTL, 0.2F, 2)),
				Pair.of(2, new RunOne(ImmutableList.of(
					Pair.of(new FollowTemptation(AxolotlAi::getSpeedModifier), 1),
					Pair.of(BabyFollowAdult.create(ADULT_FOLLOW_RANGE, AxolotlAi::getSpeedModifierFollowingAdult, MemoryModuleType.NEAREST_VISIBLE_ADULT, false), 1)))
				),
				Pair.of(3, StartAttacking.create(AxolotlAi::findNearestValidAttackTarget)),
				Pair.of(3, TryFindWater.create(6, 0.15F)),
				Pair.of(4, new GateBehavior(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), ImmutableSet.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, ImmutableList.of(
					Pair.of(RandomStroll.swim(0.5F), 2),
					Pair.of(RandomStroll.stroll(0.15F, false), 2),
					Pair.of(SetWalkTargetFromLookTarget.create(AxolotlAi::canSetWalkTargetFromLookTarget, AxolotlAi::getSpeedModifier, 3), 3),
					Pair.of(BehaviorBuilder.triggerIf(Entity::isInWater), 5),
					Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), 5)))
				)
			)
		);
	}*/

	private static void addIdleActivities(Brain<PenguinEntity> brain) {
		brain.addActivityWithConditions(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
				Pair.of(0, new FollowTemptation(penguin -> 1.25f)),
				//Pair.of(1, new CrabBreedTask(FriendsAndFoesEntityTypes.PENGUIN.get())),
				Pair.of(2, BabyFollowAdult.create(UniformInt.of(5, 16), 1.25f)),
				Pair.of(3, new RunOne(
					ImmutableList.of(
						Pair.of(RandomStroll.stroll(1.0f), 2),
						Pair.of(SetWalkTargetFromLookTarget.create(1.0f, 3), 2),
						Pair.of(new DoNothing(30, 60), 1)))
				)
			),
			ImmutableSet.of(
				Pair.of(FriendsAndFoesMemoryModuleTypes.PENGUIN_HAS_EGG.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.PENGUIN_EGG_POS.get(), MemoryStatus.VALUE_ABSENT)
			));
	}

	public static void updateActivities(PenguinEntity penguin) {
		penguin.getBrain().setActiveActivityToFirstValid(
			ImmutableList.of(
				FriendsAndFoesActivities.PENGUIN_LAY_EGG.get(),
				FriendsAndFoesActivities.PENGUIN_GUARD_EGG.get(),
				Activity.IDLE
			)
		);
	}

	public static void updateMemories(PenguinEntity penguin) {
		if (penguin.hasEgg()) {
			penguin.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.PENGUIN_HAS_EGG.get(), true);
		} else {
			penguin.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.PENGUIN_HAS_EGG.get());
		}

	}

	public static Predicate<ItemStack> getTemptations() {
		return itemStack -> itemStack.is(FriendsAndFoesTags.PENGUIN_TEMPT_ITEMS);
	}

	static {
		SENSORS = List.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS,
			SensorType.NEAREST_ADULT,
			FriendsAndFoesSensorTypes.PENGUIN_TEMPTATIONS.get()
		);
		MEMORY_MODULES = List.of(
			MemoryModuleType.TEMPTING_PLAYER,
			MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
			MemoryModuleType.IS_TEMPTED,
			MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
			MemoryModuleType.PATH,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.BREED_TARGET,
			MemoryModuleType.IS_PANICKING,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			MemoryModuleType.NEAREST_VISIBLE_PLAYER,
			MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
			FriendsAndFoesMemoryModuleTypes.PENGUIN_HAS_EGG.get(),
			FriendsAndFoesMemoryModuleTypes.PENGUIN_EGG_POS.get()
		);
	}
}
