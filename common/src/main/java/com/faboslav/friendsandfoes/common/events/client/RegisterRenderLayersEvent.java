package com.faboslav.friendsandfoes.common.events.client;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;

import java.util.function.BiConsumer;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
@Environment(EnvType.CLIENT)
public record RegisterRenderLayersEvent(BiConsumer<Fluid, RenderLayer> fluid, BiConsumer<Block, RenderLayer> block)
{
	public static final EventHandler<RegisterRenderLayersEvent> EVENT = new EventHandler<>();

	public void register(RenderLayer layer, Fluid... fluids) {
		for (Fluid fluid : fluids) {
			this.fluid.accept(fluid, layer);
		}
	}

	public void register(RenderLayer layer, Block... blocks) {
		for (Block block : blocks) {
			this.block.accept(block, layer);
		}
	}
}
