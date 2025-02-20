//? >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import com.faboslav.friendsandfoes.common.entity.IceologerIceChunkEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

@Environment(EnvType.CLIENT)
public final class IceologerIceChunkRenderState extends EntityRenderState
{
	public IceologerIceChunkEntity iceologerIceChunk;

	public IceologerIceChunkRenderState() {
	}
}
//?}