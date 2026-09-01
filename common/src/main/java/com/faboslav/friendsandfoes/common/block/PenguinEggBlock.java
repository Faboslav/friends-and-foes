package com.faboslav.friendsandfoes.common.block;

import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings({"deprecation", "unchecked"})
public final class PenguinEggBlock extends Block
{
	private static final VoxelShape SHAPE = Block.box(5.5, 0.0, 5.5, 10.5, 7.0, 10.5);
	public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
	private static final int HATCH_TIME_TICKS = 24000;
	private static final int RANDOM_HATCH_OFFSET_TICKS = 300;

	public PenguinEggBlock(BlockBehaviour.Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
		stateBuilder.add(HATCH);
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE;
	}

	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		if (!world.isClientSide()) {
			int progressionTickDelay = HATCH_TIME_TICKS / 3;
			world.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(state));
			world.scheduleTick(pos, this, progressionTickDelay + world.getRandom().nextInt(RANDOM_HATCH_OFFSET_TICKS));
		}
	}

	public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		int hatchProgress = state.getValue(HATCH);

		if (hatchProgress < 2) {
			world.playSound(null, pos, FriendsAndFoesSoundEvents.ENTITY_PENGUIN_EGG_CRACK.get(), SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
			world.setBlock(pos, state.setValue(HATCH, hatchProgress + 1), 2);
		} else {
			PenguinEntity penguin = FriendsAndFoesEntityTypes.PENGUIN.get().create(world/*? if >=1.21.3 {*/, VersionedEntitySpawnReason.BREEDING/*?}*/);

			if (penguin == null) {
				return;
			}

			world.levelEvent(2001, pos, Block.getId(state));
			penguin.setAge(-24000);
			VersionedEntity.moveTo(penguin, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0F, 0.0F);
			world.addFreshEntity(penguin);

			world.playSound(null, pos, FriendsAndFoesSoundEvents.ENTITY_PENGUIN_EGG_HATCH.get(), SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
			world.removeBlock(pos, false);
		}
	}

	public boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}
}
