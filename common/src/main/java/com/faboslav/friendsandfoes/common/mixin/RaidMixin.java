package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.IceologerEntity;
import com.faboslav.friendsandfoes.common.entity.IllusionerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? >= 1.21.5 {
import net.minecraft.server.level.ServerLevel;
//?}

@Mixin(Raid.class)
public final class RaidMixin
{
	@Inject(
		method = "joinRaid",
		at = @At("HEAD"),
		cancellable = true
	)
	public void friendsandfoes_addRaider(
		//? >= 1.21.5 {
		ServerLevel serverLevel,
		//?}
		int wave,
		Raider raider,
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
