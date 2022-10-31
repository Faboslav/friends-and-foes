package com.faboslav.friendsandfoes.entity.ai.brain;

import com.faboslav.friendsandfoes.entity.WildfireEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.task.WildfireBarrageAttackTask;
import com.faboslav.friendsandfoes.entity.ai.brain.task.WildfireShockwaveAttackTask;
import com.faboslav.friendsandfoes.init.FriendsAndFoesActivities;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class WildfireBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super WildfireEntity>>> SENSORS;
	private static final UniformIntProvider barrageAttackCooldownProvider = UniformIntProvider.create(200, 300);
	private static final UniformIntProvider shockwaveAttackCooldownProvider = UniformIntProvider.create(200, 300);
	private static final TargetPredicate VALID_TARGET_PLAYER_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(WildfireEntity.GENERIC_FOLLOW_RANGE);

	public WildfireBrain() {
	}

	public static Brain<?> create(Brain<WildfireEntity> brain) {
		addCoreActivities(brain);
		addIdleActivities(brain);
		addAttackActivities(brain);

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
				new TemptationCooldownTask(FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get())
			));
	}

	private static void addIdleActivities(Brain<WildfireEntity> brain) {
		brain.setTaskList(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, new UpdateAttackTargetTask(wildfire -> getTarget((WildfireEntity) wildfire))),
				Pair.of(0, new RandomTask(
					ImmutableList.of(
						Pair.of(new StrollTask(1.0F), 2),
						Pair.of(new GoTowardsLookTarget(1.0F, 3), 2),
						Pair.of(new WaitTask(30, 60), 1)
					)
				))
			)
		);
	}

	private static void addAttackActivities(
		Brain<WildfireEntity> brain
	) {
		brain.setTaskList(
			FriendsAndFoesActivities.WILDFIRE_SHOCKWAVE_ATTACK.get(),
			ImmutableList.of(Pair.of(0, new WildfireShockwaveAttackTask())),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleState.VALUE_PRESENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT)
			)
		);
		brain.setTaskList(
			FriendsAndFoesActivities.WILDFIRE_BARRAGE_ATTACK.get(),
			ImmutableList.of(Pair.of(0, new WildfireBarrageAttackTask())),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT),
				Pair.of(FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT)
			)
		);
	}

	public static void updateActivities(WildfireEntity wildfire) {
		wildfire.getBrain().resetPossibleActivities(ImmutableList.of(
			FriendsAndFoesActivities.WILDFIRE_SHOCKWAVE_ATTACK.get(),
			FriendsAndFoesActivities.WILDFIRE_BARRAGE_ATTACK.get(),
			Activity.IDLE
		));
	}

	public static void setBarrageAttackCooldown(LivingEntity wildfire) {
		wildfire.getBrain().remember(FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get(), barrageAttackCooldownProvider.get(wildfire.getRandom()));
	}

	public static void setShockwaveAttackCooldown(LivingEntity wildfire) {
		wildfire.getBrain().remember(FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get(), shockwaveAttackCooldownProvider.get(wildfire.getRandom()));
	}

	public static void onAttacked(WildfireEntity wildfire, LivingEntity attacker) {
		setAttackTarget(wildfire, attacker);
	}

	private static Optional<? extends LivingEntity> getTarget(WildfireEntity wildfire) {
		PlayerEntity nearestVisibleTargetablePlayer = wildfire.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER).orElse(
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
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get(),
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get()
		);
	}
}
