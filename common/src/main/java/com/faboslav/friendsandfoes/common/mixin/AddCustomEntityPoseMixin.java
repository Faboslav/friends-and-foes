package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.pose.*;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.world.entity.Pose;

@Mixin(Pose.class)
@SuppressWarnings({"ShadowTarget", "InvokerTarget"})
public final class AddCustomEntityPoseMixin
{
	@Invoker("<init>")
	private static Pose newEntityPose(String internalName, int internalIndex, int index) {
		throw new AssertionError();
	}

	@Shadow
	private static @Final
	@Mutable
	Pose[] $VALUES;

	@Inject(
		method = "<clinit>",
		at = @At(
			value = "FIELD",
			opcode = Opcodes.PUTSTATIC,
			target = "Lnet/minecraft/world/entity/Pose;$VALUES:[Lnet/minecraft/world/entity/Pose;",
			shift = At.Shift.AFTER
		)
	)
	private static void friendsandfoes_addCustomEntityPoses(CallbackInfo ci) {
		var specificEntityPoseEnums = List.of(
			BarnacleEntityPose.class,
			CopperGolemEntityPose.class,
			RascalEntityPose.class,
			TuffGolemEntityPose.class,
			CrabEntityPose.class,
			WildfireEntityPose.class,
			MaulerEntityPose.class,
			PenguinEntityPose.class
		);
		var entityPoses = new ArrayList<>(Arrays.asList($VALUES));
		var lastEntityPose = entityPoses.get(entityPoses.size() - 1);
		var nextEntityPoseInternalIndex = lastEntityPose.ordinal();
		var nextEntityPoseIndex = lastEntityPose.id();

		for (var specificEntityPoseEnum : specificEntityPoseEnums) {
			for (SpecificEntityPose specificEntityPose : specificEntityPoseEnum.getEnumConstants()) {
				var newEntityPose = newEntityPose(
					specificEntityPose.getName(),
					++nextEntityPoseInternalIndex,
					++nextEntityPoseIndex
				);

				entityPoses.add(newEntityPose);
			}
		}

		$VALUES = entityPoses.toArray(new Pose[0]);
	}
}