package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.pose.CopperGolemEntityPose;
import com.faboslav.friendsandfoes.common.entity.pose.CrabEntityPose;
import com.faboslav.friendsandfoes.common.entity.pose.RascalEntityPose;
import com.faboslav.friendsandfoes.common.entity.pose.TuffGolemEntityPose;
import net.minecraft.entity.EntityPose;
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

@Mixin(EntityPose.class)
@SuppressWarnings({"ShadowTarget", "InvokerTarget"})
public final class AddCustomEntityPoseMixin
{
	@Invoker("<init>")
	private static EntityPose newEntityPose(String internalName, int internalIndex, int index) {
		throw new AssertionError();
	}

	@Shadow
	private static @Final
	@Mutable
	EntityPose[] field_18083;

	@Inject(
		method = "<clinit>",
		at = @At(
			value = "FIELD",
			opcode = Opcodes.PUTSTATIC,
			target = "Lnet/minecraft/entity/EntityPose;field_18083:[Lnet/minecraft/entity/EntityPose;",
			shift = At.Shift.AFTER
		)
	)
	private static void friendsandfoes_addCustomEntityPoses(CallbackInfo ci) {
		var entityPoses = new ArrayList<>(Arrays.asList(field_18083));
		var lastEntityPose = entityPoses.get(entityPoses.size() - 1);
		var nextEntityPoseInternalIndex = lastEntityPose.ordinal();
		var nextEntityPoseIndex = lastEntityPose.getIndex();

		for (CopperGolemEntityPose copperGolemEntityPose : CopperGolemEntityPose.values()) {
			var newEntityPose = newEntityPose(
				copperGolemEntityPose.getName(),
				++nextEntityPoseInternalIndex,
				++nextEntityPoseIndex
			);

			copperGolemEntityPose.setIndex(nextEntityPoseIndex);
			entityPoses.add(newEntityPose);
		}

		for (RascalEntityPose rascalEntityPose : RascalEntityPose.values()) {
			var newEntityPose = newEntityPose(
				rascalEntityPose.getName(),
				++nextEntityPoseInternalIndex,
				++nextEntityPoseIndex
			);

			rascalEntityPose.setIndex(nextEntityPoseIndex);
			entityPoses.add(newEntityPose);
		}

		for (TuffGolemEntityPose tuffGolemEntityPose : TuffGolemEntityPose.values()) {
			var newEntityPose = newEntityPose(
				tuffGolemEntityPose.getName(),
				++nextEntityPoseInternalIndex,
				++nextEntityPoseIndex
			);

			tuffGolemEntityPose.setIndex(nextEntityPoseIndex);
			entityPoses.add(newEntityPose);
		}

		for (CrabEntityPose crabEntityPose : CrabEntityPose.values()) {
			var newEntityPose = newEntityPose(
				crabEntityPose.getName(),
				++nextEntityPoseInternalIndex,
				++nextEntityPoseIndex
			);

			entityPoses.add(newEntityPose);
		}

		field_18083 = entityPoses.toArray(new EntityPose[0]);
	}
}