package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.modcompat.ModChecker;
import com.faboslav.friendsandfoes.common.modcompat.ModCompat;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin
{
	@WrapOperation(
		method = "tryUseTotem",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;")
	)
	private ItemStack friendsandfoes_getStackInCustomSlots(
		LivingEntity instance,
		Hand hand,
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

	private static boolean friendsandfoes_isTotemOfUndying(ItemStack itemStack) {
		return itemStack.getItem() == Items.TOTEM_OF_UNDYING;
	}
}
