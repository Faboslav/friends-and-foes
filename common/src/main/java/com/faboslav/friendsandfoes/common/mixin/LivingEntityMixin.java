package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStatusEffects;
import com.faboslav.friendsandfoes.common.modcompat.ModChecker;
import com.faboslav.friendsandfoes.common.modcompat.ModCompat;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntity.class)
public class LivingEntityMixin
{
	@WrapOperation(
		method = "checkTotemDeathProtection",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;")
	)
	private ItemStack friendsandfoes_getStackInCustomSlots(
		LivingEntity instance,
		InteractionHand hand,
		Operation<ItemStack> original
	) {
		var itemStackInHand = original.call(instance, hand);

		if (itemStackInHand.getItem() != Items.TOTEM_OF_UNDYING) {
			for (ModCompat compat : ModChecker.CUSTOM_EQUIPMENT_SLOTS_COMPATS) {
				ItemStack itemStack = compat.getEquippedItemFromCustomSlots(instance, LivingEntityMixin::friendsandfoes_isTotemOfUndying);

				if (itemStack != null) {
					return itemStack;
				}
			}
		}

		return itemStackInHand;
	}

	/*
	@ModifyArgs(
		method = "travel",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/phys/Vec3;multiply(DDD)Lnet/minecraft/world/phys/Vec3;"
		)
	)
	private void structurify$applyGlidingEffect(Args args) {
		var entity = (LivingEntity) (Object) this;

		if (!entity.hasEffect(FriendsAndFoesStatusEffects.GLIDE.holder())) {
			return;
		}

		double x = args.get(0);
		double y = args.get(1);
		double z = args.get(2);

		args.set(0, Math.max(x, 0.995D));
		args.set(1, y);
		args.set(2, Math.max(z, 0.995D));
	}*/

	private static boolean friendsandfoes_isTotemOfUndying(ItemStack itemStack) {
		return itemStack.getItem() == Items.TOTEM_OF_UNDYING;
	}
}
