package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStatusEffects;
import com.faboslav.friendsandfoes.common.versions.VersionedRegistryHolder;
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
	//? if >= 1.21.1 {
	@Shadow
	@Final
	@Mutable
	public static List<List<Holder<MobEffect>>> BEACON_EFFECTS;

	@Shadow
	@Final
	@Mutable
	private static Set<Holder<MobEffect>> VALID_EFFECTS;
	//?} else {
	/*@Shadow
	@Final
	@Mutable
	public static MobEffect[][] BEACON_EFFECTS;

	@Shadow
	@Final
	@Mutable
	private static Set<MobEffect> VALID_EFFECTS;
	*///?}

	@Inject(
		method = "<clinit>",
		at = @At(
			value = "TAIL"
		)
	)
	private static void friendsandfoes$addReachToBeaconEffects(CallbackInfo ci) {
		var reach = VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.REACH);

		//? if >= 1.21.1 {
		var effects = new ArrayList<>(BEACON_EFFECTS);
		var primary = new ArrayList<>(effects.get(0));
		//?} else {
		/*var primary = new ArrayList<>(java.util.Arrays.asList(BEACON_EFFECTS[0]));
		*///?}

		if (!primary.contains(reach)) {
			primary.add(reach);

			//? if >= 1.21.1 {
			effects.set(0, List.copyOf(primary));
			BEACON_EFFECTS = List.copyOf(effects);
			//?} else {
			/*MobEffect[][] effects = java.util.Arrays.copyOf(BEACON_EFFECTS, BEACON_EFFECTS.length);
			effects[0] = primary.toArray(new MobEffect[0]);
			BEACON_EFFECTS = effects;
			*///?}

			var validEffects = new HashSet<>(VALID_EFFECTS);
			validEffects.add(reach);
			VALID_EFFECTS = Set.copyOf(validEffects);
		}
	}
}
