package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.BoatEntityAccess;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStatusEffects;
import com.faboslav.friendsandfoes.common.versions.VersionedRegistryHolder;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >= 1.21.4 {
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
//?} else {
/*import net.minecraft.world.entity.vehicle.Boat;
*///?}

//? if >= 1.21.4 {
@Mixin(AbstractBoat.class)
//?} else {
/*@Mixin(Boat.class)
*///?}
public abstract class AbstractBoatMixin implements BoatEntityAccess
{
	@Unique
	@Nullable
	private Vec3 friendsandfoes$tentaclePull;

	@Override
	public void friendsandfoes$setTentaclePull(@Nullable Vec3 tentaclePull) {
		this.friendsandfoes$tentaclePull = tentaclePull;
	}

	@Inject(
		at = @At("TAIL"),
		method = "tick"
	)
	private void friendsandfoes$applyTentaclePull(CallbackInfo ci) {
		if (this.friendsandfoes$tentaclePull == null) {
			return;
		}

		//? if >= 1.21.4 {
		AbstractBoat boat = (AbstractBoat) (Object) this;
		//?} else {
		/*Boat boat = (Boat) (Object) this;
		*///?}

		boat.setDeltaMovement(this.friendsandfoes$tentaclePull);
		boat.syncVelocity = true;
		this.friendsandfoes$tentaclePull = null;
	}

	@WrapOperation(
		method = "controlBoat",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;add(DDD)Lnet/minecraft/world/phys/Vec3;")
	)
	private Vec3 friendsandfoes$applyAbstractBoatSpeedEffect(
		Vec3 instance,
		double x,
		double y,
		double z,
		Operation<Vec3> original
	) {
		double multiplier = 1.0D;

		//? if >= 1.21.4 {
		LivingEntity controllingPassenger = ((AbstractBoat) (Object) this).getControllingPassenger();
		//?} else {
		/*LivingEntity controllingPassenger = ((Boat) (Object) this).getControllingPassenger();
		*///?}

		if (controllingPassenger != null && controllingPassenger.hasEffect(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.PENGUINS_GLIDE))) {
			MobEffectInstance effectInstance = controllingPassenger.getEffect(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.PENGUINS_GLIDE));
			int amplifier = effectInstance == null ? 0 : effectInstance.getAmplifier();
			double bonusPerLevel = FriendsAndFoes.getConfig().penguinsGlideStatusEffectModifier;
			multiplier += bonusPerLevel * (amplifier + 1);
		}

		return original.call(instance, x * multiplier, y, z * multiplier);
	}
}
