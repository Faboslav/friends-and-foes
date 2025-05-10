package com.faboslav.friendsandfoes.common.entity.ai.brain;

import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSensorTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleState;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.List;
import java.util.Optional;

public final class BarnacleBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super BarnacleEntity>>> SENSORS;
	private static final TargetingConditions VALID_TARGET_PLAYER_PREDICATE;

	public BarnacleBrain() {
	}

	public static Brain<BarnacleEntity> create(Dynamic<?> dynamic) {
		Brain.Provider<CopperGolemEntity> profile = Brain.provider(MEMORY_MODULES, SENSORS);
		Brain<BarnacleEntity> brain = profile.makeBrain(dynamic);

		addCoreActivities(brain);
		addFightActivities(brain);
		addAvoidActivities(brain);
		addIdleActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();

		return brain;
	}

	private static void addCoreActivities(Brain<BarnacleEntity> brain) {
		brain.addActivity(
			Activity.CORE,
			0,
			ImmutableList.of(
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink()
			)
		);
	}

	private static void addFightActivities(Brain<BarnacleEntity> brain) {
		brain.addActivity(
			Activity.FIGHT,
			0,
			ImmutableList.of(
				new RangedApproach(0.6F),
				new MeleeAttack(20)
			),
			MemoryModuleType.ATTACK_TARGET
		);
	}

	private static void addIdleActivities(Brain<BarnacleEntity> brain) {
		brain.addActivity(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, new UpdateAttackTarget<>(barnacle -> getAttackTarget(barnacle))),
				Pair.of(1, makeRandomWanderTask())
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.AVOID_TARGET, MemoryModuleState.VALUE_ABSENT)
			)
		);
	}

	private static void addAvoidActivities(Brain<BarnacleEntity> brain) {
		brain.addActivity(
			Activity.AVOID,
			ImmutableList.of(
				Pair.of(0, GoToRememberedPosition.createEntityBased(MemoryModuleType.AVOID_TARGET, 0.8F, 16, false))
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.AVOID_TARGET, MemoryModuleState.VALUE_PRESENT)
			)
		);
	}

	public static void updateActivities(BarnacleEntity barnacle) {
		barnacle.getBrain().resetPossibleActivities(
			ImmutableList.of(
				Activity.FIGHT,
				Activity.AVOID,
				Activity.IDLE
			)
		);
	}

	public static void updateMemories(BarnacleEntity barnacle) {
	}

	private static RandomTask<BarnacleEntity> makeRandomWanderTask() {
		return new RandomTask<>(
			ImmutableList.of(
				Pair.of(new AquaticStroll(0.6F), 2),
				Pair.of(new GoTowardsLookTarget(1.0F, 3), 2),
				Pair.of(new Wait(30, 60), 1)
			)
		);
	}

	public static void onAttacked(BarnacleEntity barnacle, LivingEntity attacker) {
		setAttackTarget(barnacle, attacker);
	}

	public static void setAttackTarget(BarnacleEntity barnacle, LivingEntity target) {
		barnacle.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		barnacle.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, target, 200L);
	}

	private static Optional<? extends LivingEntity> getAttackTarget(BarnacleEntity barnacle) {
		Optional<Player> nearestVisibleTargetablePlayer = barnacle.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
		if (nearestVisibleTargetablePlayer.isPresent()) {
			return nearestVisibleTargetablePlayer;
		}

		Player nearestPlayer = barnacle.level().getNearestPlayer(VALID_TARGET_PLAYER_PREDICATE, barnacle);
		if (nearestPlayer != null) {
			return Optional.of(nearestPlayer);
		}

		return barnacle.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
	}

	static {
		SENSORS = List.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS,
			SensorType.HURT_BY,
			FriendsAndFoesSensorTypes.BARNACLE_SPECIFIC_SENSOR.get(),
			FriendsAndFoesSensorTypes.BARNACLE_ATTACKABLE_SENSOR.get()
		);
		MEMORY_MODULES = List.of(
			MemoryModuleType.NEAREST_LIVING_ENTITIES,
			MemoryModuleType.PATH,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.AVOID_TARGET,
			MemoryModuleType.ATTACK_TARGET,
			MemoryModuleType.INTERACTION_TARGET,
			MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
			MemoryModuleType.NEAREST_ATTACKABLE,
			MemoryModuleType.NEAREST_PLAYERS,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			MemoryModuleType.HAS_HUNTING_COOLDOWN,
			MemoryModuleType.ATTACK_COOLING_DOWN
		);
		VALID_TARGET_PLAYER_PREDICATE = TargetingConditions.forCombat().range(WildfireEntity.GENERIC_FOLLOW_RANGE);
	}
}