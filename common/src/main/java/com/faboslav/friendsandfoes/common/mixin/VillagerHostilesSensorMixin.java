package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.sensing.VillagerHostilesSensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(VillagerHostilesSensor.class)
public final class VillagerHostilesSensorMixin
{
	@Unique
	private static final Supplier<ImmutableMap<EntityType<?>, Float>> FRIENDSANDFOES_SQUARED_DISTANCES_FOR_DANGER = Suppliers.memoize(() -> ImmutableMap.<EntityType<?>, Float>builder().put(FriendsAndFoesEntityTypes.ILLUSIONER.get(), 12.0F).put(FriendsAndFoesEntityTypes.ICEOLOGER.get(), 12.0F).build());

	@WrapMethod(
		method = "isClose"
	)
	private boolean friendsandfoes$isCloseEnoughForDanger(
		LivingEntity attacker,
		LivingEntity target,
		Operation<Boolean> original
	) {
		var entityType = target.getType();

		if (FRIENDSANDFOES_SQUARED_DISTANCES_FOR_DANGER.get().containsKey(entityType)) {
			var distance = FRIENDSANDFOES_SQUARED_DISTANCES_FOR_DANGER.get().get(entityType);

			if(distance == null) {
				return false;
			}

			return target.distanceToSqr(attacker) <= (double) (distance * distance);
		}

		return original.call(attacker, target);
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

		return FRIENDSANDFOES_SQUARED_DISTANCES_FOR_DANGER.get().containsKey(entity.getType());
	}
}
