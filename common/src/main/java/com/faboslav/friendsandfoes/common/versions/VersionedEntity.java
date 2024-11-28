package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

/*? >=1.21.3 {*/
import net.minecraft.server.world.ServerWorld;
/*?}*/

public final class VersionedEntity
{
	public static <T extends MobEntity> T convertTo(EntityType<T> entityType) {

	}

	public static boolean damage(Entity attacker, Entity target, DamageSource source, float amount) {
		/*? >=1.21.3 {*/
		return target.damage(((ServerWorld) attacker.getWorld()), source, amount);
		/*?} else {*/
		/*return target.damage(source, amount);
		*//*?}*/
	}

	@Nullable
	public static ItemEntity dropItem(Entity source, ItemConvertible item, int amount) {
		/*? >=1.21.3 {*/
		return source.dropItem((ServerWorld) source.getWorld(), item, amount);
		/*?} else {*/
		/*return source.dropItem(item, amount);
		*//*?}*/
	}

	public static ItemEntity dropStack(MobEntity entity, ItemStack itemStack) {
		/*? >=1.21.3 {*/
		return entity.dropStack((ServerWorld) entity.getWorld(), itemStack);
		/*?} else {*/
		/*return entity.dropStack(itemStack);
		*//*?}*/
	}
}