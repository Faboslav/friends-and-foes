package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.platform.CustomSpawnGroup;
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
import net.minecraft.world.entity.MobCategory;

@Mixin(MobCategory.class)
@SuppressWarnings({"ShadowTarget", "InvokerTarget"})
public final class AddCustomSpawnGroupMixin
{
	@Invoker("<init>")
	public static MobCategory newSpawnGroup(
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
	MobCategory[] $VALUES;

	@Inject(
		method = "<clinit>",
		at = @At(
			value = "FIELD",
			opcode = Opcodes.PUTSTATIC,
			target = "Lnet/minecraft/world/entity/MobCategory;$VALUES:[Lnet/minecraft/world/entity/MobCategory;",
			shift = At.Shift.AFTER
		)
	)
	private static void friendsandfoes_addCustomSpawnGroup(CallbackInfo ci) {
		var spawnGroups = new ArrayList<>(Arrays.asList($VALUES));
		var lastSpawnGroup = spawnGroups.get(spawnGroups.size() - 1);

		var glaresSpawnGroup = newSpawnGroup(
			CustomSpawnGroup.GLARES_INTERNAL_NAME,
			lastSpawnGroup.ordinal() + 1,
			CustomSpawnGroup.GLARES_NAME,
			CustomSpawnGroup.GLARES_SPAWN_CAP,
			CustomSpawnGroup.GLARES_PEACEFUL,
			CustomSpawnGroup.GLARES_RARE,
			CustomSpawnGroup.GLARES_IMMEDIATE_DESPAWN_RANGE
		);
		CustomSpawnGroup.GLARES = glaresSpawnGroup;
		spawnGroups.add(glaresSpawnGroup);

		var rascalsSpawnGroup = newSpawnGroup(
			CustomSpawnGroup.RASCALS_INTERNAL_NAME,
			lastSpawnGroup.ordinal() + 2,
			CustomSpawnGroup.RASCALS_NAME,
			CustomSpawnGroup.RASCALS_SPAWN_CAP,
			CustomSpawnGroup.RASCALS_PEACEFUL,
			CustomSpawnGroup.RASCALS_RARE,
			CustomSpawnGroup.RASCALS_IMMEDIATE_DESPAWN_RANGE
		);
		CustomSpawnGroup.RASCALS = rascalsSpawnGroup;
		spawnGroups.add(rascalsSpawnGroup);

		$VALUES = spawnGroups.toArray(new MobCategory[0]);
	}
}