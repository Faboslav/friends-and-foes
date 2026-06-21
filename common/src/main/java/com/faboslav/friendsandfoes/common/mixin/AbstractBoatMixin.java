package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStatusEffects;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >= 1.21.8 {
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
//?} else {
/*import net.minecraft.world.entity.vehicle.Boat;
*///?}

//? if >= 1.21.8 {
@Mixin(AbstractBoat.class)
//?} else {
/*@Mixin(Boat.class)
*///?}
public abstract class AbstractBoatMixin
{
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

		//? if >= 1.21.8 {
		LivingEntity controllingPassenger = ((AbstractBoat) (Object) this).getControllingPassenger();
		//?} else {
		/*LivingEntity controllingPassenger = ((Boat) (Object) this).getControllingPassenger();
		*///?}

		if (controllingPassenger != null && controllingPassenger.hasEffect(FriendsAndFoesStatusEffects.BOAT_SPEED.holder())) {
			MobEffectInstance effectInstance = controllingPassenger.getEffect(FriendsAndFoesStatusEffects.BOAT_SPEED.holder());
			int amplifier = effectInstance == null ? 0 : effectInstance.getAmplifier();
			double bonusPerLevel = FriendsAndFoes.getConfig().penguinBoatSpeedStatusEffectModifier;
			multiplier += bonusPerLevel * (amplifier + 1);
		}

		return original.call(instance, x * multiplier, y, z * multiplier);
	}
}
