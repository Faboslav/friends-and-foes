package com.faboslav.friendsandfoes.events.lifecycle;

import com.faboslav.friendsandfoes.events.base.EventHandler;
import net.minecraft.block.Block;

public record RegisterFlammabilityEvent(Registrar registrar)
{

	public static final EventHandler<RegisterFlammabilityEvent> EVENT = new EventHandler<>();

	public void register(Block block, int igniteOdds, int burnOdds) {
		registrar.register(block, igniteOdds, burnOdds);
	}

	@FunctionalInterface
	public interface Registrar
	{
		void register(Block block, int igniteOdds, int burnOdds);
	}
}
