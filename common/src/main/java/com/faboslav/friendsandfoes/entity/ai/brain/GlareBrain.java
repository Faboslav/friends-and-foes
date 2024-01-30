package com.faboslav.friendsandfoes.entity.ai.brain;

import com.faboslav.friendsandfoes.entity.GlareEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.task.glare.*;
import com.faboslav.friendsandfoes.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemorySensorType;
import com.faboslav.friendsandfoes.tag.FriendsAndFoesTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class GlareBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super GlareEntity>>> SENSORS;
	private static final UniformIntProvider DARK_SPOT_LOCATING_COOLDOWN_PROVIDER;
	private static final UniformIntProvider EAT_GLOW_BERRIES_COOLDOWN_PROVIDER;

	public GlareBrain() {
	}

	public static Brain<?> create(Dynamic<?> dynamic) {
		Brain.Profile<GlareEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
		Brain<GlareEntity> brain = profile.deserialize(dynamic);

		addCoreActivities(brain);
		addAvoidActivities(brain);
		addIdleActivities(brain);
		addDarkSpotActivities(brain);
		addGlowBerriesActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();

		return brain;
	}

	private static void addCoreActivities(Brain<GlareEntity> brain) {
		brain.setTaskList(Activity.CORE,
			0,
			ImmutableList.of(
				new StayAboveWaterTask(0.8f),
				new LookAroundTask(45, 90),
				new WanderAroundTask(),
				new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
				new TemptationCooldownTask(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS),
				new TemptationCooldownTask(FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get()),
				new TemptationCooldownTask(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get())
			)
		);
	}

	private static void addDarkSpotActivities(
		Brain<GlareEntity> brain
	) {
		brain.setTaskList(
			FriendsAndFoesActivities.GLARE_SHOW_DARK_SPOT.get(),
			ImmutableList.of(
				Pair.of(0, new GlareLocateDarkSpotTask()),
				Pair.of(1, new GlareTravelToDarkSpotTask()),
				Pair.of(2, new GlareBeGrumpyAtDarkSpotTask())
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.AVOID_TARGET, MemoryModuleState.VALUE_ABSENT),
				Pair.of(MemoryModuleType.BREED_TARGET, MemoryModuleState.VALUE_ABSENT),
				Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryModuleState.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_IS_TAMED.get(), MemoryModuleState.VALUE_PRESENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), MemoryModuleState.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT)
			)
		);
	}

	private static void addGlowBerriesActivities(
		Brain<GlareEntity> brain
	) {
		brain.setTaskList(
			FriendsAndFoesActivities.GLARE_EAT_GLOW_BERRIES.get(),
			ImmutableList.of(
				Pair.of(0, WalkToNearestVisibleWantedItemTask.create(glare -> true, 1.25F, true, 32)),
				Pair.of(1, new GlareLocateGlowBerriesTask()),
				Pair.of(2, new GlareTravelToGlowBerriesTask()),
				Pair.of(3, new GlareShakeGlowBerriesTask())
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.AVOID_TARGET, MemoryModuleState.VALUE_ABSENT),
				Pair.of(MemoryModuleType.BREED_TARGET, MemoryModuleState.VALUE_ABSENT),
				Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryModuleState.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), MemoryModuleState.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT)
			)
		);
	}

	private static void addAvoidActivities(Brain<GlareEntity> brain) {
		brain.setTaskList(
			Activity.AVOID,
			ImmutableList.of(
				Pair.of(0, GoToRememberedPositionTask.createEntityBased(MemoryModuleType.AVOID_TARGET, 1.25F, 16, false))
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.AVOID_TARGET, MemoryModuleState.VALUE_PRESENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), MemoryModuleState.VALUE_ABSENT)
			)
		);
	}

	private static void addIdleActivities(Brain<GlareEntity> brain) {
		brain.setTaskList(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, new TemptTask(glare -> 1.25f)),
				Pair.of(1, new BreedTask(FriendsAndFoesEntityTypes.GLARE.get(), 1.0f)),
				Pair.of(2, WalkTowardClosestAdultTask.create(UniformIntProvider.create(5, 16), 1.25f)),
				Pair.of(3, WalkTowardsLookTargetTask.create(glare -> getOwner((GlareEntity) glare), 4, 16, 2.0f)),
				Pair.of(4, FollowMobWithIntervalTask.follow(6.0F, UniformIntProvider.create(30, 60))),
				Pair.of(5, new RandomTask(
					ImmutableList.of(
						Pair.of(GoTowardsLookTargetTask.create(1.0F, 3), 3),
						Pair.of(new GlareStrollTask(), 2),
						Pair.of(new WaitTask(30, 60), 1)
					)
				))
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.AVOID_TARGET, MemoryModuleState.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), MemoryModuleState.VALUE_ABSENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), MemoryModuleState.VALUE_PRESENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get(), MemoryModuleState.VALUE_PRESENT)
			)
		);
	}

	private static Optional<LookTarget> getOwner(GlareEntity glare) {
		if (
			glare.isTamed() == false
			|| glare.getOwner() == null
		) {
			return Optional.empty();
		}

		return Optional.of(new EntityLookTarget(glare.getOwner(), true));
	}

	public static void updateActivities(GlareEntity glare) {
		glare.getBrain().resetPossibleActivities(
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
			glare.isBaby()
			&& glare.getBrain().isMemoryInState(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT)
		) {
			GlareBrain.setDarkSpotLocatingCooldown(glare);
		}

		if (
			glare.isSitting()
			|| glare.isLeashed()
		) {
			glare.getBrain().remember(FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get(), true);
		} else {
			glare.getBrain().forget(FriendsAndFoesMemoryModuleTypes.GLARE_IS_IDLE.get());
		}

		if (glare.isTamed()) {
			glare.getBrain().remember(FriendsAndFoesMemoryModuleTypes.GLARE_IS_TAMED.get(), true);
		} else {
			glare.getBrain().forget(FriendsAndFoesMemoryModuleTypes.GLARE_IS_TAMED.get());
		}
	}

	public static void setDarkSpotLocatingCooldown(GlareEntity glare) {
		glare.getBrain().remember(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), DARK_SPOT_LOCATING_COOLDOWN_PROVIDER.get(glare.getRandom()));
	}

	public static void setDarkSpotLocatingCooldown(GlareEntity glare, UniformIntProvider cooldown) {
		glare.getBrain().remember(FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(), cooldown.get(glare.getRandom()));
	}

	public static void setLocatingGlowBerriesCooldown(GlareEntity glare) {
		glare.getBrain().remember(FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get(), EAT_GLOW_BERRIES_COOLDOWN_PROVIDER.get(glare.getRandom()));
	}

	public static void setLocatingGlowBerriesCooldown(GlareEntity glare, UniformIntProvider cooldown) {
		glare.getBrain().remember(FriendsAndFoesMemoryModuleTypes.GLARE_LOCATING_GLOW_BERRIES_COOLDOWN.get(), cooldown.get(glare.getRandom()));
	}

	public static void setItemPickupCooldown(GlareEntity glare) {
		glare.getBrain().remember(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, TimeHelper.betweenSeconds(1, 10).get(glare.getRandom()));
	}

	public static Ingredient getTemptItems() {
		return Ingredient.fromTag(FriendsAndFoesTags.GLARE_TEMPT_ITEMS);
	}

	static {
		SENSORS = List.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS,
			SensorType.NEAREST_ITEMS,
			SensorType.NEAREST_ADULT,
			FriendsAndFoesMemorySensorType.GLARE_TEMPTATIONS.get(),
			FriendsAndFoesMemorySensorType.GLARE_SPECIFIC_SENSOR.get()
		);
		MEMORY_MODULES = List.of(
			MemoryModuleType.TEMPTING_PLAYER,
			MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
			MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS,
			MemoryModuleType.IS_TEMPTED,
			MemoryModuleType.VISIBLE_MOBS,
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
		DARK_SPOT_LOCATING_COOLDOWN_PROVIDER = TimeHelper.betweenSeconds(15, 30);
		EAT_GLOW_BERRIES_COOLDOWN_PROVIDER = TimeHelper.betweenSeconds(30, 60);
	}
}