package com.faboslav.friendsandfoes.common.entity.ai.brain;

import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.coppergolem.CopperGolemLocateButtonTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.coppergolem.CopperGolemPressButtonTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.coppergolem.CopperGolemSpinHeadTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.coppergolem.CopperGolemTravelToButtonTask;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSensorTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class CopperGolemBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super CopperGolemEntity>>> SENSORS;
	private static final UniformInt SPIN_HEAD_COOLDOWN_PROVIDER;
	private static final UniformInt PRESS_BUTTON_COOLDOWN_PROVIDER;

	public CopperGolemBrain() {
	}

	public static Brain<?> create(Dynamic<?> dynamic) {
		Brain.Provider<CopperGolemEntity> profile = Brain.provider(MEMORY_MODULES, SENSORS);
		Brain<CopperGolemEntity> brain = profile.makeBrain(dynamic);

		addCoreActivities(brain);
		addAvoidActivities(brain);
		addIdleActivities(brain);
		addPressButtonActivities(brain);
		addSpinHeadActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();

		return brain;
	}

	private static void addCoreActivities(Brain<CopperGolemEntity> brain) {
		brain.addActivity(Activity.CORE,
			0,
			ImmutableList.of(
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink(),
				new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
				new CountDownCooldownTicks(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_SPIN_HEAD_COOLDOWN.get()),
				new CountDownCooldownTicks(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get())
			)
		);
	}

	private static void addPressButtonActivities(
		Brain<CopperGolemEntity> brain
	) {
		brain.addActivityWithConditions(
			FriendsAndFoesActivities.COPPER_GOLEM_PRESS_BUTTON.get(),
			ImmutableList.of(
				Pair.of(0, new CopperGolemLocateButtonTask()),
				Pair.of(1, new CopperGolemTravelToButtonTask()),
				Pair.of(2, new CopperGolemPressButtonTask())
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static void addSpinHeadActivities(
		Brain<CopperGolemEntity> brain
	) {
		brain.addActivityWithConditions(
			FriendsAndFoesActivities.COPPER_GOLEM_SPIN_HEAD.get(),
			ImmutableList.of(
				Pair.of(0, new CopperGolemSpinHeadTask())
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_SPIN_HEAD_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get(), MemoryStatus.VALUE_PRESENT)
			)
		);
	}

	private static void addAvoidActivities(Brain<CopperGolemEntity> brain) {
		brain.addActivityAndRemoveMemoryWhenStopped(
			Activity.AVOID,
			10,
			ImmutableList.of(
				SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.25F, 8, true)
			),
			MemoryModuleType.AVOID_TARGET
		);
	}

	private static void addIdleActivities(Brain<CopperGolemEntity> brain) {
		brain.addActivityWithConditions(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, new FollowTemptation(copperGolem -> 1.25f)),
				Pair.of(1, new RunOne(
					ImmutableList.of(
						Pair.of(RandomStroll.stroll(1.0F), 2),
						Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2),
						Pair.of(new DoNothing(30, 60), 1)
					)
				))
			),
			ImmutableSet.of(
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_SPIN_HEAD_COOLDOWN.get(), MemoryStatus.VALUE_PRESENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get(), MemoryStatus.VALUE_PRESENT)
			)
		);
	}

	public static void updateActivities(CopperGolemEntity copperGolem) {
		copperGolem.getBrain().setActiveActivityToFirstValid(
			ImmutableList.of(
				FriendsAndFoesActivities.COPPER_GOLEM_PRESS_BUTTON.get(),
				FriendsAndFoesActivities.COPPER_GOLEM_SPIN_HEAD.get(),
				Activity.AVOID,
				Activity.IDLE
			)
		);
	}

	public static void setSpinHeadCooldown(CopperGolemEntity copperGolem) {
		copperGolem.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_SPIN_HEAD_COOLDOWN.get(), SPIN_HEAD_COOLDOWN_PROVIDER.sample(copperGolem.getRandom()));
	}

	public static void setPressButtonCooldown(CopperGolemEntity copperGolem) {
		copperGolem.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get(), PRESS_BUTTON_COOLDOWN_PROVIDER.sample(copperGolem.getRandom()));
	}

	public static void setPressButtonCooldown(CopperGolemEntity copperGolem, UniformInt cooldown) {
		copperGolem.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get(), cooldown.sample(copperGolem.getRandom()));
	}

	public static Ingredient getTemptItems() {
		return Ingredient.of(Items.HONEYCOMB);
	}

	static {
		SENSORS = List.of(
			FriendsAndFoesSensorTypes.COPPER_GOLEM_TEMPTATIONS.get(),
			FriendsAndFoesSensorTypes.COPPER_GOLEM_SPECIFIC_SENSOR.get()
		);
		MEMORY_MODULES = List.of(
			MemoryModuleType.TEMPTING_PLAYER,
			MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
			MemoryModuleType.IS_TEMPTED,
			MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
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
		SPIN_HEAD_COOLDOWN_PROVIDER = TimeUtil.rangeOfSeconds(8, 16);
		PRESS_BUTTON_COOLDOWN_PROVIDER = TimeUtil.rangeOfSeconds(15, 30);
	}
}