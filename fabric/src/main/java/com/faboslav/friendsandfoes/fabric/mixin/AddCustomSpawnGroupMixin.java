package com.faboslav.friendsandfoes.fabric.mixin;

import com.faboslav.friendsandfoes.common.platform.CustomSpawnGroup;
import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MobCategory.class)
public enum AddCustomSpawnGroupMixin
{
	FRIENDSANDFOES_GLARES(
		CustomSpawnGroup.GLARES_NAME,
		//? if >=26.2 {
		"GL",
		//?}
		CustomSpawnGroup.GLARES_SPAWN_CAP,
		CustomSpawnGroup.GLARES_PEACEFUL,
		CustomSpawnGroup.GLARES_RARE,
		CustomSpawnGroup.GLARES_IMMEDIATE_DESPAWN_RANGE
	),

	FRIENDSANDFOES_RASCALS(
		CustomSpawnGroup.RASCALS_NAME,
		//? if >=26.2 {
		"RA",
		//?}
		CustomSpawnGroup.RASCALS_SPAWN_CAP,
		CustomSpawnGroup.RASCALS_PEACEFUL,
		CustomSpawnGroup.RASCALS_RARE,
		CustomSpawnGroup.RASCALS_IMMEDIATE_DESPAWN_RANGE
	);

	@Shadow
	AddCustomSpawnGroupMixin(
		String name,
		//? if >=26.2 {
		String debugAbbreviation,
		//?}
		int max,
		boolean isFriendly,
		boolean isPersistent,
		int despawnDistance
	) {
	}
}