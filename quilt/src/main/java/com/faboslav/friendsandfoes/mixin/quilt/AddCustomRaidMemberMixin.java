package com.faboslav.friendsandfoes.mixin.quilt;

import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.util.CustomRaidMember;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.village.raid.Raid;
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

@Mixin(Raid.Member.class)
@SuppressWarnings({"ShadowTarget", "InvokerTarget"})
public final class AddCustomRaidMemberMixin
{
	@Invoker("<init>")
	private static Raid.Member newRaidMember(
		String internalName,
		int internalId,
		EntityType<? extends RaiderEntity> entityType,
		int[] countInWave
	) {
		throw new AssertionError();
	}

	@Shadow
	private static @Final
	@Mutable
	Raid.Member[] field_16632;

	@Inject(
		method = "<clinit>",
		at = @At(
			value = "FIELD",
			opcode = Opcodes.PUTSTATIC,
			target = "Lnet/minecraft/village/raid/Raid$Member;field_16632:[Lnet/minecraft/village/raid/Raid$Member;",
			shift = At.Shift.AFTER
		)
	)
	private static void friendsandfoes_addCustomRaidMembers(CallbackInfo ci) {
		var raidMembers = new ArrayList<>(Arrays.asList(field_16632));
		var lastRaidMember = raidMembers.get(raidMembers.size() - 1);

		var iceologerRaidMember = newRaidMember(
			CustomRaidMember.ICEOLOGER_INTERNAL_NAME,
			lastRaidMember.ordinal() + 1,
			FriendsAndFoesEntityTypes.ICEOLOGER.get(),
			CustomRaidMember.ICEOLOGER_COUNT_IN_WAVE
		);
		CustomRaidMember.ICEOLOGER = iceologerRaidMember;
		raidMembers.add(iceologerRaidMember);

		var illusionerRaidMember = newRaidMember(
			CustomRaidMember.ILLUSIONER_INTERNAL_NAME,
			lastRaidMember.ordinal() + 2,
			EntityType.ILLUSIONER,
			CustomRaidMember.ILLUSIONER_COUNT_IN_WAVE
		);
		CustomRaidMember.ILLUSIONER = illusionerRaidMember;
		raidMembers.add(illusionerRaidMember);

		field_16632 = raidMembers.toArray(new Raid.Member[0]);
	}
}
