package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.util.CopperGolemBuildPatternPredicates;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.SpawnReason;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(CarvedPumpkinBlock.class)
public abstract class CarvedPumpkinBlockMixin extends HorizontalFacingBlock
{
	@Nullable
	private BlockPattern friendsandfoes_copperGolemDispenserPattern;

	@Nullable
	private BlockPattern friendsandfoes_tuffGolemDispenserPattern;

	@Nullable
	private BlockPattern friendsandfoes_tuffGolemPattern;

	private static final Predicate<BlockState> IS_TUFF_GOLEM_HEAD_PREDICATE = state -> state != null && (
		state.isOf(Blocks.CARVED_PUMPKIN)
		|| state.isOf(Blocks.JACK_O_LANTERN)
	);

	private static final Predicate<BlockState> IS_TUFF_GOLEM_WOOL_PREDICATE = state -> state != null && (
		state.isIn(BlockTags.WOOL)
	);

	protected CarvedPumpkinBlockMixin(Settings settings) {
		super(settings);
	}

	@Inject(
		method = "canDispense",
		at = @At("RETURN"),
		cancellable = true
	)
	public void friendsandfoes_canDispense(WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) {
			cir.setReturnValue(this.getTuffGolemDispenserPattern().searchAround(world, pos) != null || this.getCopperGolemDispenserPattern().searchAround(world, pos) != null);
		}
	}

	@Inject(
		method = "onBlockAdded",
		at = @At("HEAD")
	)
	private void friendsandfoes_customOnBlockAdded(
		BlockState state,
		World world,
		BlockPos pos,
		BlockState oldState,
		boolean notify,
		CallbackInfo ci
	) {
		if (!oldState.isOf(state.getBlock())) {
			this.friendsandfoes_tryToSpawnTuffGolem(
				world,
				pos
			);
		}
	}

	private void friendsandfoes_tryToSpawnTuffGolem(
		World world,
		BlockPos pos
	) {
		if (!FriendsAndFoes.getConfig().enableTuffGolem) {
			return;
		}

		BlockPattern.Result patternSearchResult = this.friendsandfoes_getTuffGolemPattern().searchAround(world, pos);

		if (patternSearchResult == null) {
			return;
		}

		BlockState headBlockState = patternSearchResult.translate(0, 0, 0).getBlockState();
		BlockState woolBlockState = patternSearchResult.translate(0, 1, 0).getBlockState();

		for (int i = 0; i < this.friendsandfoes_getTuffGolemPattern().getHeight(); ++i) {
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
		float tuffGolemYaw = headBlockState.get(CarvedPumpkinBlock.FACING).asRotation();

		TuffGolemEntity tuffGolem = FriendsAndFoesEntityTypes.TUFF_GOLEM.get().create(world);

		tuffGolem.setPosition(
			(double) cachedBlockPosition.getX() + 0.5D,
			(double) cachedBlockPosition.getY() + 0.05D,
			(double) cachedBlockPosition.getZ() + 0.5D
		);
		tuffGolem.setSpawnYaw(tuffGolemYaw);
		tuffGolem.setColor(TuffGolemEntity.Color.fromWool(woolBlockState.getBlock()));
		tuffGolem.initialize((ServerWorldAccess) world, world.getLocalDifficulty(cachedBlockPosition), SpawnReason.TRIGGERED, null, null);
		world.spawnEntity(tuffGolem);

		for (ServerPlayerEntity serverPlayerEntity : world.getNonSpectatingEntities(
			ServerPlayerEntity.class,
			tuffGolem.getBoundingBox().expand(5.0D)
		)) {
			Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, tuffGolem);
		}

		for (int j = 0; j < this.friendsandfoes_getTuffGolemPattern().getHeight(); ++j) {
			CachedBlockPosition cachedBlockPosition2 = patternSearchResult.translate(0, j, 0);
			world.updateNeighbors(cachedBlockPosition2.getBlockPos(), Blocks.AIR);
		}
	}

	private BlockPattern getCopperGolemDispenserPattern() {
		if (this.friendsandfoes_copperGolemDispenserPattern == null) {
			this.friendsandfoes_copperGolemDispenserPattern = BlockPatternBuilder.start()
				.aisle("|", " ", "#")
				.where('|', CachedBlockPosition.matchesBlockState(CopperGolemBuildPatternPredicates.IS_COPPER_GOLEM_LIGHTNING_ROD_PREDICATE))
				.where('#', CachedBlockPosition.matchesBlockState(CopperGolemBuildPatternPredicates.IS_COPPER_GOLEM_BODY_PREDICATE))
				.build();
		}

		return this.friendsandfoes_copperGolemDispenserPattern;
	}

	private BlockPattern getTuffGolemDispenserPattern() {
		if (this.friendsandfoes_tuffGolemDispenserPattern == null) {
			this.friendsandfoes_tuffGolemDispenserPattern = BlockPatternBuilder.start()
				.aisle(" ", "|", "#")
				.where('|', CachedBlockPosition.matchesBlockState(IS_TUFF_GOLEM_WOOL_PREDICATE))
				.where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.TUFF)))
				.build();
		}

		return this.friendsandfoes_tuffGolemDispenserPattern;
	}

	private BlockPattern friendsandfoes_getTuffGolemPattern() {
		if (this.friendsandfoes_tuffGolemPattern == null) {
			this.friendsandfoes_tuffGolemPattern = BlockPatternBuilder.start()
				.aisle("^", "|", "#")
				.where('^', CachedBlockPosition.matchesBlockState(IS_TUFF_GOLEM_HEAD_PREDICATE))
				.where('|', CachedBlockPosition.matchesBlockState(IS_TUFF_GOLEM_WOOL_PREDICATE))
				.where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.TUFF)))
				.build();
		}

		return this.friendsandfoes_tuffGolemPattern;
	}
}
