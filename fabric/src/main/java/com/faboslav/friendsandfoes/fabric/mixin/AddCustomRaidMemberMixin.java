package com.faboslav.friendsandfoes.fabric.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.util.CustomRaidMember;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Raid.RaiderType.class)
public enum AddCustomRaidMemberMixin
{
	FRIENDSANDFOES_ICEOLOGER(
		FriendsAndFoesEntityTypes.ICEOLOGER.get(),
		CustomRaidMember.ICEOLOGER_COUNT_IN_WAVE
	),
	FRIENDSANDFOES_ILLUSIONER(
		FriendsAndFoesEntityTypes.ILLUSIONER.get(),
		CustomRaidMember.ILLUSIONER_COUNT_IN_WAVE
	);

	@Shadow
	AddCustomRaidMemberMixin(
		EntityType<? extends Raider> entityType,
		int[] spawnsPerWaveBeforeBonus
	) {
	}
}