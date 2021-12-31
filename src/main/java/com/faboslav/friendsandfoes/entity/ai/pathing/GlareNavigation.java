package com.faboslav.friendsandfoes.entity.ai.pathing;

import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class GlareNavigation extends BirdNavigation
{
	GlareEntity glare;

	public GlareNavigation(GlareEntity glare, World world) {
		super(glare, world);

		this.glare = glare;
	}

	protected void adjustPath() {
		super.adjustPath();

		for (int i = 0; i < this.currentPath.getLength(); ++i) {
			PathNode pathNode = this.currentPath.getNode(i);

			boolean isGlareTamed = this.glare.isTamed();
			boolean isSkyVisible = this.world.isSkyVisible(
				new BlockPos(
					this.entity.getX(),
					this.entity.getY() + 0.5D,
					this.entity.getZ()
				)
			);
			boolean isBlockBlockLightLevelDark = this.world.getLightLevel(
				LightType.BLOCK,
				new BlockPos(pathNode.x, pathNode.y, pathNode.z)
			) == 0;
			boolean isBlockSkyLevelDark = this.world.getLightLevel(
				LightType.SKY,
				new BlockPos(pathNode.x, pathNode.y, pathNode.z)
			) == 0;

			if (
				!isGlareTamed
				&& !isSkyVisible
				&& isBlockBlockLightLevelDark
				&& isBlockSkyLevelDark
			) {
				this.currentPath.setLength(i);
				return;
			}
		}
	}
}
