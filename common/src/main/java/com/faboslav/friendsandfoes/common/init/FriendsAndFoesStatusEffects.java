package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.effect.PenguinsGlideStatusEffect;
import com.faboslav.friendsandfoes.common.entity.effect.ReachStatusEffect;
import com.faboslav.friendsandfoes.common.mixin.BeaconBlockEntityAccessor;
import com.faboslav.friendsandfoes.common.versions.VersionedRegistryHolder;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

//? if >= 1.21.1 {
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import java.util.List;
//?} else {
/*import java.util.Arrays;
*///?}

/**
 * @see net.minecraft.world.effect.MobEffects
 */
@SuppressWarnings({"deprecation", "unchecked"})
public final class FriendsAndFoesStatusEffects
{
	public static final ResourcefulRegistry<MobEffect> STATUS_EFFECTS = ResourcefulRegistries.create(BuiltInRegistries.MOB_EFFECT, FriendsAndFoes.MOD_ID);

	//? if >= 1.21.1 {
	public static final HolderRegistryEntry<MobEffect> REACH = STATUS_EFFECTS.registerHolder("reach", () -> new ReachStatusEffect(MobEffectCategory.BENEFICIAL, 0xFE984B));
	public static final HolderRegistryEntry<MobEffect> GLIDE = STATUS_EFFECTS.registerHolder("glide", () -> new PenguinsGlideStatusEffect(MobEffectCategory.BENEFICIAL, 0x745784));
	public static final HolderRegistryEntry<MobEffect> PENGUINS_GLIDE = STATUS_EFFECTS.registerHolder("penguins_glide", () -> new PenguinsGlideStatusEffect(MobEffectCategory.BENEFICIAL, 0x745784));
	//?} else {
	/*public static final RegistryEntry<MobEffect> REACH = STATUS_EFFECTS.register("reach", () -> new ReachStatusEffect(MobEffectCategory.BENEFICIAL, 0xFE984B));
	public static final RegistryEntry<MobEffect> GLIDE = STATUS_EFFECTS.register("glide", () -> new PenguinsGlideStatusEffect(MobEffectCategory.BENEFICIAL, 0x745784));
	public static final RegistryEntry<MobEffect> PENGUINS_GLIDE = STATUS_EFFECTS.register("penguins_glide", () -> new PenguinsGlideStatusEffect(MobEffectCategory.BENEFICIAL, 0x745784));
	*///?}

	public static void registerBeaconEffects() {
		var reach = VersionedRegistryHolder.get(REACH);

		//? if >= 1.21.1 {
		var effects = new ArrayList<>(BeaconBlockEntity.BEACON_EFFECTS);
		var primary = new ArrayList<>(effects.get(0));
		//?} else {
		/*var primary = new ArrayList<>(Arrays.asList(BeaconBlockEntity.BEACON_EFFECTS[0]));
		*///?}

		if (!primary.contains(reach)) {
			primary.add(reach);

			//? if >= 1.21.1 {
			effects.set(0, List.copyOf(primary));
			BeaconBlockEntityAccessor.friendsandfoes$setBeaconEffects(List.copyOf(effects));
			//?} else {
			/*MobEffect[][] effects = Arrays.copyOf(BeaconBlockEntity.BEACON_EFFECTS, BeaconBlockEntity.BEACON_EFFECTS.length);
			effects[0] = primary.toArray(new MobEffect[0]);
			BeaconBlockEntityAccessor.friendsandfoes$setBeaconEffects(effects);
			*///?}

			var validEffects = new HashSet<>(BeaconBlockEntityAccessor.friendsandfoes$getValidEffects());
			validEffects.add(reach);
			BeaconBlockEntityAccessor.friendsandfoes$setValidEffects(Collections.unmodifiableSet(validEffects));
		}
	}
}
