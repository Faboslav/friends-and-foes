package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;

public final class GlareTeleportToOwnerTask extends Behavior<GlareEntity>
{
	private LivingEntity owner;

	public GlareTeleportToOwnerTask() {
		super(ImmutableMap.of());
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, GlareEntity glare) {
		LivingEntity owner = glare.getOwner();

		return owner != null
			   && !owner.isSpectator()
			   && !glare.isLeashed()
			   && !glare.isOrderedToSit()
			   && !glare.isPassenger()
			   && !(glare.distanceToSqr(owner) < 1024.0D);
	}

	@Override
	protected void start(ServerLevel world, GlareEntity glare, long time) {
		this.owner = glare.getOwner();
		this.tryTeleport(glare);
	}

	private void tryTeleport(GlareEntity glare) {
		BlockPos blockPos = this.owner.blockPosition();

		for (int i = 0; i < 10; ++i) {
			int xOffset = this.getRandomInt(glare, -3, 3);
			int yOffset = this.getRandomInt(glare, -1, 1);
			int zOffset = this.getRandomInt(glare, -3, 3);
			boolean teleportResult = this.tryTeleportTo(glare, blockPos.getX() + xOffset, blockPos.getY() + yOffset, blockPos.getZ() + zOffset);

			if (teleportResult) {
				return;
			}
		}

	}

	private boolean tryTeleportTo(GlareEntity glare, int x, int y, int z) {
		if (Math.abs((double) x - this.owner.getX()) < 2.0D && Math.abs((double) z - this.owner.getZ()) < 2.0D) {
			return false;
		} else if (!this.canTeleportTo(glare, new BlockPos(x, y, z))) {
			return false;
		} else {
			VersionedEntity.moveTo(glare, (double) x + 0.5D, y, (double) z + 0.5D, glare.getYRot(), glare.getXRot());
			glare.getNavigation().stop();
			return true;
		}
	}

	private boolean canTeleportTo(GlareEntity glare, BlockPos pos) {
		BlockPos blockPos = pos.subtract(glare.blockPosition());
		return glare.level().noCollision(glare, glare.getBoundingBox().move(blockPos));
	}

	private int getRandomInt(GlareEntity glare, int min, int max) {
		return glare.getRandom().nextInt(max - min + 1) + min;
	}
}
