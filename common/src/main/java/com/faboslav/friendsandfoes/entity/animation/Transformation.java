package com.faboslav.friendsandfoes.entity.animation;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public record Transformation(
	Transformation.Type type,
	Keyframe... keyframes
)
{
	public Transformation(
		Transformation.Type type,
		Keyframe... keyframes
	) {
		this.type = type;
		this.keyframes = keyframes;
	}

	public Transformation.Type type() {
		return this.type;
	}

	public Keyframe[] keyframes() {
		return this.keyframes;
	}

	public enum Type
	{
		TRANSLATE,
		ROTATE,
		SCALE
	}

	public static class Interpolations
	{
		public static final Transformation.Interpolation LINEAR = (vec3f, delta, keyframes, start, end, f) -> {
			Vec3f vec3f2 = keyframes[start].target();
			Vec3f vec3f3 = keyframes[end].target();
			vec3f.set(MathHelper.lerp(delta, vec3f2.getX(), vec3f3.getX()) * f, MathHelper.lerp(delta, vec3f2.getY(), vec3f3.getY()) * f, MathHelper.lerp(delta, vec3f2.getZ(), vec3f3.getZ()) * f);
			return vec3f;
		};
		public static final Transformation.Interpolation CUBIC = (vec3f, delta, keyframes, start, end, f) -> {
			Vec3f vec3f2 = keyframes[Math.max(0, start - 1)].target();
			Vec3f vec3f3 = keyframes[start].target();
			Vec3f vec3f4 = keyframes[end].target();
			Vec3f vec3f5 = keyframes[Math.min(keyframes.length - 1, end + 1)].target();
			vec3f.set(MathHelper.catmullRom(delta, vec3f2.getX(), vec3f3.getX(), vec3f4.getX(), vec3f5.getX()) * f, MathHelper.catmullRom(delta, vec3f2.getY(), vec3f3.getY(), vec3f4.getY(), vec3f5.getY()) * f, MathHelper.catmullRom(delta, vec3f2.getZ(), vec3f3.getZ(), vec3f4.getZ(), vec3f5.getZ()) * f);
			return vec3f;
		};

		public Interpolations() {
		}
	}

	public interface Interpolation
	{
		Vec3f apply(Vec3f vec3f, float delta, Keyframe[] keyframes, int start, int end, float f);
	}
}
