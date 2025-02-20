//? >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public final class CrabRenderState extends LivingEntityRenderState
{
	public CrabEntity crab;

	public CrabRenderState() {
	}
}
//?}