package com.faboslav.friendsandfoes.common.entity.pose;

import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;

public interface PoseableEntity<T extends SpecificEntityPose>
{
	void setPose(Pose pose);

	void setPrevPose(Pose pose);

	Pose getPose();

	Pose getPrevPose();

	Level level();

	default void setPrevPose(T pose) {
		if (this.level().isClientSide()) {
			return;
		}

		this.setPrevPose(pose);
	}

	default void setPose(T pose) {
		if (this.level().isClientSide()) {
			return;
		}

		setPose(pose.get());
	}

	default boolean wasInPose(Pose pose) {
		return this.getPrevPose() == pose;
	}

	default boolean wasInPose(T pose) {
		return this.getPrevPose() == pose.get();
	}

	default boolean isInPose(Pose pose) {
		return this.getPose() == pose;
	}

	default boolean isInPose(T pose) {
		return this.getPose() == pose.get();
	}
}