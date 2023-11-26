package com.faboslav.friendsandfoes.entity.ai.brain;

import com.faboslav.friendsandfoes.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.task.coppergolem.CopperGolemLocateButtonTask;
import com.faboslav.friendsandfoes.entity.ai.brain.task.coppergolem.CopperGolemPressButtonTask;
import com.faboslav.friendsandfoes.entity.ai.brain.task.coppergolem.CopperGolemSpinHeadTask;
import com.faboslav.friendsandfoes.entity.ai.brain.task.coppergolem.CopperGolemTravelToButtonTask;
import com.faboslav.friendsandfoes.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemorySensorType;
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
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class CopperGolemBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super CopperGolemEntity>>> SENSORS;
	private static final UniformIntProvider SPIN_HEAD_COOLDOWN_PROVIDER;
	private static final UniformIntProvider PRESS_BUTTON_COOLDOWN_PROVIDER;

	public CopperGolemBrain() {
	}

	public static Brain<?> create(Dynamic<?> dynamic) {
		Brain.Profile<CopperGolemEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
		Brain<CopperGolemEntity> brain = profile.deserialize(dynamic);

		addCoreActivities(brain);
		addAvoidActivities(brain);
		addIdleActivities(brain);
		addPressButtonActivities(brain);
		addSpinHeadActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();

		return brain;
	}

	private static void addCoreActivities(Brain<CopperGolemEntity> brain) {
		brain.setTaskList(Activity.CORE,
			0,
			ImmutableList.of(
				new LookAroundTask(45, 90),
				new WanderAroundTask(),
				new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
				new TemptationCooldownTask(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_SPIN_HEAD_COOLDOWN.get()),
				new TemptationCooldownTask(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get())
			)
		);
	}

	private static void addPressButtonActivities(
		Brain<CopperGolemEntity> brain
	) {
		brain.setTaskList(
			FriendsAndFoesActivities.COPPER_GOLEM_PRESS_BUTTON.get(),
			ImmutableList.of(
				Pair.of(0, new CopperGolemLocateButtonTask()),
				Pair.of(1, new CopperGolemTravelToButtonTask()),
				Pair.of(2, new CopperGolemPressButtonTask())
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryModuleState.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), MemoryModuleState.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT)
			)
		);
	}

	private static void addSpinHeadActivities(
		Brain<CopperGolemEntity> brain
	) {
		brain.setTaskList(
			FriendsAndFoesActivities.COPPER_GOLEM_SPIN_HEAD.get(),
			ImmutableList.of(
				Pair.of(0, new CopperGolemSpinHeadTask())
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryModuleState.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), MemoryModuleState.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get(), MemoryModuleState.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_SPIN_HEAD_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get(), MemoryModuleState.VALUE_PRESENT)
			)
		);
	}

	private static void addAvoidActivities(Brain<CopperGolemEntity> brain) {
		brain.setTaskList(
			Activity.AVOID,
			10,
			ImmutableList.of(
				GoToRememberedPositionTask.toEntity(MemoryModuleType.AVOID_TARGET, 1.25F, 8, true)
			),
			MemoryModuleType.AVOID_TARGET
		);
	}

	private static void addIdleActivities(Brain<CopperGolemEntity> brain) {
		brain.setTaskList(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, new TemptTask(copperGolem -> 1.25f)),
				Pair.of(1, new RandomTask(
					ImmutableList.of(
						Pair.of(new StrollTask(1.0F), 2),
						Pair.of(new GoTowardsLookTarget(1.0F, 3), 2),
						Pair.of(new WaitTask(30, 60), 1)
					)
				))
			),
			ImmutableSet.of(
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), MemoryModuleState.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_SPIN_HEAD_COOLDOWN.get(), MemoryModuleState.VALUE_PRESENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get(), MemoryModuleState.VALUE_PRESENT)
			)
		);
	}

	public static void updateActivities(CopperGolemEntity copperGolem) {
		copperGolem.getBrain().resetPossibleActivities(
			ImmutableList.of(
				FriendsAndFoesActivities.COPPER_GOLEM_PRESS_BUTTON.get(),
				FriendsAndFoesActivities.COPPER_GOLEM_SPIN_HEAD.get(),
				Activity.AVOID,
				Activity.IDLE
			)
		);
	}

	public static void setSpinHeadCooldown(CopperGolemEntity copperGolem) {
		copperGolem.getBrain().remember(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_SPIN_HEAD_COOLDOWN.get(), SPIN_HEAD_COOLDOWN_PROVIDER.get(copperGolem.getRandom()));
	}

	public static void setPressButtonCooldown(CopperGolemEntity copperGolem) {
		copperGolem.getBrain().remember(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get(), PRESS_BUTTON_COOLDOWN_PROVIDER.get(copperGolem.getRandom()));
	}

	public static void setPressButtonCooldown(CopperGolemEntity copperGolem, UniformIntProvider cooldown) {
		copperGolem.getBrain().remember(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get(), cooldown.get(copperGolem.getRandom()));
	}

	public static Ingredient getTemptItems() {
		return Ingredient.ofItems(Items.HONEYCOMB);
	}

	static {
		SENSORS = List.of(
			FriendsAndFoesMemorySensorType.COPPER_GOLEM_TEMPTATIONS.get(),
			FriendsAndFoesMemorySensorType.COPPER_GOLEM_SPECIFIC_SENSOR.get()
		);
		MEMORY_MODULES = List.of(
			MemoryModuleType.TEMPTING_PLAYER,
			MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
			MemoryModuleType.IS_TEMPTED,
			MemoryModuleType.VISIBLE_MOBS,
			MemoryModuleType.PATH,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.AVOID_TARGET,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(),
			FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get(),
			FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_SPIN_HEAD_COOLDOWN.get(),
			FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get()
		);
		SPIN_HEAD_COOLDOWN_PROVIDER = TimeHelper.betweenSeconds(8, 16);
		PRESS_BUTTON_COOLDOWN_PROVIDER = TimeHelper.betweenSeconds(15, 30);
	}
}