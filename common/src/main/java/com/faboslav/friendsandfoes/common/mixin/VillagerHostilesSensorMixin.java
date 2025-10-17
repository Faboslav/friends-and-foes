package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.sensing.VillagerHostilesSensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(VillagerHostilesSensor.class)
public final class VillagerHostilesSensorMixin
{
	@Unique
	private static final ImmutableMap<EntityType<?>, Float> CUSTOM_SQUARED_DISTANCES_FOR_DANGER = ImmutableMap.<EntityType<?>, Float>builder().put(FriendsAndFoesEntityTypes.ILLUSIONER.get(), 12.0F).put(FriendsAndFoesEntityTypes.ICEOLOGER.get(), 12.0F).build();

	@WrapMethod(
		method = "isClose"
	)
	private boolean friendsandfoes_isCloseEnoughForDanger(
		LivingEntity attacker,
		LivingEntity target,
		Operation<Boolean> original
	) {
		if(original.call(attacker, target)) {
			return true;
		}

		var entityType = target.getType();
		if (CUSTOM_SQUARED_DISTANCES_FOR_DANGER.containsKey(entityType)) {
			var distance = CUSTOM_SQUARED_DISTANCES_FOR_DANGER.get(entityType);

			if(distance == null) {
				return false;
			}

			return target.distanceToSqr(attacker) <= (double) (distance * distance);
		}

		return false;
	}

	@WrapMethod(
		method = "isHostile"
	)
	private boolean friendsandfoes$isHostile(
		LivingEntity entity, Operation<Boolean> original
	) {
		if(original.call(entity)) {
			return true;
		}

		return CUSTOM_SQUARED_DISTANCES_FOR_DANGER.containsKey(entity.getType());
	}
}
