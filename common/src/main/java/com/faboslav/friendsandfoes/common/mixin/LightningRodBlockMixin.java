package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.block.OnUseOxidizable;
import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.CopperGolemEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.util.CopperGolemBuildPatternPredicates;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopperFullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(value = LightningRodBlock.class, priority = 1001)
public abstract class LightningRodBlockMixin extends LightningRodBlockBlockMixin
{
	@Nullable
	private BlockPattern friendsandfoes_copperGolemPattern;

	@Inject(method = "onPlace", at = @At("HEAD"))
	private void friendsandfoes_onBlockAdded(
		BlockState state,
		Level world,
		BlockPos pos,
		BlockState oldState,
		boolean notify,
		CallbackInfo ci
	) {
		if (!oldState.is(state.getBlock())) {
			this.friendsandfoes_tryToSpawnCopperGolem(
				world,
				pos
			);
		}
	}

	private void friendsandfoes_tryToSpawnCopperGolem(
		Level world,
		BlockPos pos
	) {
		if (!FriendsAndFoes.getConfig().enableCopperGolem) {
			return;
		}

		BlockPattern.BlockPatternMatch patternSearchResult = this.friendsandfoes_getCopperGolemPattern().find(world, pos);

		if (patternSearchResult == null) {
			return;
		}

		BlockState lightningRodBlockState = patternSearchResult.getBlock(0, 0, 0).getState();
		BlockState headBlockState = patternSearchResult.getBlock(0, 1, 0).getState();
		BlockState bodyBlockState = patternSearchResult.getBlock(0, 2, 0).getState();

		WeatheringCopper.WeatherState lightningRodOxidationLevel;

		if (lightningRodBlockState.is(FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get())) {
			lightningRodOxidationLevel = WeatheringCopper.WeatherState.UNAFFECTED;
		} else if (lightningRodBlockState.is(FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get())) {
			lightningRodOxidationLevel = WeatheringCopper.WeatherState.WEATHERED;
		} else if (lightningRodBlockState.is(FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get())) {
			lightningRodOxidationLevel = WeatheringCopper.WeatherState.EXPOSED;
		} else if (lightningRodBlockState.is(FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get())) {
			lightningRodOxidationLevel = WeatheringCopper.WeatherState.OXIDIZED;
		} else {
			lightningRodOxidationLevel = ((WeatheringCopper) lightningRodBlockState.getBlock()).getAge();
		}

		WeatheringCopper.WeatherState bodyOxidationLevel;

		if (bodyBlockState.is(Blocks.WAXED_COPPER_BLOCK)) {
			bodyOxidationLevel = WeatheringCopper.WeatherState.UNAFFECTED;
		} else if (bodyBlockState.is(Blocks.WAXED_WEATHERED_COPPER)) {
			bodyOxidationLevel = WeatheringCopper.WeatherState.WEATHERED;
		} else if (bodyBlockState.is(Blocks.WAXED_EXPOSED_COPPER)) {
			bodyOxidationLevel = WeatheringCopper.WeatherState.EXPOSED;
		} else if (bodyBlockState.is(Blocks.WAXED_OXIDIZED_COPPER)) {
			bodyOxidationLevel = WeatheringCopper.WeatherState.OXIDIZED;
		} else {
			bodyOxidationLevel = ((WeatheringCopperFullBlock) bodyBlockState.getBlock()).getAge();
		}

		if (lightningRodOxidationLevel != bodyOxidationLevel) {
			return;
		}

		for (int i = 0; i < this.friendsandfoes_getCopperGolemPattern().getHeight(); ++i) {
			BlockInWorld cachedBlockPosition = patternSearchResult.getBlock(0, i, 0);
			world.setBlock(
				cachedBlockPosition.getPos(),
				Blocks.AIR.defaultBlockState(),
				Block.UPDATE_CLIENTS
			);
			world.levelEvent(
				LevelEvent.PARTICLES_DESTROY_BLOCK,
				cachedBlockPosition.getPos(),
				Block.getId(cachedBlockPosition.getState())
			);
		}

		BlockPos cachedBlockPosition = patternSearchResult.getBlock(0, 2, 0).getPos();
		float copperGolemYaw = headBlockState.getValue(CarvedPumpkinBlock.FACING).toYRot();

		CopperGolemEntity copperGolem = FriendsAndFoesEntityTypes.COPPER_GOLEM.get().create(world);

		copperGolem.setPos(
			(double) cachedBlockPosition.getX() + 0.5D,
			(double) cachedBlockPosition.getY() + 0.05D,
			(double) cachedBlockPosition.getZ() + 0.5D
		);
		copperGolem.setSpawnYaw(copperGolemYaw);
		copperGolem.setOxidationLevel(bodyOxidationLevel);

		if (lightningRodOxidationLevel == WeatheringCopper.WeatherState.OXIDIZED) {
			ArrayList<CopperGolemEntityPose> possiblePoses = new ArrayList<>()
			{{
				add(CopperGolemEntityPose.SPIN_HEAD);
				add(CopperGolemEntityPose.PRESS_BUTTON_UP);
				add(CopperGolemEntityPose.PRESS_BUTTON_DOWN);
			}};
			int randomPoseIndex = copperGolem.getRandom().nextInt(possiblePoses.size());
			CopperGolemEntityPose randomPose = possiblePoses.get(randomPoseIndex);
			copperGolem.setPose(randomPose);
			AnimationHolder animation = copperGolem.getAnimationByPose();

			if (animation != null) {
				int keyFrameAnimationLengthInTicks = animation.get().lengthInTicks();
				int randomKeyframeAnimationTick = copperGolem.getRandom().nextIntBetweenInclusive(keyFrameAnimationLengthInTicks / 6, keyFrameAnimationLengthInTicks - (keyFrameAnimationLengthInTicks / 6));
				copperGolem.setCurrentAnimationTick(randomKeyframeAnimationTick);
			}
		} else {
			boolean isHeadBlockWaxed = this.friendsandfoes_isCopperBlockWaxed(headBlockState);
			boolean isBodyBlockWaxed = this.friendsandfoes_isCopperBlockWaxed(bodyBlockState);
			boolean isWaxed = isHeadBlockWaxed && isBodyBlockWaxed;
			copperGolem.setIsWaxed(isWaxed);
		}

		CopperGolemBrain.setSpinHeadCooldown(copperGolem);
		CopperGolemBrain.setPressButtonCooldown(copperGolem);

		world.addFreshEntity(copperGolem);

		for (ServerPlayer serverPlayerEntity : world.getEntitiesOfClass(
			ServerPlayer.class,
			copperGolem.getBoundingBox().inflate(5.0D)
		)) {
			CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayerEntity, copperGolem);
		}

		for (int j = 0; j < this.friendsandfoes_getCopperGolemPattern().getHeight(); ++j) {
			BlockInWorld cachedBlockPosition2 = patternSearchResult.getBlock(0, j, 0);
			world.blockUpdated(cachedBlockPosition2.getPos(), Blocks.AIR);
		}
	}

	private BlockPattern friendsandfoes_getCopperGolemPattern() {
		if (this.friendsandfoes_copperGolemPattern == null) {
			this.friendsandfoes_copperGolemPattern = BlockPatternBuilder.start()
				.aisle("|", "^", "#")
				.where('|', BlockInWorld.hasState(CopperGolemBuildPatternPredicates.IS_COPPER_GOLEM_LIGHTNING_ROD_PREDICATE))
				.where('^', BlockInWorld.hasState(CopperGolemBuildPatternPredicates.IS_GOLEM_HEAD_PREDICATE))
				.where('#', BlockInWorld.hasState(CopperGolemBuildPatternPredicates.IS_COPPER_GOLEM_BODY_PREDICATE))
				.build();
		}

		return this.friendsandfoes_copperGolemPattern;
	}

	private boolean friendsandfoes_isCopperBlockWaxed(
		BlockState blockState
	) {
		return blockState.is(Blocks.WAXED_COPPER_BLOCK)
			   || blockState.is(Blocks.WAXED_WEATHERED_COPPER)
			   || blockState.is(Blocks.WAXED_EXPOSED_COPPER)
			   || blockState.is(Blocks.WAXED_OXIDIZED_COPPER)
			   || blockState.is(FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get())
			   || blockState.is(FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get())
			   || blockState.is(FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get())
			   || blockState.is(FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get());
	}

	@Override
	public void friendsandfoes_hasRandomTicks(
		BlockState state, CallbackInfoReturnable<Boolean> cir
	) {
		cir.setReturnValue(true);
	}

	@Override
	public void friendsandfoes_randomTick(
		BlockState state,
		ServerLevel world,
		BlockPos pos,
		RandomSource random,
		CallbackInfo ci
	) {
		((ChangeOverTimeBlock) this).changeOverTime(state, world, pos, random);
		ci.cancel();
	}

	@Override
	public void friendsandfoes_onUse(
		BlockState state,
		Level world,
		BlockPos pos,
		Player player,
		BlockHitResult hit,
		CallbackInfoReturnable<InteractionResult> cir
	) {
		var actionResult = OnUseOxidizable.onOxidizableUse(state, world, pos, player, hit);

		if (actionResult.consumesAction()) {
			cir.setReturnValue(actionResult);
		}
	}
}
