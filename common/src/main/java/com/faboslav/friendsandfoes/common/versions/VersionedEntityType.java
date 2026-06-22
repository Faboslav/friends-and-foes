package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.equine.ZombieHorse;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.illager.Illusioner;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;

//? if >= 1.21.9 {
import net.minecraft.world.entity.decoration.Mannequin;
//?}

//? if >=26.2 {
import net.minecraft.world.entity.EntityTypes;
//?}

public class VersionedEntityType
{
	//? if >= 26.2 {
	public static final EntityType<Cow> COW = EntityTypes.COW;
	public static final EntityType<ZombieHorse> ZOMBIE_HORSE = EntityTypes.ZOMBIE_HORSE;
	public static final EntityType<LightningBolt> LIGHTNING_BOLT = EntityTypes.LIGHTNING_BOLT;
	public static final EntityType<SmallFireball> SMALL_FIREBALL = EntityTypes.SMALL_FIREBALL;
	public static final EntityType<Illusioner> ILLUSIONER = EntityTypes.ILLUSIONER;
	public static final EntityType<Zombie> ZOMBIE = EntityTypes.ZOMBIE;
	public static final EntityType<Blaze> BLAZE = EntityTypes.BLAZE;
	public static final EntityType<Player> PLAYER = EntityTypes.PLAYER;
	public static final EntityType<IronGolem> IRON_GOLEM = EntityTypes.IRON_GOLEM;
	public static final EntityType<Mannequin> MANNEQUIN = EntityTypes.MANNEQUIN;
	//?} else {
	/*public static final EntityType<Cow> COW = EntityType.COW;
	public static final EntityType<ZombieHorse> ZOMBIE_HORSE = EntityType.ZOMBIE_HORSE;
	public static final EntityType<LightningBolt> LIGHTNING_BOLT = EntityType.LIGHTNING_BOLT;
	public static final EntityType<SmallFireball> SMALL_FIREBALL = EntityType.SMALL_FIREBALL;
	public static final EntityType<Illusioner> ILLUSIONER = EntityType.ILLUSIONER;
	public static final EntityType<Zombie> ZOMBIE = EntityType.ZOMBIE;
	public static final EntityType<Blaze> BLAZE = EntityType.BLAZE;
	public static final EntityType<Player> PLAYER = EntityType.PLAYER;
	public static final EntityType<IronGolem> IRON_GOLEM = EntityType.IRON_GOLEM;
	//? if >= 1.21.9 {
	public static final EntityType<Mannequin> MANNEQUIN = EntityType.MANNEQUIN;
	//?}
	*///?}
}
