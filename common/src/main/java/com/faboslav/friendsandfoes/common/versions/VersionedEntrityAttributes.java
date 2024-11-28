package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.entry.RegistryEntry;

public class VersionedEntrityAttributes
{
	public static final RegistryEntry<EntityAttribute> ATTACK_DAMAGE;
	public static final RegistryEntry<EntityAttribute> ATTACK_KNOCKBACK;
	public static final RegistryEntry<EntityAttribute> FLYING_SPEED;
	public static final RegistryEntry<EntityAttribute> FOLLOW_RANGE;
	public static final RegistryEntry<EntityAttribute> KNOCKBACK_RESISTANCE;
	public static final RegistryEntry<EntityAttribute> MAX_HEALTH;
	public static final RegistryEntry<EntityAttribute> MOVEMENT_SPEED;
	public static final RegistryEntry<EntityAttribute> SCALE;

	static {
		/*? >=1.21.3 {*/
		ATTACK_DAMAGE = EntityAttributes.ATTACK_DAMAGE;
		ATTACK_KNOCKBACK = EntityAttributes.ATTACK_KNOCKBACK;
		FLYING_SPEED = EntityAttributes.FLYING_SPEED;
		FOLLOW_RANGE = EntityAttributes.FOLLOW_RANGE;
		KNOCKBACK_RESISTANCE = EntityAttributes.KNOCKBACK_RESISTANCE;
		MAX_HEALTH = EntityAttributes.MAX_HEALTH;
		MOVEMENT_SPEED = EntityAttributes.MOVEMENT_SPEED;
		SCALE = EntityAttributes.SCALE;
		/*?} else {*/
		/*ATTACK_DAMAGE = EntityAttributes.GENERIC_ATTACK_DAMAGE;
		ATTACK_KNOCKBACK = EntityAttributes.GENERIC_ATTACK_KNOCKBACK;
		FLYING_SPEED = EntityAttributes.GENERIC_FLYING_SPEED;
		FOLLOW_RANGE = EntityAttributes.GENERIC_FOLLOW_RANGE;
		KNOCKBACK_RESISTANCE = EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE;
		MAX_HEALTH = EntityAttributes.GENERIC_MAX_HEALTH;
		MOVEMENT_SPEED = EntityAttributes.GENERIC_MOVEMENT_SPEED;
		SCALE = EntityAttributes.GENERIC_SCALE;
		*//*?}*/
	}
}
