package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.api.BlazeEntityAccess;
import com.faboslav.friendsandfoes.entity.WildfireEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.WildfireBrain;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class WildfireSummonBlazeTask extends Task<WildfireEntity>
{
	private int summonedBlazes;
	private LivingEntity attackTarget;

	private final static int SUMMON_BLAZES_DURATION = 20;
	private final static int MIN_BLAZES_TO_BE_SUMMONED = 1;
	private final static int MAX_BLAZES_TO_BE_SUMMONED = 2;

	public WildfireSummonBlazeTask() {
		super(ImmutableMap.of(
			MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_SUMMON_BLAZE_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), SUMMON_BLAZES_DURATION);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, WildfireEntity wildfire) {
		var attackTarget = wildfire.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

		if (
			attackTarget == null
			|| attackTarget.isAlive() == false
			|| wildfire.canTarget(attackTarget) == false
			|| wildfire.getSummonedBlazesCount() >= MAX_BLAZES_TO_BE_SUMMONED
		) {
			return false;
		}

		this.attackTarget = attackTarget;

		return true;
	}

	@Override
	protected void run(ServerWorld world, WildfireEntity wildfire, long time) {
		this.summonedBlazes = 0;
		wildfire.getNavigation().stop();
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		if (
			attackTarget.isAlive() == false
			|| wildfire.canTarget(attackTarget) == false
		) {
			attackTarget = wildfire.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER).orElse(null);
		}

		return attackTarget != null
			   && attackTarget.isAlive() != false
			   && wildfire.canTarget(attackTarget) != false
			   && wildfire.getSummonedBlazesCount() != MAX_BLAZES_TO_BE_SUMMONED
			   && summonedBlazes <= 0;
	}

	@Override
	protected void keepRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		ServerWorld serverWorld = (ServerWorld) wildfire.getWorld();
		int blazesToBeSummoned = Math.max(0, RandomGenerator.generateInt(MIN_BLAZES_TO_BE_SUMMONED, MAX_BLAZES_TO_BE_SUMMONED) - wildfire.getSummonedBlazesCount());

		for (int i = 0; i < blazesToBeSummoned; ++i) {
			BlockPos blockPos = wildfire.getBlockPos().add(-2 + wildfire.getRandom().nextInt(5), 1, -2 + wildfire.getRandom().nextInt(5));
			BlazeEntity blazeEntity = EntityType.BLAZE.create(serverWorld);
			blazeEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
			blazeEntity.setTarget(this.attackTarget);
			((BlazeEntityAccess) blazeEntity).setWildfire(wildfire);
			blazeEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, null, null);
			serverWorld.spawnEntityAndPassengers(blazeEntity);

			wildfire.setSummonedBlazesCount(wildfire.getSummonedBlazesCount() + 1);
			summonedBlazes++;
		}
	}

	@Override
	protected void finishRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		WildfireBrain.setSummonBlazeCooldown(wildfire);
	}
}
