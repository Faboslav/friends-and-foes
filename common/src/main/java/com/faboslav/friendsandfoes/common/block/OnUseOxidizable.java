package com.faboslav.friendsandfoes.common.block;

import com.faboslav.friendsandfoes.common.util.WaxableBlocksMap;
import com.google.common.collect.BiMap;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import java.util.Optional;

public final class OnUseOxidizable
{
	public static ActionResult onOxidizableUse(
		BlockState blockState,
		World world,
		BlockPos blockPos,
		PlayerEntity player,
		Hand hand,
		BlockHitResult hit
	) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item itemInHand = itemStack.getItem();
		ItemUsageContext itemUsageContext = new ItemUsageContext(player, hand, hit);

		if (itemInHand instanceof HoneycombItem) {
			Optional<BlockState> possibleWaxedState = OnUseOxidizable.getWaxedState(blockState);

			if (possibleWaxedState.isPresent()) {
				if (player instanceof ServerPlayerEntity) {
					Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, blockPos, itemStack);
				}

				if (!player.isCreative()) {
					itemStack.decrement(1);
				}

				world.setBlockState(blockPos, possibleWaxedState.get(), 11);
				world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(player, possibleWaxedState.get()));
				world.syncWorldEvent(player, 3003, blockPos, 0);

				return ActionResult.success(world.isClient);
			}
		} else if (itemInHand instanceof AxeItem) {
			Optional<BlockState> possibleUnWaxedState = OnUseOxidizable.getUnWaxedState(blockState);
			Optional<BlockState> possibleOxidationState = FriendsAndFoesOxidizable.getDecreasedOxidationState(blockState);
			Optional<BlockState> possibleState = Optional.empty();

			if (possibleUnWaxedState.isPresent()) {
				world.playSound(player, blockPos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.syncWorldEvent(player, 3004, blockPos, 0);
				possibleState = possibleUnWaxedState;
			} else if (possibleOxidationState.isPresent()) {
				world.playSound(player, blockPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.syncWorldEvent(player, 3005, blockPos, 0);
				possibleState = possibleOxidationState;
			}

			if (possibleState.isPresent()) {
				if (player instanceof ServerPlayerEntity) {
					Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, blockPos, itemStack);
				}

				world.setBlockState(blockPos, possibleState.get(), 11);
				world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(player, possibleState.get()));

				if (player != null) {
					itemStack.damage(1, player, (p) -> {
						p.sendToolBreakStatus(hand);
					});
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		}

		if (itemInHand instanceof HoneycombItem || itemInHand instanceof AxeItem) {
			ActionResult itemInHandUsageResult = itemInHand.useOnBlock(itemUsageContext);

			if (itemInHandUsageResult.isAccepted()) {
				return itemInHandUsageResult;
			}
		}

		return ActionResult.PASS;
	}

	private static Optional<BlockState> getWaxedState(BlockState state) {
		return Optional.ofNullable((Block) ((BiMap) WaxableBlocksMap.UNWAXED_TO_WAXED_BLOCKS.get()).get(state.getBlock())).map((block) -> {
			return block.getStateWithProperties(state);
		});
	}

	private static Optional<BlockState> getUnWaxedState(BlockState state) {
		return Optional.ofNullable((Block) ((BiMap) WaxableBlocksMap.WAXED_TO_UNWAXED_BLOCKS.get()).get(state.getBlock())).map((block) -> {
			return block.getStateWithProperties(state);
		});
	}
}
