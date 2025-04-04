package com.faboslav.friendsandfoes.common.events.entity;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import java.util.function.BiConsumer;

import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

//? >=1.21.5 {
/*import net.minecraft.resources.ResourceKey;
*///?}

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record RegisterVillagerTradesEvent(
	//? >=1.21.5 {
	/*ResourceKey<VillagerProfession> type,
	*///?} else {
	VillagerProfession type,
	//?}
	BiConsumer<Integer,
	VillagerTrades.ItemListing> trade
)
{
	public static final EventHandler<RegisterVillagerTradesEvent> EVENT = new EventHandler<>();

	public void register(int level, VillagerTrades.ItemListing trade) {
		this.trade.accept(level, trade);
	}
}
