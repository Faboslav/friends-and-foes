//? >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public final class TuffGolemRenderState extends LivingEntityRenderState
{
	public TuffGolemEntity tuffGolem;
	public float partialTick;

	public TuffGolemRenderState() {
	}
}
//?}