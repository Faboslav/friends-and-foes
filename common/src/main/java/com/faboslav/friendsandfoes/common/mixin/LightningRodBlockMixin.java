package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.common.entity.pose.CopperGolemEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.util.CopperGolemBuildPatternPredicates;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(value = LightningRodBlock.class, priority = 10000)
public abstract class LightningRodBlockMixin extends LightningRodBlockBlockMixin
{
	@Nullable
	private BlockPattern friendsandfoes_copperGolemPattern;

	@Inject(method = "onBlockAdded", at = @At("HEAD"))
	private void friendsandfoes_customOnBlockAdded(
		BlockState state,
		World world,
		BlockPos pos,
		BlockState oldState,
		boolean notify,
		CallbackInfo ci
	) {
		if (!oldState.isOf(state.getBlock())) {
			this.friendsandfoes_tryToSpawnCopperGolem(
				world,
				pos
			);
		}
	}

	private void friendsandfoes_tryToSpawnCopperGolem(
		World world,
		BlockPos pos
	) {
		if (!FriendsAndFoes.getConfig().enableCopperGolem) {
			return;
		}

		BlockPattern.Result patternSearchResult = this.friendsandfoes_getCopperGolemPattern().searchAround(world, pos);

		if (patternSearchResult == null) {
			return;
		}

		BlockState lightningRodBlockState = patternSearchResult.translate(0, 0, 0).getBlockState();
		BlockState headBlockState = patternSearchResult.translate(0, 1, 0).getBlockState();
		BlockState bodyBlockState = patternSearchResult.translate(0, 2, 0).getBlockState();

		Oxidizable.OxidationLevel lightningRodOxidationLevel;

		if (lightningRodBlockState.isOf(FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get())) {
			lightningRodOxidationLevel = Oxidizable.OxidationLevel.UNAFFECTED;
		} else if (lightningRodBlockState.isOf(FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get())) {
			lightningRodOxidationLevel = Oxidizable.OxidationLevel.WEATHERED;
		} else if (lightningRodBlockState.isOf(FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get())) {
			lightningRodOxidationLevel = Oxidizable.OxidationLevel.EXPOSED;
		} else if (lightningRodBlockState.isOf(FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get())) {
			lightningRodOxidationLevel = Oxidizable.OxidationLevel.OXIDIZED;
		} else {
			lightningRodOxidationLevel = ((Oxidizable) lightningRodBlockState.getBlock()).getDegradationLevel();
		}

		Oxidizable.OxidationLevel bodyOxidationLevel;

		if (bodyBlockState.isOf(Blocks.WAXED_COPPER_BLOCK)) {
			bodyOxidationLevel = Oxidizable.OxidationLevel.UNAFFECTED;
		} else if (bodyBlockState.isOf(Blocks.WAXED_WEATHERED_COPPER)) {
			bodyOxidationLevel = Oxidizable.OxidationLevel.WEATHERED;
		} else if (bodyBlockState.isOf(Blocks.WAXED_EXPOSED_COPPER)) {
			bodyOxidationLevel = Oxidizable.OxidationLevel.EXPOSED;
		} else if (bodyBlockState.isOf(Blocks.WAXED_OXIDIZED_COPPER)) {
			bodyOxidationLevel = Oxidizable.OxidationLevel.OXIDIZED;
		} else {
			bodyOxidationLevel = ((OxidizableBlock) bodyBlockState.getBlock()).getDegradationLevel();
		}

		if (lightningRodOxidationLevel != bodyOxidationLevel) {
			return;
		}

		for (int i = 0; i < this.friendsandfoes_getCopperGolemPattern().getHeight(); ++i) {
			CachedBlockPosition cachedBlockPosition = patternSearchResult.translate(0, i, 0);
			world.setBlockState(
				cachedBlockPosition.getBlockPos(),
				Blocks.AIR.getDefaultState(),
				Block.NOTIFY_LISTENERS
			);
			world.syncWorldEvent(
				WorldEvents.BLOCK_BROKEN,
				cachedBlockPosition.getBlockPos(),
				Block.getRawIdFromState(cachedBlockPosition.getBlockState())
			);
		}

		BlockPos cachedBlockPosition = patternSearchResult.translate(0, 2, 0).getBlockPos();
		float copperGolemYaw = headBlockState.get(CarvedPumpkinBlock.FACING).asRotation();

		CopperGolemEntity copperGolem = FriendsAndFoesEntityTypes.COPPER_GOLEM.get().create(world);

		copperGolem.setPosition(
			(double) cachedBlockPosition.getX() + 0.5D,
			(double) cachedBlockPosition.getY() + 0.05D,
			(double) cachedBlockPosition.getZ() + 0.5D
		);
		copperGolem.setSpawnYaw(copperGolemYaw);
		copperGolem.setOxidationLevel(bodyOxidationLevel);

		if (lightningRodOxidationLevel == Oxidizable.OxidationLevel.OXIDIZED) {
			ArrayList<CopperGolemEntityPose> possiblePoses = new ArrayList<>()
			{{
				add(CopperGolemEntityPose.SPIN_HEAD);
				add(CopperGolemEntityPose.PRESS_BUTTON_UP);
				add(CopperGolemEntityPose.PRESS_BUTTON_DOWN);
			}};
			int randomPoseIndex = copperGolem.getRandom().nextInt(possiblePoses.size());
			CopperGolemEntityPose randomPose = possiblePoses.get(randomPoseIndex);
			copperGolem.setPose(randomPose);
			KeyframeAnimation keyframeAnimation = copperGolem.getKeyframeAnimationByPose();

			if (keyframeAnimation != null) {
				int keyFrameAnimationLengthInTicks = keyframeAnimation.getAnimationLengthInTicks();
				int randomKeyframeAnimationTick = copperGolem.getRandom().nextBetween(keyFrameAnimationLengthInTicks / 6, keyFrameAnimationLengthInTicks - (keyFrameAnimationLengthInTicks / 6));
				copperGolem.setKeyframeAnimationTicks(randomKeyframeAnimationTick);
			}
		} else {
			boolean isHeadBlockWaxed = this.friendsandfoes_isCopperBlockWaxed(headBlockState);
			boolean isBodyBlockWaxed = this.friendsandfoes_isCopperBlockWaxed(bodyBlockState);
			boolean isWaxed = isHeadBlockWaxed && isBodyBlockWaxed;
			copperGolem.setIsWaxed(isWaxed);
		}

		CopperGolemBrain.setSpinHeadCooldown(copperGolem);
		CopperGolemBrain.setPressButtonCooldown(copperGolem);

		world.spawnEntity(copperGolem);

		for (ServerPlayerEntity serverPlayerEntity : world.getNonSpectatingEntities(
			ServerPlayerEntity.class,
			copperGolem.getBoundingBox().expand(5.0D)
		)) {
			Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, copperGolem);
		}

		for (int j = 0; j < this.friendsandfoes_getCopperGolemPattern().getHeight(); ++j) {
			CachedBlockPosition cachedBlockPosition2 = patternSearchResult.translate(0, j, 0);
			world.updateNeighbors(cachedBlockPosition2.getBlockPos(), Blocks.AIR);
		}
	}

	private BlockPattern friendsandfoes_getCopperGolemPattern() {
		if (this.friendsandfoes_copperGolemPattern == null) {
			this.friendsandfoes_copperGolemPattern = BlockPatternBuilder.start()
				.aisle("|", "^", "#")
				.where('|', CachedBlockPosition.matchesBlockState(CopperGolemBuildPatternPredicates.IS_COPPER_GOLEM_LIGHTNING_ROD_PREDICATE))
				.where('^', CachedBlockPosition.matchesBlockState(CopperGolemBuildPatternPredicates.IS_GOLEM_HEAD_PREDICATE))
				.where('#', CachedBlockPosition.matchesBlockState(CopperGolemBuildPatternPredicates.IS_COPPER_GOLEM_BODY_PREDICATE))
				.build();
		}

		return this.friendsandfoes_copperGolemPattern;
	}

	private boolean friendsandfoes_isCopperBlockWaxed(
		BlockState blockState
	) {
		return blockState.isOf(Blocks.WAXED_COPPER_BLOCK)
			   || blockState.isOf(Blocks.WAXED_WEATHERED_COPPER)
			   || blockState.isOf(Blocks.WAXED_EXPOSED_COPPER)
			   || blockState.isOf(Blocks.WAXED_OXIDIZED_COPPER)
			   || blockState.isOf(FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get())
			   || blockState.isOf(FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get())
			   || blockState.isOf(FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get())
			   || blockState.isOf(FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get());
	}
}
