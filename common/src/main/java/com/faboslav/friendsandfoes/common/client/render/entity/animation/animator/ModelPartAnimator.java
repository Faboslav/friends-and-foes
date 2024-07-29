package com.faboslav.friendsandfoes.common.client.render.entity.animation.animator;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public final class ModelPartAnimator
{
	public static void setXPosition(ModelPart modelPart, float x) {
		modelPart.pivotX = x;
	}

	public static void setYPosition(ModelPart modelPart, float y) {
		modelPart.pivotY = y;
	}

	public static void setZPosition(ModelPart modelPart, float z) {
		modelPart.pivotZ = z;
	}

	public static void setPosition(
		ModelPart modelPart,
		Vec3f position
	) {
		modelPart.setPivot(position.getX(), position.getY(), position.getZ());
	}

	public static void setXRotation(ModelPart modelPart, float x) {
		modelPart.pitch = x;
	}

	public static void setYRotation(ModelPart modelPart, float y) {
		modelPart.yaw = y;
	}

	public static void setZRotation(ModelPart modelPart, float z) {
		modelPart.roll = z;
	}

	public static void setRotation(
		ModelPart modelPart,
		Vec3f rotation
	) {
		modelPart.setAngles(rotation.getX(), rotation.getY(), rotation.getZ());
	}
}
