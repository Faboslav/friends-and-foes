package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStatusEffects;
import com.faboslav.friendsandfoes.common.modcompat.ModChecker;
import com.faboslav.friendsandfoes.common.modcompat.ModCompat;
import com.faboslav.friendsandfoes.common.versions.VersionedRegistryHolder;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
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

	//? if >= 1.21.1 {
	@ModifyExpressionValue(
		/*? if >= 1.21.4 {*/
		method = "travelInAir",
		/*?} else {*/
		/*method = "travel",
		*//*?}*/
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;getFriction()F"
		),
		require = 0
	)
	private float friendsandfoes$applyGlideFrictionVanilla(float blockFriction) {
		return friendsandfoes$computeGlideFriction(blockFriction);
	}

	@ModifyExpressionValue(
		/*? if >= 1.21.4 {*/
		method = "travelInAir",
		/*?} else {*/
		/*method = "travel",
		*//*?}*/
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F"
		),
		require = 0
	)
	private float friendsandfoes$applyGlideFrictionForgePatched(float blockFriction) {
		return friendsandfoes$computeGlideFriction(blockFriction);
	}
	//?} else {
	/*@ModifyExpressionValue(
		method = "travel",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;getFriction()F"
		),
		require = 0
	)
	private float friendsandfoes$applyGlideFrictionVanilla(float blockFriction) {
		return friendsandfoes$computeGlideFriction(blockFriction);
	}

	@ModifyExpressionValue(
		method = "travel",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F"
		),
		require = 0
	)
	private float friendsandfoes$applyGlideFrictionForgePatched(float blockFriction) {
		return friendsandfoes$computeGlideFriction(blockFriction);
	}
	*///?}

	@Unique
	private float friendsandfoes$computeGlideFriction(float blockFriction) {
		var entity = (LivingEntity) (Object) this;

		if (entity.onGround() && entity.hasEffect(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.GLIDE))) {
			int amplifier = entity.getEffect(VersionedRegistryHolder.get(FriendsAndFoesStatusEffects.GLIDE)).getAmplifier();
			return Math.min(Blocks.ICE.getFriction() + GLIDE_FRICTION_PER_LEVEL * amplifier, MAX_GLIDE_FRICTION);
		}

		return blockFriction;
	}

	@Unique
	private static boolean friendsandfoes$isTotemOfUndying(ItemStack itemStack) {
		return itemStack.getItem() == Items.TOTEM_OF_UNDYING;
	}
}
