package com.faboslav.friendsandfoes.common.entity.ai.brain;

import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import java.util.List;
import java.util.Optional;

//? if >= 26.1 {
import net.minecraft.world.entity.ai.ActivityData;
//?} else {
/*import com.mojang.serialization.Dynamic;
import com.google.common.collect.ImmutableSet;
*///?}

public final class BarnacleBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super BarnacleEntity>>> SENSORS;
	public static final Brain.Provider<BarnacleEntity> BRAIN_PROVIDER;
	private static final UniformInt AVOID_MEMORY_DURATION;

	public BarnacleBrain() {
	}

	//? if >= 26.1 {
	public static Brain<BarnacleEntity> create(BarnacleEntity barnacle, final Brain.Packed packedBrain) {
		return BRAIN_PROVIDER.makeBrain(barnacle, packedBrain);
	}
	//?} else {
	/*public static Brain<BarnacleEntity> create(Dynamic<?> dynamic) {
		Brain<BarnacleEntity> brain = BRAIN_PROVIDER.makeBrain(dynamic);

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
	protected static List<ActivityData<BarnacleEntity>> addActivities(BarnacleEntity barnacle)
	//?} else {
	/*protected static void addActivities(Brain<BarnacleEntity> brain)
	 *///?}
	{
		//? if >= 26.1 {
		return List.of(
			addCoreActivities(),
			addFightActivities(),
			addAvoidActivities(),
			addIdleActivities()

		);
		//?} else {
		/*addCoreActivities(brain);
		addFightActivities(brain);
		addAvoidActivities(brain);
		addIdleActivities(brain);
		*///?}
	}

	//? if >= 26.1 {
	private static ActivityData<BarnacleEntity> addCoreActivities()
	//?} else {
	/*private static void addCoreActivities(Brain<BarnacleEntity> brain)
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
				new MoveToTargetSink()
			)
		);
	}

	//? if >= 26.1 {
	private static ActivityData<BarnacleEntity> addIdleActivities()
	//?} else {
	/*private static void addIdleActivities(Brain<BarnacleEntity> brain)
	 *///?}
	{
		//? if >= 26.1 {
		return ActivityData.create(
			//?} else {
			/*brain.addActivity(
			*///?}
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, StartAttacking.create((/*? >=1.21.3 {*/serverLevel, /*?}*/barnacle) -> true, BarnacleBrain::getTarget)),
				Pair.of(1, makeRandomWanderTask())
			)
		);
	}

	//? if >= 26.1 {
	private static ActivityData<BarnacleEntity> addFightActivities()
	//?} else {
	/*private static void addFightActivities(Brain<BarnacleEntity> brain)
	 *///?}
	{
		//? if >= 26.1 {
		return ActivityData.create(
			//?} else {
			/*brain.addActivityAndRemoveMemoryWhenStopped(
			 *///?}
			Activity.FIGHT,
			10,
			ImmutableList.of(
				SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(0.6F),
				MeleeAttack.create(20)
			),
			MemoryModuleType.ATTACK_TARGET
		);
	}

	//? if >= 26.1 {
	private static ActivityData<BarnacleEntity> addAvoidActivities()
	//?} else {
	/*private static void addAvoidActivities(Brain<BarnacleEntity> brain)
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
				SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.4F, 16, true),
				makeRandomWanderTask()
			),
			MemoryModuleType.AVOID_TARGET
		);
	}

	public static void updateActivities(BarnacleEntity barnacle) {
		barnacle.getBrain().setActiveActivityToFirstValid(ImmutableList.of(
			Activity.FIGHT,
			Activity.AVOID,
			Activity.IDLE
		));
	}

	public static void updateMemories(BarnacleEntity barnacle) {
	}

	private static RunOne<BarnacleEntity> makeRandomWanderTask() {
		return new RunOne(
			ImmutableList.of(
				Pair.of(RandomStroll.stroll(0.6F), 2),
				Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2),
				Pair.of(new DoNothing(30, 60), 1)
			)
		);
	}

	private static Optional<? extends LivingEntity> getTarget(/*? >=1.21.3 {*/
		ServerLevel level, /*?}*/
		BarnacleEntity barnacle) {
		Player nearestVisibleTargetablePlayer = barnacle.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER).orElse(
			barnacle.level().getNearestPlayer(barnacle, BarnacleEntity.GENERIC_FOLLOW_RANGE)
		);

		if (nearestVisibleTargetablePlayer == null) {
			return Optional.empty();
		}

		return Optional.of(nearestVisibleTargetablePlayer);
	}

	public static boolean shouldRunAway(BarnacleEntity barnacle) {
		return false;
	}

	public static void setAttackTarget(BarnacleEntity barnacle, LivingEntity target) {
		barnacle.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		barnacle.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, target, 200L);
		barnacle.setLeashedTo(target, true);
	}

	private static void runAwayFrom(BarnacleEntity barnacle, LivingEntity target) {
		barnacle.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
		barnacle.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		barnacle.getBrain().setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, target, AVOID_MEMORY_DURATION.sample(barnacle.level().getRandom()));
	}

	public static void onAttacked(BarnacleEntity barnacle, LivingEntity attacker) {
		setAttackTarget(barnacle, attacker);

		if (shouldRunAway(barnacle)) {
			runAwayFrom(barnacle, attacker);
		}
	}

	static {
		SENSORS = List.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS,
			SensorType.HURT_BY
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
		BRAIN_PROVIDER = Brain.provider(
			MEMORY_MODULES,
			SENSORS
			//? if >= 26.1 {
			, BarnacleBrain::addActivities
			//?}
		);
		AVOID_MEMORY_DURATION = TimeUtil.rangeOfSeconds(5, 20);
	}
}