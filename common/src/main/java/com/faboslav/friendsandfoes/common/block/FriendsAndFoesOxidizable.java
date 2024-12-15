package com.faboslav.friendsandfoes.common.block;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings({"rawtypes", "unchecked"})
public interface FriendsAndFoesOxidizable extends WeatheringCopper
{
	Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() -> (BiMap) ImmutableBiMap.builder()
		.put(FriendsAndFoesBlocks.COPPER_BUTTON.get(), FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get())
		.put(FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get())
		.put(FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get(), FriendsAndFoesBlocks.OXIDIZED_COPPER_BUTTON.get())
		.put(Blocks.LIGHTNING_ROD, FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get())
		.put(FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get())
		.put(FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get(), FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get())
		.build());
	Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> ((BiMap) NEXT_BY_BLOCK.get()).inverse());

	static Optional<Block> getPrevious(Block block) {
		return Optional.ofNullable((Block) ((BiMap) PREVIOUS_BY_BLOCK.get()).get(block));
	}

	static Block getFirst(Block block) {
		Block block2 = block;
		for (Block block3 = (Block) ((BiMap) PREVIOUS_BY_BLOCK.get()).get(block); block3 != null; block3 = (Block) ((BiMap) PREVIOUS_BY_BLOCK.get()).get(block3)) {
			block2 = block3;
		}

		return block2;
	}

	static Optional<BlockState> getPrevious(BlockState state) {
		return getPrevious(state.getBlock()).map((block) -> block.withPropertiesOf(state));
	}

	static Optional<Block> getNext(Block block) {
		return Optional.ofNullable((Block) ((BiMap) NEXT_BY_BLOCK.get()).get(block));
	}

	static BlockState getFirst(BlockState state) {
		return getFirst(state.getBlock()).withPropertiesOf(state);
	}

	@Override
	default Optional<BlockState> getNext(BlockState state) {
		return getNext(state.getBlock()).map((block) -> block.withPropertiesOf(state));
	}

	@Override
	default float getChanceModifier() {
		return this.getAge() == WeatherState.UNAFFECTED ? 0.75F:1.0F;
	}
}