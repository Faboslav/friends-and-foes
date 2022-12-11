package com.faboslav.friendsandfoes.entity.ai.goal;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.LocalDifficulty;

public class ZombieHorseTrapTriggerGoal
{
	private final ZombieHorseEntity zombieHorse;

	public ZombieHorseTrapTriggerGoal(ZombieHorseEntity zombieHorse) {
		this.zombieHorse = zombieHorse;
	}

	public boolean canStart() {
		return this.zombieHorse.world.isPlayerInRange(this.zombieHorse.getX(), this.zombieHorse.getY(), this.zombieHorse.getZ(), 10.0);
	}

	public void tick() {
		ServerWorld serverWorld = (ServerWorld)this.zombieHorse.world;
		LocalDifficulty localDifficulty = serverWorld.getLocalDifficulty(this.zombieHorse.getBlockPos());
		//this.zombieHorse.setTrapped(false);
		//this.zombieHorse.setTame(true);
		//this.zombieHorse.setBreedingAge(0);
		LightningEntity lightningEntity = (LightningEntity) EntityType.LIGHTNING_BOLT.create(serverWorld);
		lightningEntity.refreshPositionAfterTeleport(this.zombieHorse.getX(), this.zombieHorse.getY(), this.zombieHorse.getZ());
		lightningEntity.setCosmetic(true);
		serverWorld.spawnEntity(lightningEntity);
		SkeletonEntity skeletonEntity = this.getSkeleton(localDifficulty, this.zombieHorse);
		skeletonEntity.startRiding(this.zombieHorse);
		serverWorld.spawnEntityAndPassengers(skeletonEntity);

		for(int i = 0; i < 3; ++i) {
			AbstractHorseEntity abstractHorseEntity = this.getHorse(localDifficulty);
			SkeletonEntity skeletonEntity2 = this.getSkeleton(localDifficulty, abstractHorseEntity);
			skeletonEntity2.startRiding(abstractHorseEntity);
			abstractHorseEntity.addVelocity(this.zombieHorse.getRandom().nextTriangular(0.0, 1.1485), 0.0, this.zombieHorse.getRandom().nextTriangular(0.0, 1.1485));
			serverWorld.spawnEntityAndPassengers(abstractHorseEntity);
		}

	}

	private AbstractHorseEntity getHorse(LocalDifficulty localDifficulty) {
		SkeletonHorseEntity skeletonHorseEntity = (SkeletonHorseEntity)EntityType.SKELETON_HORSE.create(this.zombieHorse.world);
		skeletonHorseEntity.initialize((ServerWorld)this.zombieHorse.world, localDifficulty, SpawnReason.TRIGGERED, (EntityData)null, (NbtCompound)null);
		skeletonHorseEntity.setPosition(this.zombieHorse.getX(), this.zombieHorse.getY(), this.zombieHorse.getZ());
		skeletonHorseEntity.timeUntilRegen = 60;
		skeletonHorseEntity.setPersistent();
		skeletonHorseEntity.setTame(true);
		skeletonHorseEntity.setBreedingAge(0);
		return skeletonHorseEntity;
	}

	private SkeletonEntity getSkeleton(LocalDifficulty localDifficulty, AbstractHorseEntity vehicle) {
		SkeletonEntity skeletonEntity = (SkeletonEntity)EntityType.SKELETON.create(vehicle.world);
		skeletonEntity.initialize((ServerWorld)vehicle.world, localDifficulty, SpawnReason.TRIGGERED, (EntityData)null, (NbtCompound)null);
		skeletonEntity.setPosition(vehicle.getX(), vehicle.getY(), vehicle.getZ());
		skeletonEntity.timeUntilRegen = 60;
		skeletonEntity.setPersistent();
		if (skeletonEntity.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
			skeletonEntity.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
		}

		skeletonEntity.equipStack(EquipmentSlot.MAINHAND, EnchantmentHelper.enchant(skeletonEntity.getRandom(), this.removeEnchantments(skeletonEntity.getMainHandStack()), (int)(5.0F + localDifficulty.getClampedLocalDifficulty() * (float)skeletonEntity.getRandom().nextInt(18)), false));
		skeletonEntity.equipStack(EquipmentSlot.HEAD, EnchantmentHelper.enchant(skeletonEntity.getRandom(), this.removeEnchantments(skeletonEntity.getEquippedStack(EquipmentSlot.HEAD)), (int)(5.0F + localDifficulty.getClampedLocalDifficulty() * (float)skeletonEntity.getRandom().nextInt(18)), false));
		return skeletonEntity;
	}

	private ItemStack removeEnchantments(ItemStack stack) {
		stack.removeSubNbt("Enchantments");
		return stack;
	}
}
