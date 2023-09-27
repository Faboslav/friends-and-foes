package com.faboslav.friendsandfoes.item;

import com.faboslav.friendsandfoes.init.FriendsAndFoesItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.function.Supplier;

/**
 * @see net.minecraft.item.ArmorMaterials
 */
public enum FriendsAndFoesArmorMaterials implements ArmorMaterial
{
	WILDFIRE(
		"wildfire",
		35,
		Util.make(new EnumMap(ArmorItem.Type.class), (map) -> {
			map.put(ArmorItem.Type.BOOTS, 2);
			map.put(ArmorItem.Type.LEGGINGS, 5);
			map.put(ArmorItem.Type.CHESTPLATE, 7);
			map.put(ArmorItem.Type.HELMET, 2);
		}),
		12,
		SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
		2.0F,
		0.1F, () -> {
		return Ingredient.ofItems(FriendsAndFoesItems.WILDFIRE_CROWN_FRAGMENT.get());
	});

	private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY = (EnumMap) Util.make(new EnumMap(ArmorItem.Type.class), (map) -> {
		map.put(ArmorItem.Type.BOOTS, 13);
		map.put(ArmorItem.Type.LEGGINGS, 15);
		map.put(ArmorItem.Type.CHESTPLATE, 16);
		map.put(ArmorItem.Type.HELMET, 11);
	});

	private final String name;
	private final int durabilityMultiplier;
	private final EnumMap<ArmorItem.Type, Integer> protectionAmounts;
	private final int enchantability;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final Lazy<Ingredient> repairIngredientSupplier;

	FriendsAndFoesArmorMaterials(
		String name,
		int durabilityMultiplier,
		EnumMap protectionAmounts,
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

	public int getDurability(ArmorItem.Type type) {
		return BASE_DURABILITY.get(type) * this.durabilityMultiplier;
	}

	public int getProtection(ArmorItem.Type type) {
		return this.protectionAmounts.get(type);
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
