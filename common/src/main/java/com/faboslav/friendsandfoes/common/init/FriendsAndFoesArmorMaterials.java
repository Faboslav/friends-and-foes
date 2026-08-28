package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import net.minecraft.util.Util;

import java.util.EnumMap;

//? if >= 1.21.3 {
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
//?} else {
/*import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
*///?}

//? if >= 1.21.1 && < 1.21.3 {
/*import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.versions.VersionedRegistryHolder;
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
*///?} else if < 1.21.1 {
/*import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;
*///?}

public final class FriendsAndFoesArmorMaterials
{
	private static final int DURABILITY = 37;
	private static final int ENCHANTMENT_VALUE = 9;
	private static final float TOUGHNESS = 1.0F;
	private static final float KNOCKBACK_RESISTANCE = 0.0F;

	private static final int BOOTS_DEFENSE = 3;
	private static final int LEGGINGS_DEFENSE = 6;
	private static final int CHESTPLATE_DEFENSE = 8;
	private static final int HELMET_DEFENSE = 3;
	private static final int BODY_DEFENSE = 11;

	//? if < 1.21.1 {
	/*private static final EnumMap<ArmorItem.Type, Integer> DURABILITY_MULTIPLIER_PER_TYPE = Util.make(
		new EnumMap<>(ArmorItem.Type.class),
		enumMap -> {
			enumMap.put(ArmorItem.Type.BOOTS, 13);
			enumMap.put(ArmorItem.Type.LEGGINGS, 15);
			enumMap.put(ArmorItem.Type.CHESTPLATE, 16);
			enumMap.put(ArmorItem.Type.HELMET, 11);
		}
	);
	*///?}

	//? if >= 1.21.3 {
	private static final EnumMap<ArmorType, Integer> DEFENSE_FOR_TYPE = Util.make(
		new EnumMap<>(ArmorType.class),
		enumMap -> {
			enumMap.put(ArmorType.BOOTS, BOOTS_DEFENSE);
			enumMap.put(ArmorType.LEGGINGS, LEGGINGS_DEFENSE);
			enumMap.put(ArmorType.CHESTPLATE, CHESTPLATE_DEFENSE);
			enumMap.put(ArmorType.HELMET, HELMET_DEFENSE);
			enumMap.put(ArmorType.BODY, BODY_DEFENSE);
		}
	);
	//?} else {
	/*private static final EnumMap<ArmorItem.Type, Integer> DEFENSE_FOR_TYPE = Util.make(
		new EnumMap<>(ArmorItem.Type.class),
		enumMap -> {
			enumMap.put(ArmorItem.Type.BOOTS, BOOTS_DEFENSE);
			enumMap.put(ArmorItem.Type.LEGGINGS, LEGGINGS_DEFENSE);
			enumMap.put(ArmorItem.Type.CHESTPLATE, CHESTPLATE_DEFENSE);
			enumMap.put(ArmorItem.Type.HELMET, HELMET_DEFENSE);
			//? if >= 1.21.1 {
			enumMap.put(ArmorItem.Type.BODY, BODY_DEFENSE);
			//?}
		}
	);
	*///?}

	//? if >= 1.21.1 && < 1.21.3 {
	/*public static final ResourcefulRegistry<ArmorMaterial> ARMOR_MATERIALS =
		ResourcefulRegistries.create(BuiltInRegistries.ARMOR_MATERIAL, FriendsAndFoes.MOD_ID);
	*///?}

	//? if >= 1.21.3 {
	public static final ArmorMaterial WILDFIRE = new ArmorMaterial(
		DURABILITY,
		DEFENSE_FOR_TYPE,
		ENCHANTMENT_VALUE,
		FriendsAndFoesSoundEvents.ITEM_EQUIP_WILDFIRE_CROWN.holder(),
		TOUGHNESS,
		KNOCKBACK_RESISTANCE,
		FriendsAndFoesTags.REPAIRS_WILDFIRE_CROWN,
		FriendsAndFoesEquipmentAssets.WILDFIRE_CROWN
	);
	//?} else if >= 1.21.1 {
	/*public static final HolderRegistryEntry<ArmorMaterial> WILDFIRE = ARMOR_MATERIALS.registerHolder(
		"wildfire_crown",
		() -> new ArmorMaterial(
			DEFENSE_FOR_TYPE,
			ENCHANTMENT_VALUE,
			VersionedRegistryHolder.get(FriendsAndFoesSoundEvents.ITEM_EQUIP_WILDFIRE_CROWN),
			() -> Ingredient.of(FriendsAndFoesTags.REPAIRS_WILDFIRE_CROWN),
			List.of(new ArmorMaterial.Layer(FriendsAndFoes.makeID("wildfire_crown"))),
			TOUGHNESS,
			KNOCKBACK_RESISTANCE
		)
	);
	*///?} else {
	/*public static final ArmorMaterial WILDFIRE = createArmorMaterial(
		"wildfire_crown",
		DURABILITY,
		DEFENSE_FOR_TYPE,
		ENCHANTMENT_VALUE,
		FriendsAndFoesSoundEvents.ITEM_EQUIP_WILDFIRE_CROWN.get(),
		TOUGHNESS,
		KNOCKBACK_RESISTANCE,
		() -> Ingredient.of(FriendsAndFoesTags.REPAIRS_WILDFIRE_CROWN)
	);
	*///?}

	//? if < 1.21.1 {
	/*private static ArmorMaterial createArmorMaterial(
		String name,
		int durabilityMultiplier,
		EnumMap<ArmorItem.Type, Integer> defenseForType,
		int enchantmentValue,
		SoundEvent sound,
		float toughness,
		float knockbackResistance,
		Supplier<Ingredient> repairIngredient
	) {
		return new ArmorMaterial() {
			@Override
			public int getDurabilityForType(ArmorItem.Type type) {
				return DURABILITY_MULTIPLIER_PER_TYPE.get(type) * durabilityMultiplier;
			}

			@Override
			public int getDefenseForType(ArmorItem.Type type) {
				return defenseForType.get(type);
			}

			@Override
			public int getEnchantmentValue() {
				return enchantmentValue;
			}

			@Override
			public SoundEvent getEquipSound() {
				return sound;
			}

			@Override
			public Ingredient getRepairIngredient() {
				return repairIngredient.get();
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public float getToughness() {
				return toughness;
			}

			@Override
			public float getKnockbackResistance() {
				return knockbackResistance;
			}
		};
	}
	*///?}

	private FriendsAndFoesArmorMaterials() {
	}
}