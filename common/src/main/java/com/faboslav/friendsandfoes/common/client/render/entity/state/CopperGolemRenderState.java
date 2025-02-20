//? >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Crackiness;
import net.minecraft.world.entity.Crackiness.Level;
import net.minecraft.world.level.block.WeatheringCopper;

@Environment(EnvType.CLIENT)
public final class CopperGolemRenderState extends LivingEntityRenderState
{
	public CopperGolemEntity copperGolem;

	public CopperGolemRenderState() {
	}
}
//?}