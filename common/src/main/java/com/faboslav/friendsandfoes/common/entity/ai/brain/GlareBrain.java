package com.faboslav.friendsandfoes.common.entity.ai.brain;

import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare.*;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
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
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.GoToWantedItem;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StayCloseToTarget;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.goat.GoatAi;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class GlareBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super GlareEntity>>> SENSORS;
	private static final UniformInt DARK_SPOT_LOCATING_COOLDOWN_PROVIDER;
	private static final UniformInt EAT_GLOW_BERRIES_COOLDOWN_PROVIDER;

	public GlareBrain() {
	}

	public static Brain<?> create(Dynamic<?> dynamic) {
		Brain.Provider<GlareEntity> profile = Brain.provider(MEMORY_MODULES, SENSORS);
		Brain<GlareEntity> brain = profile.makeBrain(dynamic);

		addCoreActivities(brain);
		addAvoidActivities(brain);
		addIdleActivities(brain);
		addDarkSpotActivities(brain);
		addGlowBerriesActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();

		return brain;
	}

	private static void addCoreActivities(Brain<GlareEntity> brain) {
		brain.addActivity(Activity.CORE,
			0,
			ImmutableList.of(
				new Swim/*? >=1.21.3 {*/<>/*?}*/(0.8f),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink(),
				new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
				new CountDownCooldownTicks(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS),
				new CountDownCooldownTicks(FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get()),
				new CountDownCooldownTicks(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get())
			)
		);
	}

	private static void addDarkSpotActivities(
		Brain<GlareEntity> brain
	) {
		brain.addActivityWithConditions(
			FriendsAndFoesActivities.GLARE_SHOW_DARK_SPOT.get(),
			ImmutableList.of(
				Pair.of(0, new GlareLocateDarkSpotTask()),
				Pair.of(1, new GlareTravelToDarkSpotTask()),
				Pair.of(2, new GlareBeGrumpyAtDarkSpotTask())
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.AVOID_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_IS_TAMED.get(), MemoryStatus.VALUE_PRESENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static void addGlowBerriesActivities(
		Brain<GlareEntity> brain
	) {
		brain.addActivityWithConditions(
			FriendsAndFoesActivities.GLARE_EAT_GLOW_BERRIES.get(),
			ImmutableList.of(
				Pair.of(0, GoToWantedItem.create(glare -> true, 1.25F, true, 32)),
				Pair.of(1, new GlareLocateGlowBerriesTask()),
				Pair.of(2, new GlareTravelToGlowBerriesTask()),
				Pair.of(3, new GlareShakeGlowBerriesTask())
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.AVOID_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static void addAvoidActivities(Brain<GlareEntity> brain) {
		brain.addActivityWithConditions(
			Activity.AVOID,
			ImmutableList.of(
				Pair.of(0, SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.25F, 16, false))
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.AVOID_TARGET, MemoryStatus.VALUE_PRESENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_IS_TAMED.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static void addIdleActivities(Brain<GlareEntity> brain) {
		brain.addActivityWithConditions(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, new FollowTemptation(glare -> 1.25f)),
				Pair.of(1, new AnimalMakeLove(FriendsAndFoesEntityTypes.GLARE.get())),
				Pair.of(2, BabyFollowAdult.create(UniformInt.of(5, 16), 1.25f)),
				Pair.of(3, new GlareTeleportToOwnerTask()),
				Pair.of(4, StayCloseToTarget.create(glare -> getOwner((GlareEntity) glare), (glare) -> true, 3, 8, 2.0f)),
				Pair.of(5, SetEntityLookTargetSometimes.create(3.0f, UniformInt.of(30, 60))),
				Pair.of(6, new RunOne(
					ImmutableList.of(
						Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 3),
						Pair.of(new GlareStrollTask(), 2),
						Pair.of(new DoNothing(30, 60), 1)
					)
				))
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.AVOID_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), MemoryStatus.VALUE_PRESENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get(), MemoryStatus.VALUE_PRESENT)
			)
		);
	}

	private static Optional<PositionTracker> getOwner(GlareEntity glare) {
		if (
			!glare.isTame()
			|| glare.getOwner() == null
		) {
			return Optional.empty();
		}

		return Optional.of(new EntityTracker(glare.getOwner(), true));
	}

	public static void updateActivities(GlareEntity glare) {
		glare.getBrain().setActiveActivityToFirstValid(
			ImmutableList.of(
				FriendsAndFoesActivities.GLARE_SHOW_DARK_SPOT.get(),
				FriendsAndFoesActivities.GLARE_EAT_GLOW_BERRIES.get(),
				Activity.AVOID,
				Activity.IDLE
			)
		);
	}

	public static void updateMemories(GlareEntity glare) {
		if (
			(
				(!glare.isBaby() && !glare.isTame())
				|| !GlareLocateDarkSpotTask.canLocateDarkSpot(glare)
			)
			&& glare.getBrain().checkMemory(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT)
		) {
			GlareBrain.setDarkSpotLocatingCooldown(glare);
		}

		if (
			glare.isOrderedToSit()
			|| glare.isLeashed()
			|| glare.isPassenger()
		) {
			glare.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), true);
		} else {
			glare.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get());
		}

		if (glare.isTame()) {
			glare.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.GLARE_IS_TAMED.get(), true);
		} else {
			glare.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.GLARE_IS_TAMED.get());
		}
	}

	public static void setDarkSpotLocatingCooldown(GlareEntity glare) {
		glare.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), DARK_SPOT_LOCATING_COOLDOWN_PROVIDER.sample(glare.getRandom()));
	}

	public static void setDarkSpotLocatingCooldown(GlareEntity glare, UniformInt cooldown) {
		glare.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), cooldown.sample(glare.getRandom()));
	}

	public static void setLocatingGlowBerriesCooldown(GlareEntity glare) {
		glare.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get(), EAT_GLOW_BERRIES_COOLDOWN_PROVIDER.sample(glare.getRandom()));
	}

	public static void setLocatingGlowBerriesCooldown(GlareEntity glare, UniformInt cooldown) {
		glare.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get(), cooldown.sample(glare.getRandom()));
	}

	public static void setItemPickupCooldown(GlareEntity glare) {
		glare.getBrain().setMemory(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, TimeUtil.rangeOfSeconds(1, 10).sample(glare.getRandom()));
	}

	public static Predicate<ItemStack> getTemptations() {
		return itemStack -> itemStack.is(FriendsAndFoesTags.GLARE_TEMPT_ITEMS);
	}

	static {
		SENSORS = List.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS,
			SensorType.NEAREST_ITEMS,
			SensorType.NEAREST_ADULT,
			FriendsAndFoesSensorTypes.GLARE_TEMPTATIONS.get(),
			FriendsAndFoesSensorTypes.GLARE_SPECIFIC_SENSOR.get()
		);
		MEMORY_MODULES = List.of(
			MemoryModuleType.IS_PANICKING,
			MemoryModuleType.TEMPTING_PLAYER,
			MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
			MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS,
			MemoryModuleType.IS_PANICKING,
			MemoryModuleType.IS_TEMPTED,
			MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
			MemoryModuleType.PATH,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.ATE_RECENTLY,
			MemoryModuleType.BREED_TARGET,
			MemoryModuleType.AVOID_TARGET,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
			FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(),
			FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get(),
			FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(),
			FriendsAndFoesMemoryModuleTypes.GLARE_IS_TAMED.get(),
			FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(),
			FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get()
		);
		DARK_SPOT_LOCATING_COOLDOWN_PROVIDER = TimeUtil.rangeOfSeconds(20, 40);
		EAT_GLOW_BERRIES_COOLDOWN_PROVIDER = TimeUtil.rangeOfSeconds(30, 60);
	}
}