package com.faboslav.friendsandfoes.mixin.quilt;

import com.faboslav.friendsandfoes.platform.CustomSpawnGroup;
import com.faboslav.friendsandfoes.platform.fabric.CustomSpawnGroupImpl;
import net.minecraft.entity.SpawnGroup;
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

@Mixin(SpawnGroup.class)
@SuppressWarnings({"ShadowTarget", "InvokerTarget"})
public final class AddCustomSpawnGroupMixin
{
	@Invoker("<init>")
	public static SpawnGroup newSpawnGroup(
		String internalName,
		int internalId,
		String name,
		int spawnCap,
		boolean peaceful,
		boolean rare,
		int immediateDespawnRange
	) {
		throw new AssertionError();
	}

	@Shadow
	private static @Final
	@Mutable
	SpawnGroup[] field_6301;

	@Inject(
		method = "<clinit>",
		at = @At(
			value = "FIELD",
			opcode = Opcodes.PUTSTATIC,
			target = "Lnet/minecraft/entity/SpawnGroup;field_6301:[Lnet/minecraft/entity/SpawnGroup;",
			shift = At.Shift.AFTER
		)
	)
	private static void friendsandfoes_addCustomSpawnGroup(CallbackInfo ci) {
		var spawnGroups = new ArrayList<>(Arrays.asList(field_6301));
		var lastSpawnGroup = spawnGroups.get(spawnGroups.size() - 1);

		var glaresSpawnGroup = newSpawnGroup(
			CustomSpawnGroup.GLARES_INTERNAL_NAME,
			lastSpawnGroup.ordinal() + 1,
			CustomSpawnGroup.NAME,
			CustomSpawnGroup.SPAWN_CAP,
			CustomSpawnGroup.PEACEFUL,
			CustomSpawnGroup.RARE,
			CustomSpawnGroup.IMMEDIATE_DESPAWN_RANGE
		);
		CustomSpawnGroupImpl.GLARES = glaresSpawnGroup;
		spawnGroups.add(glaresSpawnGroup);

		field_6301 = spawnGroups.toArray(new SpawnGroup[0]);
	}
}