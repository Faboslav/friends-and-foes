package com.faboslav.friendsandfoes.common.events.item;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import org.apache.logging.log4j.util.TriConsumer;

public record RegisterBrewingRecipesEvent(TriConsumer<Holder<Potion>, Item, Holder<Potion>> registrator)
{

	public static final EventHandler<RegisterBrewingRecipesEvent> EVENT = new EventHandler<>();
}
