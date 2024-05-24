package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.entity.pose.CustomEntityPose;
import net.minecraft.entity.EntityPose;
import net.minecraft.world.World;

public interface PoseableEntity<T extends CustomEntityPose>
{
	void setPose(EntityPose pose);

	EntityPose getPose();

	World getWorld();

	default void setPose(T pose) {
		if (this.getWorld().isClient()) {
			return;
		}

		setPose(pose.get());
	}

	default boolean isInPose(EntityPose pose) {
		return this.getPose() == pose;
	}

	default boolean isInPose(T pose) {
		return this.getPose() == pose.get();
	}
}
