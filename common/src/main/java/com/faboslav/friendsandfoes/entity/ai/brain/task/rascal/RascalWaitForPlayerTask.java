package com.faboslav.friendsandfoes.entity.ai.brain.task.rascal;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.RascalEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.RascalBrain;
import com.faboslav.friendsandfoes.entity.pose.RascalEntityPose;
import com.faboslav.friendsandfoes.init.FriendsAndFoesCriteria;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
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
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.StructureTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.StructureAccessor;

public final class RascalWaitForPlayerTask extends MultiTickTask<RascalEntity>
{
	private final static int NOD_DURATION = 90;
	public final static float NOD_RANGE = 4F;

	private int nodTicks;
	private LivingEntity nearestTarget;

	public RascalWaitForPlayerTask() {
		super(ImmutableMap.of(
			MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleState.REGISTERED,
			MemoryModuleType.INTERACTION_TARGET, MemoryModuleState.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.RASCAL_NOD_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), NOD_DURATION);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, RascalEntity rascal) {
		if (rascal.hasCustomName()) {
			return false;
		}

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
			return false;
		}

		this.nearestTarget = nearestTarget;

		return true;
	}

	@Override
	protected void run(ServerWorld world, RascalEntity rascal, long time) {
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
		rascal.disableAmbientSounds();
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, RascalEntity rascal, long time) {
		return this.nodTicks <= NOD_DURATION;
	}

	@Override
	protected void keepRunning(ServerWorld world, RascalEntity rascal, long time) {
		if (nodTicks == 20) {
			rascal.startNodAnimation();
			rascal.getLookControl().lookAt(this.nearestTarget);
		}

		if (nodTicks == 40 && rascal.shouldGiveReward()) {
			rascal.startGiveRewardAnimation();
		}

		if (nodTicks == 62 && rascal.shouldGiveReward()) {
			Vec3d targetPos = nearestTarget.getPos().add(0.0, 1.0, 0.0);
			LootTable rascalGoodItemsLootTable = world.getServer().getReloadableRegistries().getLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, FriendsAndFoes.makeID("rewards/rascal_good_reward")));
			LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(world)
				.add(LootContextParameters.ORIGIN, targetPos)
				.add(LootContextParameters.THIS_ENTITY, this.nearestTarget)
				.build(LootContextTypes.GIFT);
			ObjectArrayList<ItemStack> rascalGoodRewards = rascalGoodItemsLootTable.generateLoot(lootContextParameterSet);


			for (ItemStack rascalReward : rascalGoodRewards) {
				ItemStack bundleItemStack = Items.BUNDLE.getDefaultStack();
				BundleContentsComponent bundleContentsComponent = bundleItemStack.get(DataComponentTypes.BUNDLE_CONTENTS);

				if (bundleContentsComponent == null) {
					break;
				}

				BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(bundleContentsComponent);
				builder.add(rascalReward);
				bundleItemStack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());
				LookTargetUtil.give(rascal, bundleItemStack, nearestTarget.getPos().add(0.0, 1.0, 0.0));

				FriendsAndFoesCriteria.COMPLETE_HIDE_AND_SEEK_GAME.get().trigger((ServerPlayerEntity) this.nearestTarget, rascal, bundleItemStack);
			}
		}

		this.nodTicks++;
	}

	@Override
	protected void finishRunning(ServerWorld world, RascalEntity rascal, long time) {
		if (rascal.hasCustomName()) {
			RascalBrain.setNodCooldown(rascal);
			return;
		}

		rascal.spawnCloudParticles();
		rascal.playDisappearSound();

		if (rascal.shouldGiveReward()) {
			rascal.discard();
			return;
		}

		rascal.setPose(RascalEntityPose.IDLE);
		rascal.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, RascalBrain.NOD_COOLDOWN * 20));
		this.tryToTeleport(world, rascal);
		RascalBrain.setNodCooldown(rascal);
		rascal.enableAmbientSounds();
	}

	private void tryToTeleport(ServerWorld world, RascalEntity rascal) {
		StructureAccessor structureAccessor = world.getStructureAccessor();

		for (int i = 0; i < 64; ++i) {
			double x = rascal.getX() + (rascal.getRandom().nextDouble() - 0.5) * 16.0;
			double y = MathHelper.clamp(rascal.getY() + (double) (rascal.getRandom().nextInt(8) - 4), world.getBottomY(), world.getBottomY() + world.getLogicalHeight() - 1);
			double z = rascal.getZ() + (rascal.getRandom().nextDouble() - 0.5) * 16.0;

			if (structureAccessor.getStructureContaining(
				new BlockPos((int) x, (int) y, (int) z),
				StructureTags.MINESHAFT
			).hasChildren() == false) {
				continue;
			}

			if (rascal.hasVehicle()) {
				rascal.stopRiding();
			}

			boolean teleportResult = rascal.teleport(x, y, z, false);

			if (teleportResult && rascal.distanceTo(this.nearestTarget) > 10.0) {
				return;
			}
		}
	}
}