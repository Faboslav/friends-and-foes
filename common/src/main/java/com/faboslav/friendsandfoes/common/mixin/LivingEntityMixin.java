package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStatusEffects;
import com.faboslav.friendsandfoes.common.modcompat.ModChecker;
import com.faboslav.friendsandfoes.common.modcompat.ModCompat;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin
{
	@Unique
	private static final float GLIDE_FRICTION_PER_LEVEL = 0.01F;
	@Unique
	private static final float MAX_GLIDE_FRICTION = 0.99F;

	@WrapOperation(
		method = "checkTotemDeathProtection",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"
		)
	)
	private ItemStack friendsandfoes$getStackInCustomSlots(
		LivingEntity instance,
		InteractionHand hand,
		Operation<ItemStack> original
	) {
		var itemStackInHand = original.call(instance, hand);

		if (itemStackInHand.getItem() != Items.TOTEM_OF_UNDYING) {
			for (ModCompat compat : ModChecker.CUSTOM_EQUIPMENT_SLOTS_COMPATS) {
				ItemStack itemStack = compat.getEquippedItemFromCustomSlots(instance, LivingEntityMixin::friendsandfoes$isTotemOfUndying);

				if (itemStack != null) {
					return itemStack;
				}
			}
		}

		return itemStackInHand;
	}

	@ModifyVariable(
		method = "travelInAir",
		at = @At("STORE"),
		name = "blockFriction"
	)
	private float friendsandfoes$applyGlideFriction(float blockFriction) {
		var entity = (LivingEntity) (Object) this;

		if (entity.onGround() && entity.hasEffect(FriendsAndFoesStatusEffects.GLIDE.holder())) {
			int amplifier = entity.getEffect(FriendsAndFoesStatusEffects.GLIDE.holder()).getAmplifier();
			return Math.min(Blocks.ICE.getFriction() + GLIDE_FRICTION_PER_LEVEL * amplifier, MAX_GLIDE_FRICTION);
		}

		return blockFriction;
	}

	@Unique
	private static boolean friendsandfoes$isTotemOfUndying(ItemStack itemStack) {
		return itemStack.getItem() == Items.TOTEM_OF_UNDYING;
	}
}
