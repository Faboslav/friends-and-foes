package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.entity.BlazeEntityAccess;
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
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class WildfireSummonBlazeTask extends MultiTickTask<WildfireEntity>
{
	private LivingEntity attackTarget;
	private int summonedBlazesCount;

	private final static int SUMMON_BLAZES_DURATION = 20;
	public final static int MIN_BLAZES_TO_BE_SUMMONED = 1;
	private final static int MAX_BLAZES_TO_BE_SUMMONED = 2;

	public WildfireSummonBlazeTask() {
		super(ImmutableMap.of(
			MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_SUMMON_BLAZE_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), SUMMON_BLAZES_DURATION);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, WildfireEntity wildfire) {
		var attackTarget = wildfire.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

		if (
			attackTarget == null
			|| attackTarget.isAlive() == false
			|| (
				attackTarget instanceof PlayerEntity
				&& (
					attackTarget.isSpectator()
					|| ((PlayerEntity) attackTarget).isCreative()
				)
			)
			|| wildfire.getSummonedBlazesCount() == wildfire.MAXIMUM_SUMMONED_BLAZES_COUNT
		) {
			WildfireBrain.setSummonBlazeCooldown(wildfire);
			return false;
		}

		this.attackTarget = attackTarget;

		return true;
	}

	@Override
	protected void run(ServerWorld world, WildfireEntity wildfire, long time) {
		wildfire.getBrain().forget(MemoryModuleType.WALK_TARGET);
		wildfire.getNavigation().stop();

		LookTargetUtil.lookAt(wildfire, this.attackTarget);
		wildfire.getLookControl().lookAt(this.attackTarget);

		WildfireBrain.setAttackTarget(wildfire, this.attackTarget);

		this.summonedBlazesCount = 0;
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		return this.summonedBlazesCount == 0;
	}

	@Override
	protected void keepRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		LookTargetUtil.lookAt(wildfire, this.attackTarget);

		ServerWorld serverWorld = (ServerWorld) wildfire.getWorld();
		int blazesToBeSummoned = Math.max(0, RandomGenerator.generateInt(MIN_BLAZES_TO_BE_SUMMONED, MAX_BLAZES_TO_BE_SUMMONED) - wildfire.getSummonedBlazesCount());

		if (blazesToBeSummoned > 0) {
			wildfire.playSummonBlazeSound();
		}

		for (int i = 0; i < blazesToBeSummoned; i++) {
			BlockPos blockPos = wildfire.getBlockPos().add(
				-2 + wildfire.getRandom().nextInt(5),
				1,
				-2 + wildfire.getRandom().nextInt(5)
			);
			BlazeEntity blazeEntity = EntityType.BLAZE.create(serverWorld);
			blazeEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
			blazeEntity.setTarget(this.attackTarget);
			((BlazeEntityAccess) blazeEntity).setWildfire(wildfire);
			blazeEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, null, null);
			serverWorld.spawnEntityAndPassengers(blazeEntity);

			this.summonedBlazesCount++;
			wildfire.setSummonedBlazesCount(wildfire.getSummonedBlazesCount() + 1);
		}
	}

	@Override
	protected void finishRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		WildfireBrain.setSummonBlazeCooldown(wildfire);
	}
}
