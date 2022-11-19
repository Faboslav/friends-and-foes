package com.faboslav.friendsandfoes.item;

import com.faboslav.friendsandfoes.init.FriendsAndFoesItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

/**
 * @see net.minecraft.item.ArmorMaterials
 */
public enum FriendsAndFoesArmorMaterials implements ArmorMaterial
{
	WILDFIRE("wildfire", 35, new int[]{2, 5, 7, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 2.0F, 0.1F, () -> {
		return Ingredient.ofItems(FriendsAndFoesItems.WILDFIRE_CROWN_FRAGMENT.get());
	});

	private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
	private final String name;
	private final int durabilityMultiplier;
	private final int[] protectionAmounts;
	private final int enchantability;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final Lazy<Ingredient> repairIngredientSupplier;

	FriendsAndFoesArmorMaterials(
		String name,
		int durabilityMultiplier,
		int[] protectionAmounts,
		int enchantability,
		SoundEvent equipSound,
		float toughness,
		float knockbackResistance,
		Supplier repairIngredientSupplier
	) {
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionAmounts = protectionAmounts;
		this.enchantability = enchantability;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredientSupplier = new Lazy(repairIngredientSupplier);
	}

	public int getDurability(EquipmentSlot slot) {
		return BASE_DURABILITY[slot.getEntitySlotId()] * this.durabilityMultiplier;
	}

	public int getProtectionAmount(EquipmentSlot slot) {
		return this.protectionAmounts[slot.getEntitySlotId()];
	}

	public int getEnchantability() {
		return this.enchantability;
	}

	public SoundEvent getEquipSound() {
		return this.equipSound;
	}

	public Ingredient getRepairIngredient() {
		return this.repairIngredientSupplier.get();
	}

	public String getName() {
		return this.name;
	}

	public float getToughness() {
		return this.toughness;
	}

	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}
