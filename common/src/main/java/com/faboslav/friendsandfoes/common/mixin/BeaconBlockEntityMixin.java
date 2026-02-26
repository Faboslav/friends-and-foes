package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStatusEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;

@Mixin(BeaconBlockEntity.class)
public final class BeaconBlockEntityMixin
{
	@Shadow
	@Final
	@Mutable
	private static List<List<Holder<MobEffect>>> BEACON_EFFECTS;

	@Shadow
	@Final
	@Mutable
	private static Set<Holder<MobEffect>> VALID_EFFECTS;

	@Inject(
		method = "<clinit>",
		at = @At(
			value = "TAIL"
		)
	)
	private static void friendsandfoes_addReachToBeaconEffects(CallbackInfo ci) {
		var effects = new ArrayList<>(BEACON_EFFECTS);
		var primary = new ArrayList<>(effects.get(0));
		var reach = FriendsAndFoesStatusEffects.REACH.holder();

		if (!primary.contains(reach)) {
			primary.add(reach);
			effects.set(0, List.copyOf(primary));
			BEACON_EFFECTS = List.copyOf(effects);

			var validEffects = new HashSet<>(VALID_EFFECTS);
			validEffects.add(reach);
			VALID_EFFECTS = Set.copyOf(validEffects);
		}
	}
}
