package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.effect.PenguinsGlideStatusEffect;
import com.faboslav.friendsandfoes.common.entity.effect.ReachStatusEffect;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

//? if >= 1.21.1 {
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
//?}

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
}
