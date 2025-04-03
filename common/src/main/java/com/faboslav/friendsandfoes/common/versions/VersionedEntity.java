package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

//? >=1.21.3 {
import net.minecraft.server.level.ServerLevel;
//?}

public final class VersionedEntity
{
	public static InteractionResult success(Entity entity) {
		InteractionResult interactionResult;

		/*? >=1.21.3 {*/
		interactionResult = InteractionResult.SUCCESS;
		/*?} else {*/
		/*interactionResult = InteractionResult.sidedSuccess(entity.level().isClientSide());
		 *//*?}*/

		return interactionResult;
	}

	public static boolean hurt(Entity entity, DamageSource damageSource, float amount) {
		boolean hurtResult;

		//? >=1.21.3 {
		hurtResult = entity.hurtServer((ServerLevel) entity.level(), damageSource, amount);
		//?} else {
		/*hurtResult = entity.hurt(damageSource, amount);
		*///?}

		return hurtResult;
	}

	public static void moveTo(Entity entity, double x, double y, double z) {
		//? >=1.21.5 {
		/*entity.snapTo(x, y, z);
		*///?} else {
		entity.moveTo(x, y, z);
		//?}
	}

	public static void moveTo(Entity entity, double x, double y, double z, float f, float g) {
		//? >=1.21.5 {
		/*entity.snapTo(x, y, z, f, g);
		*///?} else {
		entity.moveTo(x, y, z, f, g);
		 //?}
	}

	public static void moveTo(Entity entity, BlockPos blockPos, float f, float g) {
		//? >=1.21.5 {
		/*entity.snapTo(blockPos, f, g);
		*///?} else {
		entity.moveTo(blockPos, f, g);
		 //?}
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

		//? >=1.21.3 {
		item = entity.spawnAtLocation((ServerLevel) entity.level(), stack, yOffset);
		//?} else {
		/*item = entity.spawnAtLocation(stack, yOffset);
		*///?}

		return item;
	}
}
