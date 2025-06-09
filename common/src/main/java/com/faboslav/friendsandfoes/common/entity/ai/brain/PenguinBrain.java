package com.faboslav.friendsandfoes.common.entity.ai.brain;

import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSensorTypes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import java.util.List;
import java.util.function.Predicate;

public final class PenguinBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super PenguinEntity>>> SENSORS;
	private static final UniformInt SLAP_COOLDOWN_PROVIDER;

	public static Brain<?> create(Dynamic<?> dynamic) {
		Brain.Provider<PenguinEntity> profile = Brain.provider(MEMORY_MODULES, SENSORS);
		Brain<PenguinEntity> brain = profile.makeBrain(dynamic);

		addCoreActivities(brain);
		addIdleActivities(brain);
		addLayEggActivities(brain);
		addGuardEggActivities(brain);
		addSlapActivities(brain);

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
				new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
				new CountDownCooldownTicks(FriendsAndFoesMemoryModuleTypes.PENGUIN_SLAP_COOLDOWN.get())
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

	private static void addSlapActivities(Brain<PenguinEntity> brain) {
		brain.addActivityWithConditions(
			FriendsAndFoesActivities.PENGUIN_SLAP.get(),
			ImmutableList.of(

			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryStatus.VALUE_PRESENT),
				Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.PENGUIN_SLAP_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.PENGUIN_HAS_EGG.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.PENGUIN_EGG_POS.get(), MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static void addIdleActivities(Brain<PenguinEntity> brain) {
		brain.addActivityWithConditions(
			Activity.IDLE,
			ImmutableList.of(
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
				Pair.of(FriendsAndFoesMemoryModuleTypes.PENGUIN_SLAP_COOLDOWN.get(), MemoryStatus.VALUE_PRESENT),
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

	public static void setSlapCooldown(PenguinEntity penguin) {
		penguin.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.PENGUIN_SLAP_COOLDOWN.get(), SLAP_COOLDOWN_PROVIDER.sample(penguin.getRandom()));
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
			FriendsAndFoesMemoryModuleTypes.PENGUIN_EGG_POS.get(),
			FriendsAndFoesMemoryModuleTypes.PENGUIN_SLAP_COOLDOWN.get()
		);
		SLAP_COOLDOWN_PROVIDER = TimeUtil.rangeOfSeconds(20, 40);
	}
}
