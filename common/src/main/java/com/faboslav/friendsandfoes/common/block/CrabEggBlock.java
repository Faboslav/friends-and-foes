package com.faboslav.friendsandfoes.common.block;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import com.faboslav.friendsandfoes.common.versions.VersionedGameRulesProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

//? if >= 1.21.11 {
/*import net.minecraft.world.attribute.EnvironmentAttributes;
*///?}

public final class CrabEggBlock extends Block
{
	private static final VoxelShape SMALL_SHAPE = Block.box(3.0, 0.0, 3.0, 12.0, 7.0, 12.0);
	private static final VoxelShape LARGE_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 7.0, 15.0);
	public static final IntegerProperty HATCH;
	public static final IntegerProperty EGGS;

	public CrabEggBlock(BlockBehaviour.Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, 0).setValue(EGGS, 1));
	}

	public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
		if (!entity.isSteppingCarefully()) {
			this.tryBreakEgg(world, state, pos, entity, 100);
		}

		super.stepOn(world, pos, state, entity);
	}

	public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (!(entity instanceof Zombie)) {
			this.tryBreakEgg(world, state, pos, entity, 3);
		}

		super.fallOn(world, state, pos, entity, fallDistance);
	}

	private void tryBreakEgg(Level level, BlockState state, BlockPos pos, Entity entity, int inverseChance) {
		if (state.is(FriendsAndFoesBlocks.CRAB_EGG.get()) && level instanceof ServerLevel serverLevel && this.canBreaksEgg(serverLevel, entity) && level.getRandom().nextInt(inverseChance) == 0) {
			this.breakEgg(level, pos, state);
		}
	}

	private void breakEgg(Level world, BlockPos pos, BlockState state) {
		world.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
		int i = state.getValue(EGGS);

		if (i <= 1) {
			world.destroyBlock(pos, false);
		} else {
			world.setBlock(pos, state.setValue(EGGS, i - 1), 2);
			world.gameEvent(GameEvent.BLOCK_DESTROY, pos, Context.of(state));
			world.levelEvent(2001, pos, Block.getId(state));
		}

	}

	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (this.shouldHatchProgress(world, pos) && isSuitableBelow(world, pos)) {
			int i = state.getValue(HATCH);
			if (i < 2) {
				world.playSound(null, pos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
				world.setBlock(pos, state.setValue(HATCH, i + 1), 2);
			} else {
				world.playSound(null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
				world.removeBlock(pos, false);

				for (int j = 0; j < state.getValue(EGGS); ++j) {
					world.levelEvent(2001, pos, Block.getId(state));
					CrabEntity crab = FriendsAndFoesEntityTypes.CRAB.get().create(world/*? if >=1.21.3 {*/, VersionedEntitySpawnReason.BREEDING/*?}*/);
					crab.setAge(-24000);
					VersionedEntity.moveTo(crab, (double) pos.getX() + 0.3 + (double) j * 0.2, pos.getY(), (double) pos.getZ() + 0.3, 0.0F, 0.0F);
					crab.setHome(crab.getNewHome());
					world.addFreshEntity(crab);
				}
			}
		}

	}

	public static boolean isSuitableBelow(BlockGetter world, BlockPos pos) {
		return world.getBlockState(pos.below()).is(FriendsAndFoesTags.CRAB_BURROW_SPOT_BLOCKS);
	}

	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
		if (isSuitableBelow(world, pos) && !world.isClientSide()) {
			world.levelEvent(2005, pos, 0);
		}
	}

	private boolean shouldHatchProgress(Level world, BlockPos blockPos) {
		//? if >= 1.21.11 {
		/*float f = world.environmentAttributes().getValue(EnvironmentAttributes.TURTLE_EGG_HATCH_CHANCE, blockPos);
		*///?} else {
		float f = world.getTimeOfDay(1.0F);
		//?}

		return f > 0.0F && world.getRandom().nextFloat() < f;
	}

	public void playerDestroy(
		Level world,
		Player player,
		BlockPos pos,
		BlockState state,
		@Nullable BlockEntity blockEntity,
		ItemStack stack
	) {
		super.playerDestroy(world, player, pos, state, blockEntity, stack);
		this.breakEgg(world, pos, state);
	}

	public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		return !context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && state.getValue(EGGS) < 4 || super.canBeReplaced(state, context);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos());
		return blockState.is(this) ? blockState.setValue(EGGS, Math.min(4, blockState.getValue(EGGS) + 1)):super.getStateForPlacement(ctx);
	}

	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return state.getValue(EGGS) > 1 ? LARGE_SHAPE:SMALL_SHAPE;
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HATCH, EGGS);
	}

	private boolean canBreaksEgg(ServerLevel world, Entity entity) {
		if (!(entity instanceof CrabEntity) && !(entity instanceof Bat)) {
			if (!(entity instanceof LivingEntity)) {
				return false;
			} else {
				return entity instanceof Player || VersionedGameRulesProvider.getBoolean(world, VersionedGameRulesProvider.MOB_GRIEFING);
			}
		} else {
			return false;
		}
	}

	static {
		HATCH = BlockStateProperties.HATCH;
		EGGS = BlockStateProperties.EGGS;
	}
}
