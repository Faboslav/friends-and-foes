package com.faboslav.friendsandfoes.events;

import com.faboslav.friendsandfoes.events.base.EventHandler;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;

import java.util.function.BiConsumer;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record RegisterVillagerTradesEvent(VillagerProfession type, BiConsumer<Integer, TradeOffers.Factory> trade)
{
	public static final EventHandler<RegisterVillagerTradesEvent> EVENT = new EventHandler<>();

	public void register(int level, TradeOffers.Factory trade) {
		this.trade.accept(level, trade);
	}
}
