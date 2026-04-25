package com.faboslav.friendsandfoes.common.entity.ai.brain;

import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.penguin.PenguinSwimWithPlayerTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.penguin.PenguinWingFlapTask;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSensorTypes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
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
import com.google.common.collect.ImmutableSet;

//? if >= 26.1 {
import net.minecraft.world.entity.ai.ActivityData;
//?} else {
/*import com.mojang.serialization.Dynamic;
*///?}

public final class PenguinBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super PenguinEntity>>> SENSORS;
	public static final Brain.Provider<PenguinEntity> BRAIN_PROVIDER;
	private static final UniformInt WING_FLAP_COOLDOWN;

	//? if >= 26.1 {
	public static Brain<PenguinEntity> create(PenguinEntity penguin, final Brain.Packed packedBrain) {
		return BRAIN_PROVIDER.makeBrain(penguin, packedBrain);
	}
	//?} else {
	/*public static Brain<PenguinEntity> create(Dynamic<?> dynamic) {
		Brain<PenguinEntity> brain = BRAIN_PROVIDER.makeBrain(dynamic);

		addCoreActivities(brain);
		addFightActivities(brain);
		addAvoidActivities(brain);
		addIdleActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();

		return brain;
	}
	*///?}

	//? if >= 26.1 {
	protected static List<ActivityData<PenguinEntity>> addActivities(PenguinEntity penguin)
	//?} else {
	/*protected static void addActivities(Brain<PenguinEntity> brain)
	 *///?}
	{
		//? if >= 26.1 {
		return List.of(
			addCoreActivities(),
			addIdleActivities(),
			addAvoidActivities()

		);
		//?} else {
		/*addCoreActivities(brain);
		addIdleActivities(brain);
		addAvoidActivities(brain);
		*///?}
	}

	//? if >= 26.1 {
	private static ActivityData<PenguinEntity> addCoreActivities()
	//?} else {
	/*private static void addCoreActivities(Brain<PenguinEntity> brain)
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
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink(),
				new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
				new CountDownCooldownTicks(FriendsAndFoesMemoryModuleTypes.PENGUIN_WING_FLAP_COOLDOWN.get())
			)
		);
	}

	//? if >= 26.1 {
	private static ActivityData<PenguinEntity> addAvoidActivities()
	//?} else {
	/*private static void addAvoidActivities(Brain<PenguinEntity> brain)
	 *///?}
	{
		//? if >= 26.1 {
		return ActivityData.create(
			//?} else {
			/*brain.addActivityAndRemoveMemoryWhenStopped(
			 *///?}
			Activity.AVOID,
			10,
			ImmutableList.of(
				SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.4F, 16, true)
			),
			MemoryModuleType.AVOID_TARGET
		);
	}

	private static RunOne<PenguinEntity> makeRandomWanderTask() {
		return new RunOne(
			ImmutableList.of(
				Pair.of(RandomStroll.stroll(1.0f), 2),
				Pair.of(SetWalkTargetFromLookTarget.create(1.0f, 3), 2),
				Pair.of(new DoNothing(30, 60), 1)
			)
		);
	}

	//? if >= 26.1 {
	private static ActivityData<PenguinEntity> addIdleActivities()
	//?} else {
	/*private static void addIdleActivities(Brain<PenguinEntity> brain)
	 *///?}
	{
		//? if >= 26.1 {
		return ActivityData.create(
			//?} else {
			/*brain.addActivityWithConditions(
			 *///?}
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
				Pair.of(0, new FollowTemptation(penguin -> 1.25f)),
				//Pair.of(1, new CrabBreedTask(FriendsAndFoesEntityTypes.PENGUIN.get())),
				Pair.of(2, BabyFollowAdult.create(UniformInt.of(5, 16), 1.25f)),
				Pair.of(2, new PenguinWingFlapTask()),
				Pair.of(3, new PenguinSwimWithPlayerTask()),
				Pair.of(4, new RunOne(
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
				Activity.AVOID,
				Activity.FIGHT,
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

	public static void setWingFlapCooldown(PenguinEntity penguin) {
		penguin.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.PENGUIN_WING_FLAP_COOLDOWN.get(), WING_FLAP_COOLDOWN.sample(penguin.getRandom()));
	}

	public static Predicate<ItemStack> getTemptations() {
		return itemStack -> itemStack.is(FriendsAndFoesTags.PENGUIN_TEMPT_ITEMS);
	}

	static {
		SENSORS = List.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS,
			SensorType.NEAREST_ADULT,
			FriendsAndFoesSensorTypes.PENGUIN_SPECIFIC_SENSOR.get(),
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
			MemoryModuleType.AVOID_TARGET,
			FriendsAndFoesMemoryModuleTypes.PENGUIN_HAS_EGG.get(),
			FriendsAndFoesMemoryModuleTypes.PENGUIN_EGG_POS.get(),
			FriendsAndFoesMemoryModuleTypes.PENGUIN_WING_FLAP_COOLDOWN.get()
		);
		BRAIN_PROVIDER = Brain.provider(
			MEMORY_MODULES,
			SENSORS
			//? if >= 26.1 {
			, PenguinBrain::addActivities
			//?}
		);
		WING_FLAP_COOLDOWN = TimeUtil.rangeOfSeconds(5, 6);
	}
}
