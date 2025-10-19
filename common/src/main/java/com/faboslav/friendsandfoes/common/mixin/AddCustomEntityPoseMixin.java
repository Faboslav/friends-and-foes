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
import net.minecraft.world.entity.Pose;

@Mixin(Pose.class)
@SuppressWarnings({"ShadowTarget", "InvokerTarget"})
public final class AddCustomEntityPoseMixin
{
	@Invoker("<init>")
	private static Pose newEntityPose(String internalName, int internalIndex, int index, String name) {
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
		var entityPoses = new ArrayList<>(Arrays.asList($VALUES));
		var lastEntityPose = entityPoses.get(entityPoses.size() - 1);
		var nextEntityPoseInternalIndex = lastEntityPose.ordinal();
		var nextEntityPoseIndex = lastEntityPose.id();

		//? if <= 1.21.8 {
		/*for (CopperGolemEntityPose copperGolemEntityPose : CopperGolemEntityPose.values()) {
			var newEntityPose = newEntityPose(
				copperGolemEntityPose.getName(),
				++nextEntityPoseInternalIndex,
				++nextEntityPoseIndex,
				copperGolemEntityPose.getName().toLowerCase()
			);

			copperGolemEntityPose.setIndex(nextEntityPoseIndex);
			entityPoses.add(newEntityPose);
		}
		*///?}

		for (RascalEntityPose rascalEntityPose : RascalEntityPose.values()) {
			var newEntityPose = newEntityPose(
				rascalEntityPose.getName(),
				++nextEntityPoseInternalIndex,
				++nextEntityPoseIndex,
				rascalEntityPose.getName().toLowerCase()
			);

			rascalEntityPose.setIndex(nextEntityPoseIndex);
			entityPoses.add(newEntityPose);
		}

		for (TuffGolemEntityPose tuffGolemEntityPose : TuffGolemEntityPose.values()) {
			var newEntityPose = newEntityPose(
				tuffGolemEntityPose.getName(),
				++nextEntityPoseInternalIndex,
				++nextEntityPoseIndex,
				tuffGolemEntityPose.getName().toLowerCase()
			);

			tuffGolemEntityPose.setIndex(nextEntityPoseIndex);
			entityPoses.add(newEntityPose);
		}

		for (CrabEntityPose crabEntityPose : CrabEntityPose.values()) {
			var newEntityPose = newEntityPose(
				crabEntityPose.getName(),
				++nextEntityPoseInternalIndex,
				++nextEntityPoseIndex,
				crabEntityPose.getName().toLowerCase()
			);

			entityPoses.add(newEntityPose);
		}

		for (WildfireEntityPose wildfireEntityPose : WildfireEntityPose.values()) {
			var newEntityPose = newEntityPose(
				wildfireEntityPose.getName(),
				++nextEntityPoseInternalIndex,
				++nextEntityPoseIndex,
				wildfireEntityPose.getName().toLowerCase()
			);

			entityPoses.add(newEntityPose);
		}

		for (MaulerEntityPose maulerEntityPose : MaulerEntityPose.values()) {
			var newEntityPose = newEntityPose(
				maulerEntityPose.getName(),
				++nextEntityPoseInternalIndex,
				++nextEntityPoseIndex,
				maulerEntityPose.getName().toLowerCase()
			);

			entityPoses.add(newEntityPose);
		}

		$VALUES = entityPoses.toArray(new Pose[0]);
	}
}