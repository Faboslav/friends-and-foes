package com.faboslav.friendsandfoes.entity.ai.pathing;

import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GlareNavigation extends BirdNavigation
{
	int lastPathNodeBaseLightLevel = 1;

	public GlareNavigation(GlareEntity glare, World world) {
		super(glare, world);
	}

	protected void adjustPath() {
		super.adjustPath();

		GlareEntity glare = (GlareEntity) this.entity;
		World world = this.world;
		boolean isGlareTamed = glare.isTamed();
		boolean isSkyVisible = world.isSkyVisible(
			new BlockPos(
				glare.getX(),
				glare.getY() + 0.5D,
				glare.getZ()
			)
		);
		int glareBlockPosBaseLightLevel = world.getBaseLightLevel(glare.getBlockPos(), 0);
		boolean isGlareInDarkSpot = glareBlockPosBaseLightLevel == 0;

		this.lastPathNodeBaseLightLevel = glareBlockPosBaseLightLevel;

		for (int i = 0; i < this.currentPath.getLength(); ++i) {
			PathNode pathNode = this.currentPath.getNode(i);
			BlockPos pathNodeBlockPos = new BlockPos(pathNode.x, pathNode.y, pathNode.z);
			int pathNodeBaseLightLevel = world.getBaseLightLevel(pathNodeBlockPos, 0);

			if (isGlareTamed || isSkyVisible) {
				continue;
			}

			if (
				isGlareInDarkSpot
				&& pathNodeBaseLightLevel >= this.lastPathNodeBaseLightLevel
			) {
				this.lastPathNodeBaseLightLevel = pathNodeBaseLightLevel;
				continue;
			}

			boolean pathNodeIsDarkSpot = pathNodeBaseLightLevel == 0;

			if (pathNodeIsDarkSpot) {
				this.currentPath.setLength(i);

				return;
			}
		}
	}
}
