package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.entity.LivingEntity;

public final class VersionedEntity
{
	public static EquipmentSlot getEquipmentSlotForItem(InteractionHand hand) {
		EquipmentSlot equipmentSlot;

		equipmentSlot = LivingEntity.getSlotForHand(hand);

		return equipmentSlot;
	}

	public static InteractionResult success(Entity entity) {
		InteractionResult interactionResult;

		interactionResult = InteractionResult.sidedSuccess(entity.level().isClientSide());

		return interactionResult;
	}

	public static boolean hurt(Entity entity, DamageSource damageSource, float amount) {
		boolean hurtResult = false;

		hurtResult = entity.hurt(damageSource, amount);

		return hurtResult;
	}

	public static void moveTo(Entity entity, double x, double y, double z) {

		entity.moveTo(x, y, z);

	}

	public static void moveTo(Entity entity, double x, double y, double z, float f, float g) {

		entity.moveTo(x, y, z, f, g);

	}

	public static void moveTo(Entity entity, BlockPos blockPos, float f, float g) {

		entity.moveTo(blockPos, f, g);

	}

	public static ItemEntity spawnAtLocation(Entity entity, ItemLike stack) {
		return VersionedEntity.spawnAtLocation(entity, stack, 0.0F);
	}

	public static ItemEntity spawnAtLocation(Entity entity, ItemLike itemLike, float yOffset) {
		return VersionedEntity.spawnAtLocation(entity, new ItemStack(itemLike), yOffset);
	}

	public static ItemEntity spawnAtLocation(Entity entity, ItemStack stack) {
		return VersionedEntity.spawnAtLocation(entity, stack, 0.0F);
	}

	public static ItemEntity spawnAtLocation(Entity entity, ItemStack stack, float yOffset) {
		ItemEntity item;

		item = entity.spawnAtLocation(stack, yOffset);

		return item;
	}

	public static boolean isEntityType(@Nullable Entity entity, TagKey<EntityType<?>> entityType) {

		 return entity != null && entity.getType().is(entityType);

	}
}
