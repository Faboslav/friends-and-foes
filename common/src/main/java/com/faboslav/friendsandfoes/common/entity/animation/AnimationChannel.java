package com.faboslav.friendsandfoes.common.entity.animation;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public record AnimationChannel(Target target, Keyframe... keyframes) {
	public AnimationChannel(Target target, Keyframe... keyframes) {
		this.target = target;
		this.keyframes = keyframes;
	}

	public Target target() {
		return this.target;
	}

	public Keyframe[] keyframes() {
		return this.keyframes;
	}
	
	public interface Target {
		void apply(ModelPart modelPart, Vector3f vector3f);
	}
	
	public static class Interpolations {
		public static final Interpolation LINEAR = (vector3f, f, keyframes, i, j, g) -> {
			Vector3f vector3f2 = keyframes[i].target();
			Vector3f vector3f3 = keyframes[j].target();
			return vector3f2.lerp(vector3f3, f, vector3f).mul(g);
		};
		public static final Interpolation CATMULLROM = (vector3f, f, keyframes, i, j, g) -> {
			Vector3f vector3f2 = keyframes[Math.max(0, i - 1)].target();
			Vector3f vector3f3 = keyframes[i].target();
			Vector3f vector3f4 = keyframes[j].target();
			Vector3f vector3f5 = keyframes[Math.min(keyframes.length - 1, j + 1)].target();
			vector3f.set(Mth.catmullrom(f, vector3f2.x(), vector3f3.x(), vector3f4.x(), vector3f5.x()) * g, Mth.catmullrom(f, vector3f2.y(), vector3f3.y(), vector3f4.y(), vector3f5.y()) * g, Mth.catmullrom(f, vector3f2.z(), vector3f3.z(), vector3f4.z(), vector3f5.z()) * g);
			return vector3f;
		};

		public Interpolations() {
		}
	}
	
	public static class Targets {
		public static final Target POSITION = ModelPart::offsetPos;
		public static final Target ROTATION = ModelPart::offsetRotation;
		public static final Target SCALE = ModelPart::offsetScale;

		public Targets() {
		}
	}
	
	public interface Interpolation {
		Vector3f apply(Vector3f vector3f, float f, Keyframe[] keyframes, int i, int j, float g);
	}
}