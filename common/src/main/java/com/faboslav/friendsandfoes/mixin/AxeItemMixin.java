package com.faboslav.friendsandfoes.mixin;


import com.faboslav.friendsandfoes.block.Oxidizable;
import com.faboslav.friendsandfoes.common.util.WaxableBlocksMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin
{
	@ModifyVariable(
		method = "useOnBlock",
		ordinal = 0,
		at = @At(
			value = "STORE"
		)
	)
	private Optional<BlockState> friendsandfoes_modifyOxidizedBlock(
		Optional<BlockState> originalBlockState,
		ItemUsageContext context
	) {
		if (originalBlockState.isPresent()) {
			return originalBlockState;
		}

		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		PlayerEntity playerEntity = context.getPlayer();
		BlockState state = world.getBlockState(blockPos);

		return this.friendsandfoes_tryStrip(world, blockPos, playerEntity, state);
	}

	private Optional<BlockState> friendsandfoes_tryStrip(
		World world,
		BlockPos pos,
		@Nullable PlayerEntity player,
		BlockState state
	) {
		Optional<BlockState> decreasedOxidationState = Oxidizable.getDecreasedOxidationState(state);

		if (decreasedOxidationState.isPresent()) {
			world.playSound(player, pos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.syncWorldEvent(player, 3005, pos, 0);
			return decreasedOxidationState;
		}

		Optional<BlockState> unwaxedState = Optional.ofNullable(WaxableBlocksMap.WAXED_TO_UNWAXED_BLOCKS.get().get(state.getBlock())).map((block) -> block.getStateWithProperties(state));

		if (unwaxedState.isPresent()) {
			world.playSound(player, pos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.syncWorldEvent(player, 3004, pos, 0);
			return unwaxedState;
		}

		return Optional.empty();
	}
}