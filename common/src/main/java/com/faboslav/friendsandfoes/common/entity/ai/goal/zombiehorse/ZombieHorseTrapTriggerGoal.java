package com.faboslav.friendsandfoes.common.entity.ai.goal.zombiehorse;

import com.faboslav.friendsandfoes.common.entity.ZombieHorseEntityAccess;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesCriterias;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.provider.EnchantmentProviders;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.LocalDifficulty;

import java.util.List;

public final class ZombieHorseTrapTriggerGoal extends Goal
{
	private final ZombieHorseEntity zombieHorse;

	private PlayerEntity closestPlayer;

	public ZombieHorseTrapTriggerGoal(ZombieHorseEntity zombieHorse) {
		this.zombieHorse = zombieHorse;
	}

	public boolean canStart() {
		PlayerEntity closestPlayer = this.zombieHorse.getWorld().getClosestPlayer(
			this.zombieHorse.getX(),
			this.zombieHorse.getY(),
			this.zombieHorse.getZ(),
			10.0,
			EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR
		);

		if (closestPlayer == null) {
			return false;
		}

		this.closestPlayer = closestPlayer;

		return true;
	}

	public void tick() {
		ServerWorld serverWorld = (ServerWorld) this.zombieHorse.getWorld();

		LocalDifficulty localDifficulty = serverWorld.getLocalDifficulty(this.zombieHorse.getBlockPos());
		((ZombieHorseEntityAccess) this.zombieHorse).friendsandfoes_setTrapped(false);
		this.zombieHorse.setTame(true);
		this.zombieHorse.setBreedingAge(0);
		LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(serverWorld);
		lightningEntity.refreshPositionAfterTeleport(this.zombieHorse.getX(), this.zombieHorse.getY(), this.zombieHorse.getZ());
		lightningEntity.setCosmetic(true);
		serverWorld.spawnEntity(lightningEntity);
		ZombieEntity zombie = this.getZombie(localDifficulty, this.zombieHorse);
		zombie.startRiding(this.zombieHorse);
		serverWorld.spawnEntityAndPassengers(zombie);

		for (int i = 0; i < 3; ++i) {
			ZombieHorseEntity zombieHorse = this.getHorse(localDifficulty);
			ZombieEntity secondZombie = this.getZombie(localDifficulty, zombieHorse);
			secondZombie.startRiding(zombieHorse);
			zombieHorse.addVelocity(this.zombieHorse.getRandom().nextTriangular(0.0, 1.1485), 0.0, this.zombieHorse.getRandom().nextTriangular(0.0, 1.1485));
			serverWorld.spawnEntityAndPassengers(zombieHorse);
		}

		FriendsAndFoesCriterias.ACTIVATE_ZOMBIE_HORSE_TRAP.get().trigger((ServerPlayerEntity) closestPlayer, lightningEntity, List.of());
	}

	private ZombieHorseEntity getHorse(LocalDifficulty localDifficulty) {
		ZombieHorseEntity zombieHorse = EntityType.ZOMBIE_HORSE.create(this.zombieHorse.getWorld());
		zombieHorse.initialize((ServerWorld) this.zombieHorse.getWorld(), localDifficulty, SpawnReason.TRIGGERED, null);
		zombieHorse.setPosition(this.zombieHorse.getX(), this.zombieHorse.getY(), this.zombieHorse.getZ());
		zombieHorse.timeUntilRegen = 60;
		zombieHorse.setPersistent();
		zombieHorse.setTame(true);
		zombieHorse.setBreedingAge(0);
		return zombieHorse;
	}

	private ZombieEntity getZombie(LocalDifficulty localDifficulty, AbstractHorseEntity vehicle) {
		ZombieEntity zombie = EntityType.ZOMBIE.create(vehicle.getWorld());
		zombie.initialize((ServerWorld) vehicle.getWorld(), localDifficulty, SpawnReason.TRIGGERED, null);
		zombie.setBaby(false);
		zombie.setPosition(vehicle.getX(), vehicle.getY(), vehicle.getZ());
		zombie.timeUntilRegen = 60;
		zombie.setPersistent();

		if (zombie.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
			zombie.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
		}

		if (zombie.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
			zombie.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
		}

		this.enchantEquipment(zombie, EquipmentSlot.MAINHAND, localDifficulty);
		this.enchantEquipment(zombie, EquipmentSlot.HEAD, localDifficulty);

		return zombie;
	}

	private void enchantEquipment(ZombieEntity rider, EquipmentSlot slot, LocalDifficulty localDifficulty) {
		ItemStack itemStack = rider.getEquippedStack(slot);
		itemStack.set(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
		EnchantmentHelper.applyEnchantmentProvider(itemStack, rider.getWorld().getRegistryManager(), EnchantmentProviders.MOB_SPAWN_EQUIPMENT, localDifficulty, rider.getRandom());
		rider.equipStack(slot, itemStack);
	}
}
