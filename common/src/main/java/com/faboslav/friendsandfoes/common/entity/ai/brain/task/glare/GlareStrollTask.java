package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;

public class GlareStrollTask extends Behavior<GlareEntity>
{
	public GlareStrollTask() {
		super(
			Map.of(
				MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT
			)
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, GlareEntity glare) {
		return glare.getNavigation().isDone();
	}

	@Override
	protected void start(ServerLevel world, GlareEntity glare, long time) {
		this.updateCachedPathHolder(glare);
		glare.getNavigation().moveTo(glare.cachedPathHolder.cachedPath, 1);
	}

	@Override
	protected boolean canStillUse(ServerLevel world, GlareEntity glare, long time) {
		return glare.getNavigation().isInProgress();
	}

	public void updateCachedPathHolder(GlareEntity glare) {
		if (
			glare.cachedPathHolder.pathTimer > 20
			|| glare.cachedPathHolder.cachedPath == null
			|| (glare.getSpeed() <= 0.05d && glare.cachedPathHolder.pathTimer > 5)
			|| glare.blockPosition().distManhattan(glare.cachedPathHolder.cachedPath.getTarget()) <= 4
		) {
			BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().set(glare.blockPosition());
			Level world = glare.level();
			int currentGroundBlockPosY = this.getGroundBlockPosition(glare).getY();
			int blockRange;
			boolean isSkyVisible = world.canSeeSky(glare.blockPosition());
			boolean isCloseToGround = Math.abs(currentGroundBlockPosY - glare.getY()) < 3;

			for (int attempt = 0; attempt < 10; attempt++) {
				blockRange = 12 - attempt;

				int x = glare.blockPosition().getX() + glare.getRandom().nextIntBetweenInclusive(-blockRange, blockRange);
				int y;
				int z = glare.blockPosition().getZ() + glare.getRandom().nextIntBetweenInclusive(-blockRange, blockRange);

				if (isSkyVisible) {
					if (isCloseToGround) {
						y = glare.getRandom().nextIntBetweenInclusive(currentGroundBlockPosY, currentGroundBlockPosY + blockRange / 2);
					} else {
						y = glare.getRandom().nextIntBetweenInclusive(currentGroundBlockPosY - blockRange / 4, currentGroundBlockPosY + blockRange / 4);
					}
				} else {
					if (isCloseToGround) {
						y = glare.getRandom().nextIntBetweenInclusive(currentGroundBlockPosY, currentGroundBlockPosY + blockRange);
					} else {
						y = glare.getRandom().nextIntBetweenInclusive(currentGroundBlockPosY - blockRange / 2, currentGroundBlockPosY + blockRange / 2);
					}
				}

				mutable.set(glare.blockPosition()).set(x, y, z);

				if (world.getBlockState(mutable).isAir()) {
					break;
				}
			}

			Path newPath = glare.getNavigation().createPath(mutable, 1);

			glare.cachedPathHolder.cachedPath = newPath;
			glare.cachedPathHolder.pathTimer = 0;
		} else {
			glare.cachedPathHolder.pathTimer += 1;
		}
	}

	private BlockPos getGroundBlockPosition(GlareEntity glare) {
		Level world = glare.level();
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().set(glare.blockPosition());
		//? >=1.21.3 {
		int worldBottomY = glare.level().getMinY();
		//?} else {
		/*int worldBottomY = glare.level().getMinBuildHeight();
		*///?}
		BlockState currentMutableBlockState = world.getBlockState(mutable);

		while (
			currentMutableBlockState.isAir()
			&& mutable.getY() > worldBottomY
		) {
			mutable.move(Direction.DOWN);
			currentMutableBlockState = world.getBlockState(mutable);
		}

		return mutable;
	}
}
