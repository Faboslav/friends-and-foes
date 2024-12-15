package com.faboslav.friendsandfoes.common.client.render.entity.animation.animator;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public final class ModelPartAnimator
{
	public static void setXPosition(ModelPart modelPart, float x) {
		modelPart.x = x;
	}

	public static void setYPosition(ModelPart modelPart, float y) {
		modelPart.y = y;
	}

	public static void setZPosition(ModelPart modelPart, float z) {
		modelPart.z = z;
	}

	public static void setPosition(
		ModelPart modelPart,
		Vector3f position
	) {
		modelPart.setPos(position.x(), position.y(), position.z());
	}

	public static void setXRotation(ModelPart modelPart, float x) {
		modelPart.xRot = x;
	}

	public static void setYRotation(ModelPart modelPart, float y) {
		modelPart.yRot = y;
	}

	public static void setZRotation(ModelPart modelPart, float z) {
		modelPart.zRot = z;
	}

	public static void setRotation(
		ModelPart modelPart,
		Vector3f rotation
	) {
		modelPart.setRotation(rotation.x(), rotation.y(), rotation.z());
	}
}
