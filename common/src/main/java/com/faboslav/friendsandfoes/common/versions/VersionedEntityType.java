package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;

public class VersionedEntityType
{

	public static final EntityType<Cow> COW = EntityType.COW;
	public static final EntityType<ZombieHorse> ZOMBIE_HORSE = EntityType.ZOMBIE_HORSE;
	public static final EntityType<LightningBolt> LIGHTNING_BOLT = EntityType.LIGHTNING_BOLT;
	public static final EntityType<SmallFireball> SMALL_FIREBALL = EntityType.SMALL_FIREBALL;
	public static final EntityType<Illusioner> ILLUSIONER = EntityType.ILLUSIONER;
	public static final EntityType<Zombie> ZOMBIE = EntityType.ZOMBIE;
	public static final EntityType<Blaze> BLAZE = EntityType.BLAZE;
	public static final EntityType<Player> PLAYER = EntityType.PLAYER;
	public static final EntityType<IronGolem> IRON_GOLEM = EntityType.IRON_GOLEM;

}
