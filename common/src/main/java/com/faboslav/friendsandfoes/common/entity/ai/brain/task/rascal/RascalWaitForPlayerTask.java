package com.faboslav.friendsandfoes.common.entity.ai.brain.task.rascal;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.RascalEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.RascalBrain;
import com.faboslav.friendsandfoes.common.entity.pose.RascalEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesCriterias;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.util.MovementUtil;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public final class RascalWaitForPlayerTask extends Behavior<RascalEntity>
{
	private final static int NOD_DURATION = 90;
	public final static float NOD_RANGE = 5.0F;

	private int nodTicks;
	private LivingEntity nearestTarget;

	public RascalWaitForPlayerTask() {
		super(ImmutableMap.of(
			MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryStatus.REGISTERED,
			MemoryModuleType.INTERACTION_TARGET, MemoryStatus.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.RASCAL_NOD_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT
		), NOD_DURATION);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, RascalEntity rascal) {
		if (rascal.hasCustomName()) {
			return false;
		}

		LivingEntity nearestTarget = rascal.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER).orElse(null);

		if (nearestTarget == null) {
			nearestTarget = rascal.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).orElse(null);
		}

		if (
			nearestTarget == null
			|| rascal.distanceTo(nearestTarget) > NOD_RANGE
			|| !nearestTarget.isAlive()
			|| (
				nearestTarget instanceof Player
				&& (
					nearestTarget.isSpectator()
					|| ((Player) nearestTarget).isCreative()
				)
			)
		) {
			return false;
		}

		this.nearestTarget = nearestTarget;

		return true;
	}

	@Override
	protected void start(ServerLevel world, RascalEntity rascal, long time) {
		MovementUtil.stopMovement(rascal);
		BehaviorUtils.lookAtEntity(rascal, this.nearestTarget);
		rascal.getLookControl().setLookAt(this.nearestTarget);
		rascal.getLookControl().tick();

		this.nodTicks = 0;
		rascal.addToCaughtCount();
		rascal.disableAmbientSounds();
	}

	@Override
	protected boolean canStillUse(ServerLevel world, RascalEntity rascal, long time) {
		return this.nodTicks <= NOD_DURATION;
	}

	@Override
	protected void tick(ServerLevel world, RascalEntity rascal, long time) {
		if (nodTicks == 20) {
			rascal.startNodAnimation();
			rascal.getLookControl().setLookAt(this.nearestTarget);
		}

		if (nodTicks == 40 && rascal.shouldGiveReward()) {
			rascal.startGiveRewardAnimation();
		}

		if (nodTicks == 62 && rascal.shouldGiveReward()) {
			Vec3 targetPos = nearestTarget.position().add(0.0, 1.0, 0.0);
			LootTable rascalGoodItemsLootTable = world.getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, FriendsAndFoes.makeID("rewards/rascal_good_reward")));
			LootParams lootContextParameterSet = new LootParams.Builder(world)
				.withParameter(LootContextParams.ORIGIN, targetPos)
				.withParameter(LootContextParams.THIS_ENTITY, this.nearestTarget)
				.create(LootContextParamSets.GIFT);
			ObjectArrayList<ItemStack> rascalGoodRewards = rascalGoodItemsLootTable.getRandomItems(lootContextParameterSet);


			for (ItemStack rascalReward : rascalGoodRewards) {
				ItemStack bundleItemStack = Items.BUNDLE.getDefaultInstance();
				BundleContents bundleContentsComponent = bundleItemStack.get(DataComponents.BUNDLE_CONTENTS);

				if (bundleContentsComponent == null) {
					break;
				}

				BundleContents.Mutable builder = new BundleContents.Mutable(bundleContentsComponent);
				builder.tryInsert(rascalReward);
				bundleItemStack.set(DataComponents.BUNDLE_CONTENTS, builder.toImmutable());
				BehaviorUtils.throwItem(rascal, bundleItemStack, nearestTarget.position().add(0.0, 1.0, 0.0));

				FriendsAndFoesCriterias.COMPLETE_HIDE_AND_SEEK_GAME.get().trigger((ServerPlayer) this.nearestTarget, rascal, bundleItemStack);
			}
		}

		this.nodTicks++;
	}

	@Override
	protected void stop(ServerLevel world, RascalEntity rascal, long time) {
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
		rascal.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, RascalBrain.NOD_COOLDOWN * 20));
		this.tryToTeleport(world, rascal);
		RascalBrain.setNodCooldown(rascal);
		rascal.enableAmbientSounds();
	}

	private void tryToTeleport(ServerLevel world, RascalEntity rascal) {
		StructureManager structureAccessor = world.structureManager();

		for (int i = 0; i < 64; ++i) {
			double x = rascal.getX() + (rascal.getRandom().nextDouble() - 0.5) * 16.0;
			double y = Mth.clamp(rascal.getY() + (double) (rascal.getRandom().nextInt(8) - 4), world.getMinBuildHeight(), world.getMinBuildHeight() + world.getLogicalHeight() - 1);
			double z = rascal.getZ() + (rascal.getRandom().nextDouble() - 0.5) * 16.0;

			if (!structureAccessor.getStructureWithPieceAt(
				new BlockPos((int) x, (int) y, (int) z),
				StructureTags.MINESHAFT
			).isValid()) {
				continue;
			}

			if (rascal.isPassenger()) {
				rascal.stopRiding();
			}

			boolean teleportResult = rascal.randomTeleport(x, y, z, false);

			if (teleportResult && rascal.distanceTo(this.nearestTarget) > 10.0) {
				return;
			}
		}
	}
}