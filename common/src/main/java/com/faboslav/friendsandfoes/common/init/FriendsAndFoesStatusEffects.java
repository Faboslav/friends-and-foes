package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.effect.ReachStatusEffect;
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * @see net.minecraft.world.effect.MobEffects
 */
public final class FriendsAndFoesStatusEffects
{
	public static final ResourcefulRegistry<MobEffect> STATUS_EFFECTS = ResourcefulRegistries.create(BuiltInRegistries.MOB_EFFECT, FriendsAndFoes.MOD_ID);

	public static final HolderRegistryEntry<MobEffect> REACH = STATUS_EFFECTS.registerHolder("reach", () -> new ReachStatusEffect(MobEffectCategory.BENEFICIAL, 0xFE984B));
	public static final HolderRegistryEntry<MobEffect> BOAT_SPEED = STATUS_EFFECTS.registerHolder("boat_speed", () -> new ReachStatusEffect(MobEffectCategory.BENEFICIAL, 0xFE984B));
}
