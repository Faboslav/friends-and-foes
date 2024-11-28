/*? >=1.21.2 {*/
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.IllagerEntityRenderState;

@Environment(EnvType.CLIENT)
public class IceologerEntityRenderState extends IllagerEntityRenderState
{
	public boolean spellcasting;

	public IceologerEntityRenderState() {
	}
}
/*?}*/

