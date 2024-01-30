package com.faboslav.friendsandfoes.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.entity.GlareEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public final class GlareTeleportToOwnerTask extends Task<GlareEntity>
{
	private LivingEntity owner;

	public GlareTeleportToOwnerTask() {
		super(ImmutableMap.of());
	}

	@Override
	protected boolean shouldRun(ServerWorld world, GlareEntity glare) {
		LivingEntity owner = glare.getOwner();

		if (
			owner == null
			|| owner.isSpectator()
			|| glare.isLeashed()
			|| glare.isSitting()
			|| glare.hasVehicle()
			|| glare.squaredDistanceTo(owner) < 256.0D
		) {
			return false;
		}

		return true;
	}

	@Override
	protected void run(ServerWorld world, GlareEntity glare, long time) {
		this.owner = glare.getOwner();
		this.tryTeleport(glare);
	}

	private void tryTeleport(GlareEntity glare) {
		BlockPos blockPos = this.owner.getBlockPos();

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
			glare.refreshPositionAndAngles((double) x + 0.5D, y, (double) z + 0.5D, glare.getYaw(), glare.getPitch());
			glare.getNavigation().stop();
			return true;
		}
	}

	private boolean canTeleportTo(GlareEntity glare, BlockPos pos) {
		BlockPos blockPos = pos.subtract(glare.getBlockPos());
		return glare.getWorld().isSpaceEmpty(glare, glare.getBoundingBox().offset(blockPos));
	}

	private int getRandomInt(GlareEntity glare, int min, int max) {
		return glare.getRandom().nextInt(max - min + 1) + min;
	}
}
