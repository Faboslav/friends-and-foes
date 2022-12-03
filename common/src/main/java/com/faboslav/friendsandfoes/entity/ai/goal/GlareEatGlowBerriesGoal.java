package com.faboslav.friendsandfoes.entity.ai.goal;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.GlareEntity;
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

public final class GlareEatGlowBerriesGoal extends Goal
{
	public boolean isRunning = false;
	private ItemEntity foodItemToPickUp;
	private int runTicks;

	private static final Predicate<ItemEntity> IS_PICKABLE_FOOD = (itemEntity) -> {
		Item item = itemEntity.getStack().getItem();
		return item == Items.GLOW_BERRIES.asItem() && itemEntity.isAlive() && !itemEntity.cannotPickup();
	};

	private final GlareEntity glare;

	public GlareEatGlowBerriesGoal(GlareEntity glare) {
		this.glare = glare;
		this.setControls(EnumSet.of(Control.MOVE));
	}

	@Override
	public boolean canStart() {
		if (
			FriendsAndFoes.getConfig().enableGlareGriefing == false
			|| this.glare.getTicksUntilCanEatGlowBerries() > 0
			|| this.glare.getRandom().nextInt(10) != 0
			|| this.glare.isLeashed() == true
			|| this.glare.isSitting() == true
			|| this.hasGlareEmptyHand() == false
		) {
			return false;
		}

		this.foodItemToPickUp = this.getFoodItemToPickUp();

		return this.foodItemToPickUp != null;
	}

	@Override
	public void start() {
		this.runTicks = 0;
		this.isRunning = true;

		this.glare.getNavigation().startMovingTo(
			this.foodItemToPickUp,
			glare.getMovementSpeed()
		);
	}

	@Override
	public boolean shouldContinue() {
		return this.runTicks < 300
			   && this.foodItemToPickUp != null
			   && GlareEntity.PICKABLE_FOOD_FILTER.test(foodItemToPickUp)
			   && this.hasGlareEmptyHand();
	}

	@Override
	public void tick() {
		this.runTicks++;

		this.glare.getNavigation().startMovingTo(
			this.foodItemToPickUp,
			glare.getMovementSpeed()
		);

		if (this.glare.getRandom().nextFloat() < 0.05F) {
			this.glare.playAmbientSound();
		}
	}

	@Override
	public void stop() {
		ItemStack itemStack = this.glare.getEquippedStack(EquipmentSlot.MAINHAND);
		Item itemInHand = this.glare.getEquippedStack(EquipmentSlot.MAINHAND).getItem();

		if (
			!this.hasGlareEmptyHand()
			&& itemInHand == Items.GLOW_BERRIES
		) {
			ItemStackParticleEffect particleEffect = new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
			this.glare.heal(itemStack.getItem().getFoodComponent().getHunger());
			this.glare.playEatSound(itemStack);
			this.glare.spawnParticles(particleEffect, 7);
			this.glare.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
		}

		// Reset goal
		this.isRunning = false;
		this.foodItemToPickUp = null;

		// Update entity data
		this.glare.setTicksUntilCanEatGlowBerries(
			this.glare.generateRandomTicksUntilCanEatGlowBerries()
		);
		this.glare.setGrumpy(false);
	}

	@Nullable
	private ItemEntity getFoodItemToPickUp() {
		List<ItemEntity> foodItemsToPickUp = this.glare.world.getEntitiesByClass(
			ItemEntity.class,
			this.glare.getBoundingBox().expand(8.0D, 8.0D, 8.0D),
			IS_PICKABLE_FOOD
		);

		if (foodItemsToPickUp.isEmpty()) {
			return null;
		}

		return foodItemsToPickUp.get(
			this.glare.getRandom().nextInt(foodItemsToPickUp.size())
		);
	}

	private boolean hasGlareEmptyHand() {
		return this.glare.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
	}
}
