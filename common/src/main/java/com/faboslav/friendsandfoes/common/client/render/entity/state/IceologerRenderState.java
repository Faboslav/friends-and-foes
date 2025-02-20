//? >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;

@Environment(EnvType.CLIENT)
public final class IceologerRenderState extends IllagerRenderState
{
	public boolean isCastingSpell;
}
//?}