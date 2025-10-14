package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Raid.RaiderType.class)
public final class RaidMemberMixin
{
	@WrapOperation(
		method = "<clinit>",
		slice = @Slice(
			from = @At(
				value = "CONSTANT",
				args = "stringValue=EVOKER"
			)
		),
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/raid/Raid$RaiderType;<init>(Ljava/lang/String;ILnet/minecraft/world/entity/EntityType;[I)V"
		)
	)
	private static void friendsandfoes$wrapEvokerCtor(
		String name,
		int id,
		EntityType<?> type,
		int[] countInWave,
		Operation<Void> original
	) {
		if (
			(
				FriendsAndFoes.getConfig().enableIllusioner
				&& FriendsAndFoes.getConfig().enableIllusionerInRaids
			)
			|| (
				FriendsAndFoes.getConfig().enableIceologer
				&& FriendsAndFoes.getConfig().enableIceologerInRaids
			)
		) {
			original.call(name, id, type, new int[]{0, 0, 0, 0, 0, 1, 1, 1});
		} else {
			original.call(name, id, type, countInWave);
		}
	}
}
