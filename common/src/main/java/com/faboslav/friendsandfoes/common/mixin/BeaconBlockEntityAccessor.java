package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

//? if >= 1.21.1 {
import net.minecraft.core.Holder;

import java.util.List;
//?}

@Mixin(BeaconBlockEntity.class)
public interface BeaconBlockEntityAccessor
{
	@Mutable
	@Accessor("BEACON_EFFECTS")
	//? if >= 1.21.1 {
	static void friendsandfoes$setBeaconEffects(List<List<Holder<MobEffect>>> beaconEffects)
	//?} else {
	/*static void friendsandfoes$setBeaconEffects(MobEffect[][] beaconEffects)
	*///?}
	{
		throw new AssertionError();
	}

	@Accessor("VALID_EFFECTS")
	//? if >= 1.21.1 {
	static Set<Holder<MobEffect>> friendsandfoes$getValidEffects()
	//?} else {
	/*static Set<MobEffect> friendsandfoes$getValidEffects()
	*///?}
	{
		throw new AssertionError();
	}

	@Mutable
	@Accessor("VALID_EFFECTS")
	//? if >= 1.21.1 {
	static void friendsandfoes$setValidEffects(Set<Holder<MobEffect>> validEffects)
	//?} else {
	/*static void friendsandfoes$setValidEffects(Set<MobEffect> validEffects)
	*///?}
	{
		throw new AssertionError();
	}
}
