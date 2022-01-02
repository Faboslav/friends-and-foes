package com.faboslav.friendsandfoes.entity.passive.ai.goal;

import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class GlareEatGlowBerriesGoal extends Goal
{
	private ItemEntity foodItemToPickUp;
	private int runTicks;

	private static final Predicate<ItemEntity> IS_PICKABLE_FOOD = (itemEntity) -> {
		Item item = itemEntity.getStack().getItem();
		return item == Items.GLOW_BERRIES.asItem() && itemEntity.isAlive() && !itemEntity.cannotPickup();
	};

	private GlareEntity glare;

	public GlareEatGlowBerriesGoal(GlareEntity glare) {
		this.glare = glare;
		this.setControls(EnumSet.of(Control.MOVE));
	}

	@Override
	public boolean canStart() {
		this.foodItemToPickUp = this.getFoodItemToPickUp();

		if (
			glare.isLeashed()
			|| this.foodItemToPickUp == null
		) {
			return false;
		}

		System.out.println("lets pickup");
		return true;
	}

	@Override
	public boolean shouldContinue() {
		if (
			this.foodItemToPickUp == null
			|| !this.foodItemToPickUp.isAlive()
		) {
			return false;
		}

		return true;
	}

	@Override
	public void tick() {
		if (!this.glare.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
			this.glare.getNavigation().stop();
		}
	}

	public void start() {
		System.out.println("start");

		this.glare.getNavigation().startMovingTo(
			this.foodItemToPickUp,
			glare.getMovementSpeed()
		);
	}

	public void stop() {
		System.out.println("stop");
		ItemStack itemStack = foodItemToPickUp.getStack();
		ItemStackParticleEffect particleEffect = new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);

		foodItemToPickUp.discard();
		this.glare.playEatSound(itemStack);
		this.glare.spawnParticles(particleEffect, 7);
	}

	@Nullable
	private ItemEntity getFoodItemToPickUp() {
		List<ItemEntity> foodItemsToPickUp = this.glare.world.getEntitiesByClass(
			ItemEntity.class,
			this.glare.getBoundingBox().expand(12.0D, 12.0D, 12.0D),
			IS_PICKABLE_FOOD
		);

		if (foodItemsToPickUp.isEmpty()) {
			return null;
		}

		return foodItemsToPickUp.get(
			this.glare.getRandom().nextInt(foodItemsToPickUp.size())
		);
	}
}
