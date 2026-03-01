package com.faboslav.friendsandfoes.common.block;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
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
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.gamerules.GameRules;
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
import net.minecraft.world.attribute.EnvironmentAttributes;
//?}

public final class PenguinEggBlock extends Block
{
	private static final VoxelShape SHAPE = Block.box(5.5, 0.0, 5.5, 10.5, 7.0, 10.5);
	public static final IntegerProperty HATCH = BlockStateProperties.HATCH;

	public PenguinEggBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, 0));
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
		world.destroyBlock(pos, false);
	}

	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (this.shouldHatchProgress(world, pos) && isSuitableBelow(world, pos)) {
			int hatchProgress = state.getValue(HATCH);
			if (hatchProgress < 2) {
				world.playSound(null, pos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
				world.setBlock(pos, state.setValue(HATCH, hatchProgress + 1), 2);
			} else {
				PenguinEntity penguin = FriendsAndFoesEntityTypes.PENGUIN.get().create(world/*? if >=1.21.3 {*/, VersionedEntitySpawnReason.BREEDING/*?}*/);

				if(penguin == null) {
					return;
				}

				world.levelEvent(2001, pos, Block.getId(state));
				penguin.setAge(-24000);
				VersionedEntity.moveTo(penguin, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0F, 0.0F);
				world.addFreshEntity(penguin);

				world.playSound(null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
				world.removeBlock(pos, false);
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
		float f = world.environmentAttributes().getValue(EnvironmentAttributes.TURTLE_EGG_HATCH_CHANCE, blockPos);
		//?} else {
		/*float f = world.getTimeOfDay(1.0F);
		*///?}

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

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
		stateBuilder.add(HATCH);
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
}
