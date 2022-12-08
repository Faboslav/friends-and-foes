package com.faboslav.friendsandfoes.block;

import com.faboslav.friendsandfoes.init.FriendsAndFoesBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings({"rawtypes", "unchecked"})
public interface Oxidizable extends net.minecraft.block.Oxidizable
{
	Supplier<BiMap<Block, Block>> OXIDATION_LEVEL_INCREASES = Suppliers.memoize(() -> (BiMap) ImmutableBiMap.builder()
		.put(FriendsAndFoesBlocks.COPPER_BUTTON.get(), FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get())
		.put(FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get())
		.put(FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.OXIDIZED_COPPER_BUTTON.get())
		.put(Blocks.LIGHTNING_ROD, FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get())
		.put(FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get())
		.put(FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get())
		.build());
	Supplier<BiMap<Block, Block>> OXIDATION_LEVEL_DECREASES = Suppliers.memoize(() -> ((BiMap) OXIDATION_LEVEL_INCREASES.get()).inverse());

	static Optional<Block> getDecreasedOxidationBlock(Block block) {
		return Optional.ofNullable((Block) ((BiMap) OXIDATION_LEVEL_DECREASES.get()).get(block));
	}

	static Block getUnaffectedOxidationBlock(Block block) {
		Block block2 = block;
		for (Block block3 = (Block) ((BiMap) OXIDATION_LEVEL_DECREASES.get()).get(block); block3 != null; block3 = (Block) ((BiMap) OXIDATION_LEVEL_DECREASES.get()).get(block3)) {
			block2 = block3;
		}

		return block2;
	}

	static Optional<BlockState> getDecreasedOxidationState(BlockState state) {
		return getDecreasedOxidationBlock(state.getBlock()).map((block) -> {
			return block.getStateWithProperties(state);
		});
	}

	static Optional<Block> getIncreasedOxidationBlock(Block block) {
		return Optional.ofNullable((Block) ((BiMap) OXIDATION_LEVEL_INCREASES.get()).get(block));
	}

	static BlockState getUnaffectedOxidationState(BlockState state) {
		return getUnaffectedOxidationBlock(state.getBlock()).getStateWithProperties(state);
	}

	@Override
	default Optional<BlockState> getDegradationResult(BlockState state) {
		return getIncreasedOxidationBlock(state.getBlock()).map((block) -> {
			return block.getStateWithProperties(state);
		});
	}

	@Override
	default float getDegradationChanceMultiplier() {
		return this.getDegradationLevel() == net.minecraft.block.Oxidizable.OxidationLevel.UNAFFECTED ? 0.75F:1.0F;
	}
}
