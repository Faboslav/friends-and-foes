//? if >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import com.faboslav.friendsandfoes.common.entity.MoobloomEntity;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

//? if >= 26.1 {
import net.minecraft.client.renderer.block.BlockModelRenderState;
//?}

public final class MoobloomRenderState extends LivingEntityRenderState
{
	public MoobloomEntity moobloom;
	//? if >= 26.1 {
	public final BlockModelRenderState flowerModel;
	//?}

	public MoobloomRenderState() {
		//? if >= 26.1 {
		this.flowerModel = new BlockModelRenderState();
		//?}
	}
}
//?}