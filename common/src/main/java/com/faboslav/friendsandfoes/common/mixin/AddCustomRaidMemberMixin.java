package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.util.CustomRaidMember;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;

@Mixin(Raid.RaiderType.class)
@SuppressWarnings({"ShadowTarget", "InvokerTarget"})
public final class AddCustomRaidMemberMixin
{
	@Invoker("<init>")
	private static Raid.RaiderType newRaidMember(
		String internalName,
		int internalId,
		EntityType<? extends Raider> entityType,
		int[] countInWave
	) {
		throw new AssertionError();
	}

	@Shadow
	private static @Final
	@Mutable
	Raid.RaiderType[] $VALUES;

	@Inject(
		method = "<clinit>",
		at = @At(
			value = "FIELD",
			opcode = Opcodes.PUTSTATIC,
			target = "Lnet/minecraft/world/entity/raid/Raid$RaiderType;$VALUES:[Lnet/minecraft/world/entity/raid/Raid$RaiderType;",
			shift = At.Shift.AFTER
		)
	)
	private static void friendsandfoes_addCustomRaidMembers(CallbackInfo ci) {
		var raidMembers = new ArrayList<>(Arrays.asList($VALUES));
		var lastRaidMember = raidMembers.get(raidMembers.size() - 1);

		if (FriendsAndFoes.getConfig().enableIceologerInRaids) {
			var iceologerRaidMember = newRaidMember(
				CustomRaidMember.ICEOLOGER_INTERNAL_NAME,
				lastRaidMember.ordinal() + 1,
				FriendsAndFoesEntityTypes.ICEOLOGER.get(),
				CustomRaidMember.ICEOLOGER_COUNT_IN_WAVE
			);
			CustomRaidMember.ICEOLOGER = iceologerRaidMember;
			raidMembers.add(iceologerRaidMember);
		}

		if (FriendsAndFoes.getConfig().enableIllusionerInRaids) {
			var illusionerRaidMember = newRaidMember(
				CustomRaidMember.ILLUSIONER_INTERNAL_NAME,
				lastRaidMember.ordinal() + 2,
				FriendsAndFoesEntityTypes.ILLUSIONER.get(),
				CustomRaidMember.ILLUSIONER_COUNT_IN_WAVE
			);
			CustomRaidMember.ILLUSIONER = illusionerRaidMember;
			raidMembers.add(illusionerRaidMember);
		}

		$VALUES = raidMembers.toArray(new Raid.RaiderType[0]);
	}
}
