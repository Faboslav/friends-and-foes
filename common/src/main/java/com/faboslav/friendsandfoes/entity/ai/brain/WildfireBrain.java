package com.faboslav.friendsandfoes.entity.ai.brain;

import com.faboslav.friendsandfoes.entity.WildfireEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.task.WildfireBarrageAttackTask;
import com.faboslav.friendsandfoes.entity.ai.brain.task.WildfireShockwaveAttackTask;
import com.faboslav.friendsandfoes.entity.ai.brain.task.WildfireSummonBlazeTask;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class WildfireBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super WildfireEntity>>> SENSORS;
	private static final UniformIntProvider BARRAGE_ATTACK_COOLDOWN_PROVIDER;
	private static final UniformIntProvider SHOCKWAVE_ATTACK_COOLDOWN_PROVIDER;
	private static final UniformIntProvider SUMMON_BLAZE_COOLDOWN_PROVIDER;
	private static final UniformIntProvider AVOID_MEMORY_DURATION;
	private static final TargetPredicate VALID_TARGET_PLAYER_PREDICATE;

	public WildfireBrain() {
	}

	public static Brain<?> create(Dynamic<?> dynamic) {
		Brain.Profile<WildfireEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
		Brain<WildfireEntity> brain = profile.deserialize(dynamic);

		addCoreActivities(brain);
		addIdleActivities(brain);
		addFightActivities(brain);
		addAvoidActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();

		return brain;
	}

	private static void addCoreActivities(Brain<WildfireEntity> brain) {
		brain.setTaskList(Activity.CORE,
			0,
			ImmutableList.of(
				new LookAroundTask(45, 90),
				new WanderAroundTask(),
				new TemptationCooldownTask(FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get()),
				new TemptationCooldownTask(FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get()),
				new TemptationCooldownTask(FriendsAndFoesMemoryModuleTypes.WILDFIRE_SUMMON_BLAZE_COOLDOWN.get())
			));
	}

	private static void addIdleActivities(Brain<WildfireEntity> brain) {
		brain.setTaskList(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, UpdateAttackTargetTask.create(wildfire -> true, WildfireBrain::getTarget)),
				Pair.of(0, makeRandomWanderTask())
			)
		);
	}

	private static void addFightActivities(
		Brain<WildfireEntity> brain
	) {
		brain.setTaskList(
			Activity.FIGHT,
			10,
			ImmutableList.of(
				new WildfireSummonBlazeTask(),
				new WildfireBarrageAttackTask(),
				new WildfireShockwaveAttackTask()
			),
			MemoryModuleType.ATTACK_TARGET
		);
	}

	private static void addAvoidActivities(Brain<WildfireEntity> brain) {
		brain.setTaskList(
			Activity.AVOID,
			10,
			ImmutableList.of(
				GoToRememberedPositionTask.createEntityBased(MemoryModuleType.AVOID_TARGET, 1.4F, 16, true),
				makeRandomWanderTask()
			),
			MemoryModuleType.AVOID_TARGET
		);
	}

	private static RandomTask<WildfireEntity> makeRandomWanderTask() {
		return new RandomTask(
			ImmutableList.of(
				Pair.of(StrollTask.create(0.6F), 2),
				Pair.of(GoTowardsLookTargetTask.create(1.0F, 3), 2),
				Pair.of(new WaitTask(30, 60), 1)
			)
		);
	}

	public static void updateActivities(WildfireEntity wildfire) {
		wildfire.getBrain().resetPossibleActivities(ImmutableList.of(
			Activity.FIGHT,
			Activity.AVOID,
			Activity.IDLE
		));
	}

	public static void setBarrageAttackCooldown(WildfireEntity wildfire) {
		wildfire.getBrain().remember(FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get(), BARRAGE_ATTACK_COOLDOWN_PROVIDER.get(wildfire.getRandom()));
		onCooldown(wildfire);
	}

	public static void setShockwaveAttackCooldown(WildfireEntity wildfire) {
		wildfire.getBrain().remember(FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get(), SHOCKWAVE_ATTACK_COOLDOWN_PROVIDER.get(wildfire.getRandom()));
		onCooldown(wildfire);
	}

	public static void setSummonBlazeCooldown(WildfireEntity wildfire) {
		wildfire.getBrain().remember(FriendsAndFoesMemoryModuleTypes.WILDFIRE_SUMMON_BLAZE_COOLDOWN.get(), SUMMON_BLAZE_COOLDOWN_PROVIDER.get(wildfire.getRandom()));
		onCooldown(wildfire);
	}

	public static boolean shouldRunAway(WildfireEntity wildfire) {
		if (
			wildfire.getBrain().getOptionalRegisteredMemory(FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get()).isPresent()
			&& wildfire.getBrain().getOptionalRegisteredMemory(FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get()).isPresent()
			&& (
				wildfire.getBrain().getOptionalRegisteredMemory(FriendsAndFoesMemoryModuleTypes.WILDFIRE_SUMMON_BLAZE_COOLDOWN.get()).isPresent()
				|| wildfire.getSummonedBlazesCount() == WildfireEntity.MAXIMUM_SUMMONED_BLAZES_COUNT
			)
		) {
			return true;
		}

		return false;
	}

	public static void onCooldown(WildfireEntity wildfire) {
		if (shouldRunAway(wildfire) == false) {
			return;
		}

		LivingEntity attackTarget = wildfire.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

		if (attackTarget == null) {
			return;
		}

		runAwayFrom(wildfire, attackTarget);
	}

	public static void onAttacked(WildfireEntity wildfire, LivingEntity attacker) {
		setAttackTarget(wildfire, attacker);

		if (shouldRunAway(wildfire)) {
			runAwayFrom(wildfire, attacker);
		}
	}

	private static Optional<? extends LivingEntity> getTarget(WildfireEntity wildfire) {
		PlayerEntity nearestVisibleTargetablePlayer = wildfire.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER).orElse(
			wildfire.getWorld().getClosestPlayer(VALID_TARGET_PLAYER_PREDICATE, wildfire, wildfire.getX(), wildfire.getEyeY(), wildfire.getZ())
		);

		if (nearestVisibleTargetablePlayer == null) {
			return Optional.empty();
		}

		return Optional.of(nearestVisibleTargetablePlayer);
	}

	public static void setAttackTarget(WildfireEntity wildfire, LivingEntity target) {
		wildfire.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		wildfire.getBrain().remember(MemoryModuleType.ATTACK_TARGET, target, 200L);
	}

	private static void runAwayFrom(WildfireEntity wildfire, LivingEntity target) {
		wildfire.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
		wildfire.getBrain().forget(MemoryModuleType.WALK_TARGET);
		wildfire.getBrain().remember(MemoryModuleType.AVOID_TARGET, target, AVOID_MEMORY_DURATION.get(wildfire.getWorld().getRandom()));
	}

	static {
		SENSORS = List.of(
			SensorType.NEAREST_PLAYERS,
			SensorType.HURT_BY
		);
		MEMORY_MODULES = List.of(
			MemoryModuleType.PATH,
			MemoryModuleType.MOBS,
			MemoryModuleType.VISIBLE_MOBS,
			MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
			MemoryModuleType.ATTACK_TARGET,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.AVOID_TARGET,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_SUMMON_BLAZE_COOLDOWN.get(),
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get(),
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get()
		);
		SUMMON_BLAZE_COOLDOWN_PROVIDER = UniformIntProvider.create(600, 1200);
		BARRAGE_ATTACK_COOLDOWN_PROVIDER = UniformIntProvider.create(150, 300);
		SHOCKWAVE_ATTACK_COOLDOWN_PROVIDER = UniformIntProvider.create(150, 300);
		VALID_TARGET_PLAYER_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(WildfireEntity.GENERIC_FOLLOW_RANGE);
		AVOID_MEMORY_DURATION = TimeHelper.betweenSeconds(5, 20);
	}
}
