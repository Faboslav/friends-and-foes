package com.faboslav.friendsandfoes.common.events.item;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import org.apache.logging.log4j.util.TriConsumer;

//? if >= 1.21.1 {
import net.minecraft.core.Holder;
//?}

//? if >= 1.21.1 {
public record RegisterBrewingRecipesEvent(TriConsumer<Holder<Potion>, Item, Holder<Potion>> registrator)
//?} else {
/*public record RegisterBrewingRecipesEvent(TriConsumer<Potion, Item, Potion> registrator)
*///?}
{
	public static final EventHandler<RegisterBrewingRecipesEvent> EVENT = new EventHandler<>();
}
