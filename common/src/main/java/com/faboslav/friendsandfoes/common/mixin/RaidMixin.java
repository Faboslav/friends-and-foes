package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.IceologerEntity;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raid.class)
public final class RaidMixin
{
	@Inject(
		method = "addRaider",
		at = @At("HEAD"),
		cancellable = true
	)
	public void friendsandfoes_addRaider(
		int wave,
		RaiderEntity raider,
		BlockPos pos,
		boolean existing,
		CallbackInfo ci
	) {
		if (
			(
				raider instanceof IllusionerEntity
				&& (
					!FriendsAndFoes.getConfig().enableIllusioner
					|| !FriendsAndFoes.getConfig().enableIllusionerInRaids
				)
			) || (
				raider instanceof IceologerEntity
				&& (
					!FriendsAndFoes.getConfig().enableIceologer
					|| !FriendsAndFoes.getConfig().enableIceologerInRaids
				)
			)
		) {
			ci.cancel();
		}
	}
}
