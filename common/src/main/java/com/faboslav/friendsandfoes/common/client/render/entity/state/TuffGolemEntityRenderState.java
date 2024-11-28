/*? >=1.21.2 {*/
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.math.Vec2f;

@Environment(EnvType.CLIENT)
public class TuffGolemEntityRenderState extends LivingEntityRenderState
{
	public boolean isTamed = false;

	public TuffGolemEntityRenderState() {
	}
}
/*?}*/

