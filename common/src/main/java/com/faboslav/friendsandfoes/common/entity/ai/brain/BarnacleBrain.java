package com.faboslav.friendsandfoes.common.entity.ai.brain;

import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.SetSwimTargetAwayFrom;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.barnacle.BarnacleHideTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.barnacle.BarnacleLocateHidingSpotTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.barnacle.BarnacleTentacleAttackTask;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.barnacle.BarnacleTravelToHidingSpotTask;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSensorTypes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
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
*///?}

@SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
public final class BarnacleBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super BarnacleEntity>>> SENSORS;
	public static final Brain.Provider<BarnacleEntity> BRAIN_PROVIDER;
	private static final UniformInt TENTACLE_ATTACK_COOLDOWN;
	private static final UniformInt TENTACLE_ATTACK_RETRY_COOLDOWN;
	private static final UniformInt HIDING_SPOT_LOCATING_COOLDOWN;

	public BarnacleBrain() {
	}

	//? if >= 26.1 {
	public static Brain<BarnacleEntity> create(BarnacleEntity barnacle, final Brain.Packed packedBrain) {
		return BRAIN_PROVIDER.makeBrain(barnacle, packedBrain);
	}
	//?} else {
	/*public static Brain<BarnacleEntity> create(Dynamic<?> dynamic) {
		Brain<BarnacleEntity> brain = BRAIN_PROVIDER.makeBrain(dynamic);

		addActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();

		return brain;
	}
	*///?}

	//? if >= 26.1 {
	private static List<ActivityData<BarnacleEntity>> addActivities(BarnacleEntity barnacle)
	//?} else {
	/*private static void addActivities(Brain<BarnacleEntity> brain)
	*///?}
	{
		//? if >= 26.1 {
		return List.of(
			addCoreActivities(),
			addFightActivities(),
			addAvoidActivities(),
			addHideActivities(),
			addIdleActivities()
		);
		//?} else {
		/*addCoreActivities(brain);
		addFightActivities(brain);
		addAvoidActivities(brain);
		addHideActivities(brain);
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
				new MoveToTargetSink(),
				new CountDownCooldownTicks(FriendsAndFoesMemoryModuleTypes.BARNACLE_TENTACLE_ATTACK_COOLDOWN.get()),
				new CountDownCooldownTicks(FriendsAndFoesMemoryModuleTypes.BARNACLE_HIDING_SPOT_LOCATING_COOLDOWN.get())
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
				Pair.of(0, makeStartAttackingTask()),
				Pair.of(1, new BarnacleLocateHidingSpotTask()),
				Pair.of(2, makeRandomWanderTask())
			)
		);
	}

	//? if >= 26.1 {
	private static ActivityData<BarnacleEntity> addHideActivities()
	//?} else {
	/*private static void addHideActivities(Brain<BarnacleEntity> brain)
	*///?}
	{
		//? if >= 26.1 {
		return ActivityData.create(
		//?} else {
		/*brain.addActivityWithConditions(
		*///?}
			FriendsAndFoesActivities.BARNACLE_HIDE.get(),
			ImmutableList.of(
				Pair.of(0, makeStartAttackingTask()),
				Pair.of(1, new BarnacleTravelToHidingSpotTask()),
				Pair.of(2, new BarnacleHideTask())
			),
			ImmutableSet.of(
				Pair.of(FriendsAndFoesMemoryModuleTypes.BARNACLE_HIDING_SPOT_POS.get(), MemoryStatus.VALUE_PRESENT)
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
		/*brain.addActivityAndRemoveMemoriesWhenStopped(
		*///?}
			Activity.FIGHT,
			ImmutableList.of(
				Pair.of(0, makeStopAttackingTask()),
				Pair.of(1, new BarnacleTentacleAttackTask()),
				Pair.of(2, SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F)),
				Pair.of(3, MeleeAttack.create(20))
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)
			),
			ImmutableSet.of(
				MemoryModuleType.ATTACK_TARGET
			)
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
				SetSwimTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.4F, 16, true),
				makeRandomWanderTask()
			),
			MemoryModuleType.AVOID_TARGET
		);
	}

	public static void updateActivities(BarnacleEntity barnacle) {
		barnacle.getBrain().setActiveActivityToFirstValid(ImmutableList.of(
			Activity.FIGHT,
			Activity.AVOID,
			FriendsAndFoesActivities.BARNACLE_HIDE.get(),
			Activity.IDLE
		));
	}

	private static RunOne<BarnacleEntity> makeRandomWanderTask() {
		return new RunOne(
			ImmutableList.of(
				Pair.of(RandomStroll.swim(0.5F), 2),
				Pair.of(SetWalkTargetFromLookTarget.create(0.5F, 3), 1),
				Pair.of(new DoNothing(60, 120), 2)
			)
		);
	}

	private static BehaviorControl<BarnacleEntity> makeStartAttackingTask() {
		//? if >= 1.21.4 {
		return StartAttacking.create(BarnacleBrain::findNearestValidAttackTarget);
		//?} else {
		/*return StartAttacking.create(barnacle -> findNearestValidAttackTarget((ServerLevel) barnacle.level(), barnacle));
		*///?}
	}

	private static BehaviorControl<BarnacleEntity> makeStopAttackingTask() {
		return StopAttackingIfTargetInvalid.create(BarnacleBrain::shouldStopAttacking, BarnacleBrain::onStopAttacking, true);
	}

	private static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel world, BarnacleEntity barnacle) {
		Brain<BarnacleEntity> brain = barnacle.getBrain();
		Optional<LivingEntity> angryAt = BehaviorUtils.getLivingEntityFromUUIDMemory(barnacle, MemoryModuleType.ANGRY_AT)
			//? if >= 1.21.4 {
			.filter(entity -> Sensor.isEntityAttackableIgnoringLineOfSight(world, barnacle, entity))
			//?} else {
			/*.filter(entity -> Sensor.isEntityAttackableIgnoringLineOfSight(barnacle, entity))
			*///?}
			.filter(BarnacleEntity::isInWaterOrBoat);

		if (angryAt.isPresent()) {
			return angryAt;
		}

		Optional<Player> nearestVisibleAttackablePlayer = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);

		if (nearestVisibleAttackablePlayer.isPresent() && barnacle.isValidTentacleTarget(nearestVisibleAttackablePlayer.get())) {
			return nearestVisibleAttackablePlayer;
		}

		return brain.getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
	}

	//? if >= 1.21.4 {
	private static boolean shouldStopAttacking(ServerLevel world, LivingEntity target)
	//?} else {
	/*private static boolean shouldStopAttacking(LivingEntity target)
	*///?}
	{
		return !BarnacleEntity.isInWaterOrBoat(target);
	}

	//? if >= 1.21.4 {
	private static void onStopAttacking(ServerLevel world, BarnacleEntity barnacle, LivingEntity target)
	//?} else {
	/*private static void onStopAttacking(BarnacleEntity barnacle, LivingEntity target)
	*///?}
	{
		if (isPrey(target)) {
			setHuntingCooldown(barnacle);
		}
	}

	public static boolean isPrey(LivingEntity entity) {
		return VersionedEntity.isEntityType(entity, FriendsAndFoesTags.BARNACLE_PREY);
	}

	public static void onHurt(BarnacleEntity barnacle) {
		if (barnacle.hasTentacleTarget()) {
			setTentacleAttackCooldown(barnacle);
		}
	}

	public static void setAngerTarget(ServerLevel world, BarnacleEntity barnacle, LivingEntity target) {
		//? if >= 1.21.4 {
		if (Sensor.isEntityAttackableIgnoringLineOfSight(world, barnacle, target)) {
		//?} else {
		/*if (Sensor.isEntityAttackableIgnoringLineOfSight(barnacle, target)) {
		*///?}
			barnacle.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
			barnacle.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, target.getUUID(), 400L);
		}
	}

	public static void setTentacleAttackCooldown(BarnacleEntity barnacle) {
		barnacle.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.BARNACLE_TENTACLE_ATTACK_COOLDOWN.get(), TENTACLE_ATTACK_COOLDOWN.sample(barnacle.getRandom()));
	}

	public static void setTentacleAttackRetryCooldown(BarnacleEntity barnacle) {
		barnacle.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.BARNACLE_TENTACLE_ATTACK_COOLDOWN.get(), TENTACLE_ATTACK_RETRY_COOLDOWN.sample(barnacle.getRandom()));
	}

	public static void setHuntingCooldown(BarnacleEntity barnacle) {
		barnacle.getBrain().setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
	}

	public static void setHidingSpotLocatingCooldown(BarnacleEntity barnacle) {
		barnacle.getBrain().setMemory(FriendsAndFoesMemoryModuleTypes.BARNACLE_HIDING_SPOT_LOCATING_COOLDOWN.get(), HIDING_SPOT_LOCATING_COOLDOWN.sample(barnacle.getRandom()));
	}

	static {
		SENSORS = List.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS,
			SensorType.HURT_BY,
			FriendsAndFoesSensorTypes.BARNACLE_ATTACKABLES_SENSOR.get(),
			FriendsAndFoesSensorTypes.BARNACLE_SPECIFIC_SENSOR.get()
		);
		MEMORY_MODULES = List.of(
			MemoryModuleType.NEAREST_LIVING_ENTITIES,
			MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
			MemoryModuleType.NEAREST_PLAYERS,
			MemoryModuleType.NEAREST_VISIBLE_PLAYER,
			MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
			MemoryModuleType.NEAREST_ATTACKABLE,
			MemoryModuleType.HURT_BY,
			MemoryModuleType.HURT_BY_ENTITY,
			MemoryModuleType.ANGRY_AT,
			MemoryModuleType.PATH,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			MemoryModuleType.AVOID_TARGET,
			MemoryModuleType.ATTACK_TARGET,
			MemoryModuleType.ATTACK_COOLING_DOWN,
			MemoryModuleType.HAS_HUNTING_COOLDOWN,
			FriendsAndFoesMemoryModuleTypes.BARNACLE_TENTACLE_ATTACK_COOLDOWN.get(),
			FriendsAndFoesMemoryModuleTypes.BARNACLE_HIDING_SPOT_POS.get(),
			FriendsAndFoesMemoryModuleTypes.BARNACLE_HIDING_SPOT_LOCATING_COOLDOWN.get()
		);
		BRAIN_PROVIDER = Brain.provider(
			MEMORY_MODULES,
			SENSORS
			//? if >= 26.1 {
			, BarnacleBrain::addActivities
			//?}
		);
		TENTACLE_ATTACK_COOLDOWN = TimeUtil.rangeOfSeconds(20, 40);
		TENTACLE_ATTACK_RETRY_COOLDOWN = TimeUtil.rangeOfSeconds(3, 6);
		HIDING_SPOT_LOCATING_COOLDOWN = TimeUtil.rangeOfSeconds(10, 60);
	}
}
