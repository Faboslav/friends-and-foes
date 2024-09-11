package com.faboslav.friendsandfoes.common.events;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record RegisterItemGroupsEvent(TabCreator creator)
{
	public static final EventHandler<RegisterItemGroupsEvent> EVENT = new EventHandler<>();

	public void register(Identifier id, Consumer<ItemGroup.Builder> builder, Consumer<List<ItemStack>> displayStacks) {
		creator.create(id, builder, displayStacks);
	}

	@FunctionalInterface
	public interface TabCreator
	{
		void create(Identifier id, Consumer<ItemGroup.Builder> builder, Consumer<List<ItemStack>> displayStacks);
	}
}
