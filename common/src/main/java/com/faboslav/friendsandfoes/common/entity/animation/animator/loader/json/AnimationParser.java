package com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json;

import com.faboslav.friendsandfoes.common.entity.animation.animator.Keyframe;
import com.faboslav.friendsandfoes.common.entity.animation.*;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.AnimationKeyframeTarget;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.AnimationTarget;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.KeyDispatchCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;


import java.util.*;
import java.util.function.UnaryOperator;

/**
 * A parser for parsing JSON-based entity animation files.
 *
 * Animation loading related code is based on NeoForge code
 *
 * @author NeoForge team
 * <a href="https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation">https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation</a>
 */
public final class AnimationParser {
	/**
	 * {@snippet lang = JSON : "minecraft:rotation"
	 * }
	 */
	private static final Codec<AnimationTarget> TARGET_CODEC = ResourceLocation.CODEC
		.flatXmap(
			name -> Optional.ofNullable(AnimationTypeManager.getTarget(name))
				.map(DataResult::success)
				.orElseGet(() -> DataResult.error(() -> String.format(
					Locale.ENGLISH, "Animation target '%s' not found. Registered targets: %s",
					name, AnimationTypeManager.getTargetList()))),
			target -> Optional.ofNullable(AnimationTypeManager.getTargetName(target))
				.map(DataResult::success)
				.orElseGet(() -> DataResult.error(() -> String.format(
					Locale.ENGLISH, "Unregistered animation target '%s'. Registered targets: %s",
					target, AnimationTypeManager.getTargetList()))));

	/**
	 * {@snippet lang = JSON : "minecraft:linear"
	 * }
	 */
	private static final Codec<AnimationChannel.Interpolation> INTERPOLATION_CODEC = ResourceLocation.CODEC
		.flatXmap(
			name -> Optional.ofNullable(AnimationTypeManager.getInterpolation(name))
				.map(DataResult::success)
				.orElseGet(() -> DataResult.error(() -> String.format(
					Locale.ENGLISH, "Animation interpolation '%s' not found. Registered interpolations: %s",
					name, AnimationTypeManager.getInterpolationList()))),
			target -> Optional.ofNullable(AnimationTypeManager.getInterpolationName(target))
				.map(DataResult::success)
				.orElseGet(() -> DataResult.error(() -> String.format(
					Locale.ENGLISH, "Unregistered animation interpolation '%s'. Registered interpolations: %s",
					target, AnimationTypeManager.getInterpolationList()))));

	/**
	 * {@snippet lang = JSON :
	 * {
	 *   "keyframes": [
	 *     {
	 *       "timestamp": 0.5,
	 *       "target": [22.5, 0.0, 0.0],
	 *       "interpolation": "minecraft:linear"
	 *     }
	 *   ],
	 *   "target": "minecraft:rotation"
	 * }
	 * }
	 */
	public static final MapCodec<AnimationChannel> CHANNEL_CODEC = new KeyDispatchCodec<>(
		"target",
		TARGET_CODEC,
		channel -> Optional.ofNullable(AnimationTypeManager.getTargetFromChannelTarget(channel.target()))
			.map(DataResult::success)
			.orElseGet(() -> DataResult.error(() -> String.format(
				Locale.ENGLISH, "Unregistered animation channel target '%s'. Registered targets: %s",
				channel.target(), AnimationTypeManager.getTargetList()))),
		target -> DataResult.success(
			Optional.ofNullable(AnimationTypeManager.getKeyframeCodec(target))
				.orElseGet(() -> keyframeCodec(target))
				.listOf()
				.xmap(
					keyframes -> new AnimationChannel(target.channelTarget(), keyframes.toArray(Keyframe[]::new)),
					channel -> Arrays.asList(channel.keyframes()))
				.fieldOf("keyframes")));

	/**
	 * {@snippet lang = JSON :
	 * {
	 *   "bone": "head",
	 *   "keyframes": [
	 *     {
	 *       "timestamp": 0.5,
	 *       "target": [22.5, 0.0, 0.0],
	 *       "interpolation": "minecraft:linear"
	 *     }
	 *   ],
	 *   "target": "minecraft:rotation"
	 * }
	 * }
	 */
	private static final Codec<Pair<String, AnimationChannel>> NAMED_CHANNEL_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			Codec.STRING.fieldOf("bone").forGetter(Pair::key),
			CHANNEL_CODEC.forGetter(Pair::value)).apply(instance, Pair::of));

	/**
	 * {@snippet lang = JSON :
	 * {
	 *   "length": 1.125,
	 *   "loop": true,
	 *   "animations": [
	 *     {
	 *       "bone": "head",
	 *       "keyframes": [
	 *         {
	 *           "timestamp": 0.5,
	 *           "target": [22.5, 0.0, 0.0],
	 *           "interpolation": "minecraft:linear"
	 *         }
	 *       ]
	 *     }
	 *   ]
	 * }
	 * }
	 */
	public static final Codec<AnimationDefinition> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				Codec.FLOAT.fieldOf("length").forGetter(AnimationDefinition::lengthInSeconds),
				Codec.BOOL.optionalFieldOf("loop", false).forGetter(AnimationDefinition::looping),
				NAMED_CHANNEL_CODEC.listOf()
					.<Map<String, List<AnimationChannel>>>xmap(
						list -> {
							final var result = new HashMap<String, List<AnimationChannel>>();
							for (final var animation : list) {
								result.computeIfAbsent(animation.key(), k -> new ArrayList<>()).add(animation.value());
							}
							return result;
						},
						map -> {
							final var result = new ArrayList<Pair<String, AnimationChannel>>();
							for (final var entry : map.entrySet()) {
								for (final var channel : entry.getValue()) {
									result.add(Pair.of(entry.getKey(), channel));
								}
							}
							return result;
						})
					.fieldOf("animations")
					.forGetter(AnimationDefinition::boneAnimations))
			.apply(instance, (lengthInSeconds, looping, boneAnimations) -> new AnimationDefinition("", lengthInSeconds, looping, boneAnimations)));

	private AnimationParser() {}

	/**
	 * {@snippet lang = JSON :
	 * {
	 *   "timestamp": 0.5,
	 *   "target": [22.5, 0.0, 0.0],
	 *   "interpolation": "minecraft:linear"
	 * }
	 * }
	 */
	static Codec<Keyframe> keyframeCodec(AnimationTarget target) {
		return RecordCodecBuilder.create(
			instance -> instance.group(
				Codec.FLOAT.fieldOf("timestamp").forGetter(Keyframe::timestamp),
				ExtraCodecs.VECTOR3F
					.xmap(
						keyframeTargetToUnaryOp(target.keyframeTarget()),
						keyframeTargetToUnaryOp(target.inverseKeyframeTarget()))
					.fieldOf("target")
					.forGetter(Keyframe::target),
				INTERPOLATION_CODEC.fieldOf("interpolation").forGetter(Keyframe::interpolation)).apply(instance, Keyframe::new));
	}

	private static UnaryOperator<Vector3f> keyframeTargetToUnaryOp(AnimationKeyframeTarget target) {
		return vec -> target.apply(vec.x, vec.y, vec.z);
	}
}