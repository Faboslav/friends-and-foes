package com.faboslav.friendsandfoes.common.events.item;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import org.apache.logging.log4j.util.TriConsumer;

public record RegisterBrewingRecipesEvent(TriConsumer<RegistryEntry<Potion>, Item, RegistryEntry<Potion>> registrator) {

    public static final EventHandler<RegisterBrewingRecipesEvent> EVENT = new EventHandler<>();
}
