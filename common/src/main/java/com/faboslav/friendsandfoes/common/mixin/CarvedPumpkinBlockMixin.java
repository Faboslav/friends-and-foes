package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

//? if <= 1.21.8 {
/*import com.faboslav.friendsandfoes.common.util.CopperGolemBuildPatternPredicates;
*///?}

@Mixin(CarvedPumpkinBlock.class)
public abstract class CarvedPumpkinBlockMixin extends HorizontalDirectionalBlock
{
	//? if <= 1.21.8 {
	/*@Nullable
	private BlockPattern friendsandfoes_copperGolemDispenserPattern;
	*///?}

	@Nullable
	private BlockPattern friendsandfoes_tuffGolemDispenserPattern;

	@Nullable
	private BlockPattern friendsandfoes_tuffGolemPattern;

	private static final Predicate<BlockState> IS_TUFF_GOLEM_HEAD_PREDICATE = state -> state != null && (
		state.is(Blocks.CARVED_PUMPKIN)
		|| state.is(Blocks.JACK_O_LANTERN)
	);

	private static final Predicate<BlockState> IS_TUFF_GOLEM_WOOL_PREDICATE = state -> state != null && (
		state.is(BlockTags.WOOL)
	);

	protected CarvedPumpkinBlockMixin(Properties settings) {
		super(settings);
	}

	@Inject(
		method = "canSpawnGolem",
		at = @At("HEAD"),
		cancellable = true
	)
	public void friendsandfoes_canDispense(LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (this.getTuffGolemDispenserPattern().find(world, pos) != null) {
			cir.setReturnValue(true);
		}

		//? if <= 1.21.8 {
		/*if (this.getCopperGolemDispenserPattern().find(world, pos) != null) {
			cir.setReturnValue(true);
		}
		*///?}
	}

	@Inject(
		method = "onPlace",
		at = @At("HEAD")
	)
	private void friendsandfoes_customOnBlockAdded(
		BlockState state,
		Level world,
		BlockPos pos,
		BlockState oldState,
		boolean notify,
		CallbackInfo ci
	) {
		if (!oldState.is(state.getBlock())) {
			this.friendsandfoes_tryToSpawnTuffGolem(
				world,
				pos
			);
		}
	}

	private void friendsandfoes_tryToSpawnTuffGolem(
		Level world,
		BlockPos pos
	) {
		if (!FriendsAndFoes.getConfig().enableTuffGolem) {
			return;
		}

		BlockPattern.BlockPatternMatch patternSearchResult = this.friendsandfoes_getTuffGolemPattern().find(world, pos);

		if (patternSearchResult == null) {
			return;
		}

		BlockState headBlockState = patternSearchResult.getBlock(0, 0, 0).getState();
		BlockState woolBlockState = patternSearchResult.getBlock(0, 1, 0).getState();

		CarvedPumpkinBlock.clearPatternBlocks(world, patternSearchResult);

		BlockPos cachedBlockPosition = patternSearchResult.getBlock(0, 2, 0).getPos();
		float tuffGolemYaw = headBlockState.getValue(CarvedPumpkinBlock.FACING).toYRot();

		TuffGolemEntity tuffGolem = FriendsAndFoesEntityTypes.TUFF_GOLEM.get().create(world/*? if >=1.21.3 {*/, VersionedEntitySpawnReason.TRIGGERED/*?}*/);

		tuffGolem.setPos(
			(double) cachedBlockPosition.getX() + 0.5D,
			(double) cachedBlockPosition.getY() + 0.05D,
			(double) cachedBlockPosition.getZ() + 0.5D
		);
		tuffGolem.setSpawnYaw(tuffGolemYaw);
		tuffGolem.setColor(TuffGolemEntity.Color.fromWool(woolBlockState.getBlock()));
		tuffGolem.finalizeSpawn((ServerLevelAccessor) world, ((ServerLevelAccessor) world).getCurrentDifficultyAt(cachedBlockPosition), VersionedEntitySpawnReason.TRIGGERED, null);
		world.addFreshEntity(tuffGolem);

		for (ServerPlayer serverPlayerEntity : world.getEntitiesOfClass(
			ServerPlayer.class,
			tuffGolem.getBoundingBox().inflate(5.0D)
		)) {
			CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayerEntity, tuffGolem);
		}

		CarvedPumpkinBlock.updatePatternBlocks(world, patternSearchResult);
	}

	//? if <= 1.21.8 {
	/*private BlockPattern getCopperGolemDispenserPattern() {
		if (this.friendsandfoes_copperGolemDispenserPattern == null) {
			this.friendsandfoes_copperGolemDispenserPattern = BlockPatternBuilder.start()
				.aisle("|", " ", "#")
				.where('|', BlockInWorld.hasState(CopperGolemBuildPatternPredicates.IS_COPPER_GOLEM_LIGHTNING_ROD_PREDICATE))
				.where('#', BlockInWorld.hasState(CopperGolemBuildPatternPredicates.IS_COPPER_GOLEM_BODY_PREDICATE))
				.build();
		}

		return this.friendsandfoes_copperGolemDispenserPattern;
	}
	*///?}

	private BlockPattern getTuffGolemDispenserPattern() {
		if (this.friendsandfoes_tuffGolemDispenserPattern == null) {
			this.friendsandfoes_tuffGolemDispenserPattern = BlockPatternBuilder.start()
				.aisle(" ", "|", "#")
				.where('|', BlockInWorld.hasState(IS_TUFF_GOLEM_WOOL_PREDICATE))
				.where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.TUFF)))
				.build();
		}

		return this.friendsandfoes_tuffGolemDispenserPattern;
	}

	private BlockPattern friendsandfoes_getTuffGolemPattern() {
		if (this.friendsandfoes_tuffGolemPattern == null) {
			this.friendsandfoes_tuffGolemPattern = BlockPatternBuilder.start()
				.aisle("^", "|", "#")
				.where('^', BlockInWorld.hasState(IS_TUFF_GOLEM_HEAD_PREDICATE))
				.where('|', BlockInWorld.hasState(IS_TUFF_GOLEM_WOOL_PREDICATE))
				.where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.TUFF)))
				.build();
		}

		return this.friendsandfoes_tuffGolemPattern;
	}
}
