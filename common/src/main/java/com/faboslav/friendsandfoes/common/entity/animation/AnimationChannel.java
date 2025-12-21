package com.faboslav.friendsandfoes.common.entity.animation;

import com.faboslav.friendsandfoes.common.entity.animation.animator.Keyframe;
import net.minecraft.util.Mth;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public record AnimationChannel(Target target, Keyframe... keyframes) {

	public static class Interpolations {
		public static final AnimationChannel.Interpolation LINEAR = (vector3f, f, keyframes, i, j, g) -> {
			Vector3fc vector3fc = keyframes[i].postTarget();
			Vector3fc vector3fc2 = keyframes[j].preTarget();
			return vector3fc.lerp(vector3fc2, f, vector3f).mul(g);
		};
		public static final AnimationChannel.Interpolation CATMULLROM = (vector3f, f, keyframes, i, j, g) -> {
			Vector3fc vector3fc = keyframes[Math.max(0, i - 1)].postTarget();
			Vector3fc vector3fc2 = keyframes[i].postTarget();
			Vector3fc vector3fc3 = keyframes[j].postTarget();
			Vector3fc vector3fc4 = keyframes[Math.min(keyframes.length - 1, j + 1)].postTarget();
			vector3f.set(Mth.catmullrom(f, vector3fc.x(), vector3fc2.x(), vector3fc3.x(), vector3fc4.x()) * g, Mth.catmullrom(f, vector3fc.y(), vector3fc2.y(), vector3fc3.y(), vector3fc4.y()) * g, Mth.catmullrom(f, vector3fc.z(), vector3fc2.z(), vector3fc3.z(), vector3fc4.z()) * g);
			return vector3f;
		};
	}

	public interface Interpolation {
		Vector3f apply(Vector3f vector3f, float f, Keyframe[] keyframes, int i, int j, float g);
	}

	public enum Target {
		POSITION,
		ROTATION,
		SCALE;
	}
}
