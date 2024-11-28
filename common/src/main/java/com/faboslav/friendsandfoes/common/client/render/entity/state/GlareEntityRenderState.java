/*? >=1.21.2 {*/
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.IllagerEntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.math.Vec2f;

@Environment(EnvType.CLIENT)
public class GlareEntityRenderState extends LivingEntityRenderState
{
	public boolean isTamed = false;
	public boolean isGrumpy = false;
	public boolean isSitting = false;
	public Vec2f eyesPositionOffset =  new Vec2f(0, 0);

	public GlareEntityRenderState() {
	}
}
/*?}*/

