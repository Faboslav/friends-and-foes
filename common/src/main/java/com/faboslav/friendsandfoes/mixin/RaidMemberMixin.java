package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.minecraft.village.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Raid.Member.class)
public final class RaidMemberMixin
{
	@ModifyArg(
		method = "<clinit>",
		slice = @Slice(
			from = @At(
				value = "CONSTANT",
				args = "stringValue=EVOKER"
			)
		),
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/village/raid/Raid$Member;<init>(Ljava/lang/String;ILnet/minecraft/entity/EntityType;[I)V",
			ordinal = 0
		)
	)
	private static int[] friendsandfoes_updateCountInWave(
		int[] countInWave
	) {
		if (
			FriendsAndFoes.getConfig().enableIllusioner
			|| FriendsAndFoes.getConfig().enableIllusionerInRaids
			|| FriendsAndFoes.getConfig().enableIceologer
			|| FriendsAndFoes.getConfig().enableIceologerInRaids
		) {
			return new int[]{0, 0, 0, 0, 0, 1, 1, 1};
		}

		return new int[]{0, 0, 0, 0, 0, 1, 1, 2};
	}
}
