package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.IceologerEntity;
import com.faboslav.friendsandfoes.common.entity.IllusionerEntity;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Raid.class)
public final class RaidMixin
{
	@WrapMethod(
		method = "joinRaid"
	)
	public void friendsandfoes_addRaider(

		int wave,
		Raider raider,
		BlockPos pos,
		boolean isRecruited,
		Operation<Void> original
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
			return;
		}

		original.call(

			wave,
			raider,
			pos,
			isRecruited
		);
	}
}
