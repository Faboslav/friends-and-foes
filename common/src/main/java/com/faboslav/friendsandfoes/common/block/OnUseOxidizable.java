package com.faboslav.friendsandfoes.common.block;

import com.faboslav.friendsandfoes.common.util.WaxableBlocksMap;
import com.google.common.collect.BiMap;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import java.util.Optional;

public final class OnUseOxidizable
{
	public static InteractionResult onOxidizableUse(
		BlockState blockState,
		Level world,
		BlockPos blockPos,
		Player player,
		BlockHitResult hit
	) {
		for (InteractionHand hand : InteractionHand.values()) {
			ItemStack itemStack = player.getItemInHand(hand);
			if (itemStack.getItem() instanceof HoneycombItem || itemStack.getItem() instanceof AxeItem) {
				var actionResult = OnUseOxidizable.onOxidizableUseHand(blockState, world, blockPos, player, hand, hit);

				if (actionResult.consumesAction()) {
					player.swing(hand);
					return actionResult;
				}
			}
		}

		return InteractionResult.PASS;
	}

	public static InteractionResult onOxidizableUseHand(
		BlockState blockState,
		Level world,
		BlockPos blockPos,
		Player player,
		InteractionHand hand,
		BlockHitResult hit
	) {
		ItemStack itemStack = player.getItemInHand(hand);
		Item itemInHand = itemStack.getItem();
		UseOnContext itemUsageContext = new UseOnContext(player, hand, hit);

		if (itemInHand instanceof HoneycombItem) {
			Optional<BlockState> possibleWaxedState = OnUseOxidizable.getWaxedState(blockState);

			if (possibleWaxedState.isPresent()) {
				if (player instanceof ServerPlayer) {
					CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockPos, itemStack);
				}

				if (!player.isCreative()) {
					itemStack.shrink(1);
				}

				world.setBlock(blockPos, possibleWaxedState.get(), 11);
				world.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, possibleWaxedState.get()));
				world.levelEvent(player, 3003, blockPos, 0);

				return InteractionResult.sidedSuccess(false);
			}
		} else if (itemInHand instanceof AxeItem) {
			Optional<BlockState> possibleUnWaxedState = OnUseOxidizable.getUnWaxedState(blockState);
			Optional<BlockState> possibleOxidationState = FriendsAndFoesOxidizable.getPrevious(blockState);
			Optional<BlockState> possibleState = Optional.empty();

			if (possibleUnWaxedState.isPresent()) {
				world.playSound(player, blockPos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
				world.levelEvent(player, 3004, blockPos, 0);
				possibleState = possibleUnWaxedState;
			} else if (possibleOxidationState.isPresent()) {
				world.playSound(player, blockPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
				world.levelEvent(player, 3005, blockPos, 0);
				possibleState = possibleOxidationState;
			}

			if (possibleState.isPresent()) {
				if (player instanceof ServerPlayer) {
					CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockPos, itemStack);
				}

				world.setBlock(blockPos, possibleState.get(), 11);
				world.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, possibleState.get()));

				if (player != null) {
					itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
				}

				return InteractionResult.sidedSuccess(false);
			} else {
				return InteractionResult.PASS;
			}
		}

		if (itemInHand instanceof HoneycombItem || itemInHand instanceof AxeItem) {
			InteractionResult itemInHandUsageResult = itemInHand.useOn(itemUsageContext);

			if (itemInHandUsageResult.consumesAction()) {
				return itemInHandUsageResult;
			}
		}

		return InteractionResult.PASS;
	}

	private static Optional<BlockState> getWaxedState(BlockState state) {
		return Optional.ofNullable((Block) ((BiMap) WaxableBlocksMap.UNWAXED_TO_WAXED_BLOCKS.get()).get(state.getBlock())).map((block) -> {
			return block.withPropertiesOf(state);
		});
	}

	private static Optional<BlockState> getUnWaxedState(BlockState state) {
		return Optional.ofNullable((Block) ((BiMap) WaxableBlocksMap.WAXED_TO_UNWAXED_BLOCKS.get()).get(state.getBlock())).map((block) -> {
			return block.withPropertiesOf(state);
		});
	}
}
