package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.IceologerEntity;
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
					FriendsAndFoes.getConfig().enableIllusioner == false
					|| FriendsAndFoes.getConfig().enableIllusionerInRaids == false
				)
			) || (
				raider instanceof IceologerEntity
				&& (
					FriendsAndFoes.getConfig().enableIceologer == false
					|| FriendsAndFoes.getConfig().enableIceologerInRaids == false
				)
			)
		) {
			ci.cancel();
		}
	}
}
