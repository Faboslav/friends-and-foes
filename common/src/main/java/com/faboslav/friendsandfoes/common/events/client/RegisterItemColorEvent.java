package com.faboslav.friendsandfoes.common.events.client;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record RegisterItemColorEvent(BiConsumer<ItemColor, ItemLike[]> colors,
									 BlockColorProvider blockColors)
{
	public static final EventHandler<RegisterItemColorEvent> EVENT = new EventHandler<>();

	public void register(ItemColor color, ItemLike... items) {
		colors.accept(color, items);
	}

	@FunctionalInterface
	public interface BlockColorProvider
	{
		int getColor(
			BlockState blockState,
			@Nullable BlockAndTintGetter blockRenderView,
			@Nullable BlockPos blockPos,
			int i
		);
	}
}
