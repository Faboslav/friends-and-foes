package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.sensing.VillagerHostilesSensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VillagerHostilesSensor.class)
public final class VillagerHostilesSensorMixin
{
	@WrapOperation(
		method = "<clinit>",
		at = @At(
			value = "INVOKE",
			target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"
		)
	)
	private static ImmutableMap<EntityType<?>, Float> addDanger(
		ImmutableMap.Builder<EntityType<?>, Float> instance,
		Operation<ImmutableMap<EntityType<?>, Float>> original
	) {
		return original.call(instance.put(FriendsAndFoesEntityTypes.ILLUSIONER.get(), 12.0F).put(FriendsAndFoesEntityTypes.ICEOLOGER.get(), 12.0F));
	}
}
