package com.faboslav.friendsandfoes.events.client;

import com.faboslav.friendsandfoes.events.base.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record RegisterItemColorEvent(BiConsumer<ItemColorProvider, ItemConvertible[]> colors,
									 BlockColorProvider blockColors)
{
	public static final EventHandler<RegisterItemColorEvent> EVENT = new EventHandler<>();

	public void register(ItemColorProvider color, ItemConvertible... items) {
		colors.accept(color, items);
	}

	@FunctionalInterface
	public interface BlockColorProvider
	{
		int getColor(
			BlockState blockState,
			@Nullable BlockRenderView blockRenderView,
			@Nullable BlockPos blockPos,
			int i
		);
	}
}
