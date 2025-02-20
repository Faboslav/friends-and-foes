//? >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import com.faboslav.friendsandfoes.common.entity.MoobloomEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public final class MoobloomRenderState extends LivingEntityRenderState
{
	public MoobloomEntity moobloom;

	public MoobloomRenderState() {
	}
}
//?}