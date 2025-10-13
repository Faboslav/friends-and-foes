package com.faboslav.friendsandfoes.common.entity.ai.goal.zombiehorse;

import com.faboslav.friendsandfoes.common.entity.ZombieHorseEntityAccess;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesCriterias;
import java.util.List;

import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.providers.VanillaEnchantmentProviders;

public final class ZombieHorseTrapTriggerGoal extends Goal
{
	private final ZombieHorse zombieHorse;

	private Player closestPlayer;

	public ZombieHorseTrapTriggerGoal(ZombieHorse zombieHorse) {
		this.zombieHorse = zombieHorse;
	}

	public boolean canUse() {
		Player closestPlayer = this.zombieHorse.level().getNearestPlayer(
			this.zombieHorse.getX(),
			this.zombieHorse.getY(),
			this.zombieHorse.getZ(),
			10.0,
			EntitySelector.NO_CREATIVE_OR_SPECTATOR
		);

		if (closestPlayer == null) {
			return false;
		}

		this.closestPlayer = closestPlayer;

		return true;
	}

	public void tick() {
		ServerLevel serverWorld = (ServerLevel) this.zombieHorse.level();

		DifficultyInstance localDifficulty = serverWorld.getCurrentDifficultyAt(this.zombieHorse.blockPosition());
		((ZombieHorseEntityAccess) this.zombieHorse).friendsandfoes_setTrapped(false);
		this.zombieHorse.setTamed(true);
		this.zombieHorse.setAge(0);
		LightningBolt lightningEntity = EntityType.LIGHTNING_BOLT.create(serverWorld/*? if >=1.21.3 {*/, VersionedEntitySpawnReason.TRIGGERED/*?}*/);
		VersionedEntity.moveTo(lightningEntity, this.zombieHorse.getX(), this.zombieHorse.getY(), this.zombieHorse.getZ());
		lightningEntity.setVisualOnly(true);
		serverWorld.addFreshEntity(lightningEntity);
		Zombie zombie = this.getZombie(localDifficulty, this.zombieHorse);
		zombie.startRiding(this.zombieHorse);
		serverWorld.addFreshEntityWithPassengers(zombie);

		for (int i = 0; i < 3; ++i) {
			ZombieHorse zombieHorse = this.getHorse(localDifficulty);
			Zombie secondZombie = this.getZombie(localDifficulty, zombieHorse);
			secondZombie.startRiding(zombieHorse);
			zombieHorse.push(this.zombieHorse.getRandom().triangle(0.0, 1.1485), 0.0, this.zombieHorse.getRandom().triangle(0.0, 1.1485));
			serverWorld.addFreshEntityWithPassengers(zombieHorse);
		}

		FriendsAndFoesCriterias.ACTIVATE_ZOMBIE_HORSE_TRAP.get().trigger((ServerPlayer) closestPlayer, lightningEntity, List.of());
	}

	private ZombieHorse getHorse(DifficultyInstance localDifficulty) {
		ZombieHorse zombieHorse = EntityType.ZOMBIE_HORSE.create(this.zombieHorse.level()/*? if >=1.21.3 {*/, VersionedEntitySpawnReason.TRIGGERED/*?}*/);

		if(zombieHorse != null) {
			zombieHorse.finalizeSpawn((ServerLevel) this.zombieHorse.level(), localDifficulty, VersionedEntitySpawnReason.TRIGGERED, null);
			zombieHorse.setPos(this.zombieHorse.getX(), this.zombieHorse.getY(), this.zombieHorse.getZ());
			zombieHorse.invulnerableTime = 60;
			zombieHorse.setPersistenceRequired();
			zombieHorse.setTamed(true);
			zombieHorse.setAge(0);
		}

		return zombieHorse;
	}

	private Zombie getZombie(DifficultyInstance localDifficulty, AbstractHorse vehicle) {
		Zombie zombie = EntityType.ZOMBIE.create(vehicle.level()/*? if >=1.21.3 {*/, VersionedEntitySpawnReason.TRIGGERED/*?}*/);

		if(zombie != null) {
			zombie.finalizeSpawn((ServerLevel) vehicle.level(), localDifficulty, VersionedEntitySpawnReason.TRIGGERED, null);
			zombie.setBaby(false);
			zombie.setPos(vehicle.getX(), vehicle.getY(), vehicle.getZ());
			zombie.invulnerableTime = 60;
			zombie.setPersistenceRequired();

			if (zombie.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
				zombie.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			}

			if (zombie.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
				zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
			}

			this.enchantEquipment(zombie, EquipmentSlot.MAINHAND, localDifficulty);
			this.enchantEquipment(zombie, EquipmentSlot.HEAD, localDifficulty);
		}

		return zombie;
	}

	private void enchantEquipment(Zombie rider, EquipmentSlot slot, DifficultyInstance localDifficulty) {
		ItemStack itemStack = rider.getItemBySlot(slot);
		itemStack.set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
		EnchantmentHelper.enchantItemFromProvider(itemStack, rider.level().registryAccess(), VanillaEnchantmentProviders.MOB_SPAWN_EQUIPMENT, localDifficulty, rider.getRandom());
		rider.setItemSlot(slot, itemStack);
	}
}
