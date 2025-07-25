package com.faboslav.friendsandfoes.common.events.client;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import java.util.function.BiConsumer;

//? if >=1.21.6 {
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
//?} else {
/*import net.minecraft.client.renderer.RenderType;
*///?}

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record RegisterRenderLayersEvent(
	BiConsumer<Fluid, /*? >=1.21.6 {*/ ChunkSectionLayer /*?} else {*//*RenderType*//*?}*/> fluid,
	BiConsumer<Block, /*? >=1.21.6 {*/ ChunkSectionLayer /*?} else {*//*RenderType*//*?}*/> block
) {
	public static final EventHandler<RegisterRenderLayersEvent> EVENT = new EventHandler<>();

	public void register(/*? >=1.21.6 {*/ ChunkSectionLayer /*?} else {*//*RenderType*//*?}*/ layer, Fluid... fluids) {
		for (Fluid fluid : fluids) {
			this.fluid.accept(fluid, layer);
		}
	}

	public void register(/*? >=1.21.6 {*/ ChunkSectionLayer /*?} else {*//*RenderType*//*?}*/ layer, Block... blocks) {
		for (Block block : blocks) {
			this.block.accept(block, layer);
		}
	}
}
