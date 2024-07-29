package com.faboslav.friendsandfoes.common.block;


import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.GameEvent.Emitter;
import org.jetbrains.annotations.Nullable;

public final class CrabEggBlock extends Block
{
	private static final VoxelShape SMALL_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 12.0, 7.0, 12.0);
	private static final VoxelShape LARGE_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 7.0, 15.0);
	public static final IntProperty HATCH;
	public static final IntProperty EGGS;

	public CrabEggBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(HATCH, 0).with(EGGS, 1));
	}

	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (!entity.bypassesSteppingEffects()) {
			this.tryBreakEgg(world, state, pos, entity, 100);
		}

		super.onSteppedOn(world, pos, state, entity);
	}

	public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (!(entity instanceof ZombieEntity)) {
			this.tryBreakEgg(world, state, pos, entity, 3);
		}

		super.onLandedUpon(world, state, pos, entity, fallDistance);
	}

	private void tryBreakEgg(World world, BlockState state, BlockPos pos, Entity entity, int inverseChance) {
		if (this.breaksEgg(world, entity)) {
			if (!world.isClient() && world.getRandom().nextInt(inverseChance) == 0 && state.isOf(FriendsAndFoesBlocks.CRAB_EGG.get())) {
				this.breakEgg(world, pos, state);
			}

		}
	}

	private void breakEgg(World world, BlockPos pos, BlockState state) {
		world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
		int i = state.get(EGGS);

		if (i <= 1) {
			world.breakBlock(pos, false);
		} else {
			world.setBlockState(pos, state.with(EGGS, i - 1), 2);
			world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, Emitter.of(state));
			world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
		}

	}

	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (this.shouldHatchProgress(world) && isSuitableBelow(world, pos)) {
			int i = state.get(HATCH);
			if (i < 2) {
				world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
				world.setBlockState(pos, state.with(HATCH, i + 1), 2);
			} else {
				world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
				world.removeBlock(pos, false);

				for (int j = 0; j < state.get(EGGS); ++j) {
					world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
					CrabEntity crab = FriendsAndFoesEntityTypes.CRAB.get().create(world);
					crab.setBreedingAge(-24000);
					crab.refreshPositionAndAngles((double) pos.getX() + 0.3 + (double) j * 0.2, pos.getY(), (double) pos.getZ() + 0.3, 0.0F, 0.0F);
					world.spawnEntity(crab);
				}
			}
		}

	}

	public static boolean isSuitableBelow(BlockView world, BlockPos pos) {
		return world.getBlockState(pos.down()).isIn(FriendsAndFoesTags.CRAB_BURROW_SPOT_BLOCKS);
	}

	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (isSuitableBelow(world, pos) && !world.isClient()) {
			world.syncWorldEvent(2005, pos, 0);
		}

	}

	private boolean shouldHatchProgress(World world) {
		float f = world.getSkyAngle(1.0F);
		if ((double) f < 0.69 && (double) f > 0.65) {
			return true;
		} else {
			return world.random.nextInt(500) == 0;
		}
	}

	public void afterBreak(
		World world,
		PlayerEntity player,
		BlockPos pos,
		BlockState state,
		@Nullable BlockEntity blockEntity,
		ItemStack stack
	) {
		super.afterBreak(world, player, pos, state, blockEntity, stack);
		this.breakEgg(world, pos, state);
	}

	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		return !context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(EGGS) < 4 ? true:super.canReplace(state, context);
	}

	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
		return blockState.isOf(this) ? blockState.with(EGGS, Math.min(4, (Integer) blockState.get(EGGS) + 1)):super.getPlacementState(ctx);
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(EGGS) > 1 ? LARGE_SHAPE:SMALL_SHAPE;
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(HATCH, EGGS);
	}

	private boolean breaksEgg(World world, Entity entity) {
		if (!(entity instanceof CrabEntity) && !(entity instanceof BatEntity)) {
			if (!(entity instanceof LivingEntity)) {
				return false;
			} else {
				return entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
			}
		} else {
			return false;
		}
	}

	static {
		HATCH = Properties.HATCH;
		EGGS = Properties.EGGS;
	}
}
