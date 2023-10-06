package com.faboslav.friendsandfoes.entity.ai.brain.task.rascal;

import com.faboslav.friendsandfoes.entity.RascalEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.RascalBrain;
import com.faboslav.friendsandfoes.init.FriendsAndFoesCriteria;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public final class RascalWaitForPlayerTask extends MultiTickTask<RascalEntity>
{
	private final static int NOD_DURATION = 60;
	public final static float NOD_RANGE = 3.0F;

	private int nodTicks;
	private LivingEntity nearestTarget;

	public RascalWaitForPlayerTask() {
		super(ImmutableMap.of(
			MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleState.REGISTERED,
			MemoryModuleType.NEAREST_PLAYERS, MemoryModuleState.REGISTERED,
			FriendsAndFoesMemoryModuleTypes.RASCAL_NOD_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), NOD_DURATION);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, RascalEntity rascal) {
		LivingEntity nearestTarget = rascal.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER).orElse(null);

		if (nearestTarget == null) {
			nearestTarget = rascal.getBrain().getOptionalRegisteredMemory(MemoryModuleType.INTERACTION_TARGET).orElse(null);
		}

		if (
			nearestTarget == null
			|| rascal.distanceTo(nearestTarget) > NOD_RANGE
			|| nearestTarget.isAlive() == false
			|| (
				nearestTarget instanceof PlayerEntity
				&& (
					nearestTarget.isSpectator()
					|| ((PlayerEntity) nearestTarget).isCreative()
				)
			)
		) {

			if (nearestTarget == null) {
			}
			return false;
		}

		this.nearestTarget = nearestTarget;

		return true;
	}

	@Override
	protected void run(ServerWorld world, RascalEntity rascal, long time) {
		FriendsAndFoesCriteria.COMPLETE_HIDE_AND_SEEK_GAME.trigger((ServerPlayerEntity) this.nearestTarget, rascal);
		rascal.getBrain().forget(MemoryModuleType.WALK_TARGET);
		rascal.getNavigation().setSpeed(0);
		rascal.getNavigation().stop();
		rascal.getNavigation().tick();
		rascal.getMoveControl().tick();

		rascal.setMovementSpeed(0.0F);
		rascal.prevHorizontalSpeed = 0.0F;
		rascal.horizontalSpeed = 0.0F;
		rascal.sidewaysSpeed = 0.0F;
		rascal.upwardSpeed = 0.0F;

		LookTargetUtil.lookAt(rascal, this.nearestTarget);
		rascal.getLookControl().lookAt(this.nearestTarget);
		rascal.getLookControl().tick();

		this.nodTicks = 0;
		rascal.addToCaughtCount();
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, RascalEntity rascal, long time) {
		return this.nodTicks <= NOD_DURATION;
	}

	@Override
	protected void keepRunning(ServerWorld world, RascalEntity rascal, long time) {
		//rascal.getLookControl().lookAt(this.nearestTarget);
		rascal.playNodSound();

		if (nodTicks == 30) {
			if (rascal.shouldGiveReward()) {
				rascal.playRewardSound();
				Random random = rascal.getRandom();
				ItemStack itemStack = Items.IRON_PICKAXE.getDefaultStack();
				ItemStack enchantedItemStack = EnchantmentHelper.enchant(
					random,
					itemStack,
					rascal.getRandom().nextBetween(15, 19),
					true
				);

				LookTargetUtil.give(rascal, enchantedItemStack, nearestTarget.getPos().add(0.0, 1.0, 0.0));
			}
		}

		this.nodTicks++;
	}

	@Override
	protected void finishRunning(ServerWorld world, RascalEntity rascal, long time) {
		if (rascal.shouldGiveReward() && rascal.hasCustomName() == false) {
			rascal.spawnCloudParticles();
			rascal.playAmbientSound();
			rascal.discard();
		}

		rascal.spawnCloudParticles();
		rascal.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 400));
		RascalBrain.setNodCooldown(rascal);
	}
}
