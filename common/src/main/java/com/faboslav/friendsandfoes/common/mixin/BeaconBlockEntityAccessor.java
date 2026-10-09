package com.faboslav.friendsandfoes.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Set;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;

//? if >= 1.21.1 {
import java.util.List;
import net.minecraft.core.Holder;
//?}

@Mixin(BeaconBlockEntity.class)
public interface BeaconBlockEntityAccessor
{
	//? if >= 1.21.1 {
	@Mutable
	@Accessor("BEACON_EFFECTS")
	static void friendsandfoes$setBeaconEffects(List<List<Holder<MobEffect>>> beaconEffects) {
		throw new AssertionError();
	}

	@Accessor("VALID_EFFECTS")
	static Set<Holder<MobEffect>> friendsandfoes$getValidEffects() {
		throw new AssertionError();
	}

	@Mutable
	@Accessor("VALID_EFFECTS")
	static void friendsandfoes$setValidEffects(Set<Holder<MobEffect>> validEffects) {
		throw new AssertionError();
	}
	//?} else {
	/*@Mutable
	@Accessor("BEACON_EFFECTS")
	static void friendsandfoes$setBeaconEffects(MobEffect[][] beaconEffects) {
		throw new AssertionError();
	}

	@Accessor("VALID_EFFECTS")
	static Set<MobEffect> friendsandfoes$getValidEffects() {
		throw new AssertionError();
	}

	@Mutable
	@Accessor("VALID_EFFECTS")
	static void friendsandfoes$setValidEffects(Set<MobEffect> validEffects) {
		throw new AssertionError();
	}
	*///?}
}
