package com.faboslav.friendsandfoes.common.client.render.entity.animation.animator;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import org.joml.Vector3f;

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
		Vector3f position
	) {
		modelPart.setPivot((float) position.x(), (float) position.y(), (float) position.z());
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
		Vector3f rotation
	) {
		modelPart.setAngles((float) rotation.x(), (float) rotation.y(), (float) rotation.z());
	}
}
