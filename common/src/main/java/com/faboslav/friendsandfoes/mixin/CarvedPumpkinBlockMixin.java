package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityTypes;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Wearable;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(CarvedPumpkinBlock.class)
public abstract class CarvedPumpkinBlockMixin extends HorizontalFacingBlock implements Wearable
{
	@Nullable
	private BlockPattern tuffGolemPattern;

	private static final Predicate<BlockState> IS_TUFF_GOLEM_HEAD_PREDICATE = state -> state != null && (
		state.isOf(Blocks.CARVED_PUMPKIN)
		|| state.isOf(Blocks.JACK_O_LANTERN)
	);

	private static final Predicate<BlockState> IS_TUFF_GOLEM_WOOL_PREDICATE = state -> state != null && (
		state.getMaterial() == Material.WOOL && state.getSoundGroup() == BlockSoundGroup.WOOL
	);

	protected CarvedPumpkinBlockMixin(Settings settings) {
		super(settings);
	}

	@Inject(method = "onBlockAdded", at = @At("HEAD"))
	private void friendsandfoes_customOnBlockAdded(
		BlockState state,
		World world,
		BlockPos pos,
		BlockState oldState,
		boolean notify,
		CallbackInfo ci
	) {
		if (oldState.isOf(state.getBlock()) == false) {
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
		if (FriendsAndFoes.getConfig().enableTuffGolem == false) {
			return;
		}

		BlockPattern.Result patternSearchResult = this.friendsandfoes_getTuffGolemPattern().searchAround(world, pos);

		if (patternSearchResult == null) {
			return;
		}

		BlockState headBlockState = patternSearchResult.translate(0, 0, 0).getBlockState();
		BlockState woolBlockState = patternSearchResult.translate(0, 2, 0).getBlockState();

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

	private BlockPattern friendsandfoes_getTuffGolemPattern() {
		if (this.tuffGolemPattern == null) {
			this.tuffGolemPattern = BlockPatternBuilder.start()
				.aisle("^", "|", "#")
				.where('^', CachedBlockPosition.matchesBlockState(IS_TUFF_GOLEM_HEAD_PREDICATE))
				.where('|', CachedBlockPosition.matchesBlockState(IS_TUFF_GOLEM_WOOL_PREDICATE))
				.where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.TUFF)))
				.build();
		}

		return this.tuffGolemPattern;
	}
}
