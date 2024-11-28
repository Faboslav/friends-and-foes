package com.faboslav.friendsandfoes.common.init;

import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentModels;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;/*? >=1.21.2 {*/
public interface FriendsAndFoesArmorMaterials
{
	ArmorMaterial WILDFIRE = new ArmorMaterial(5, Util.make(new EnumMap(EquipmentType.class), map -> {
		map.put(EquipmentType.BOOTS, 3);
		map.put(EquipmentType.LEGGINGS, 6);
		map.put(EquipmentType.CHESTPLATE, 8);
		map.put(EquipmentType.HELMET, 3);
		map.put(EquipmentType.BODY, 11);
	}), 9, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 1.0F, 0.0F, ItemTags.REPAIRS_LEATHER_ARMOR, EquipmentModels.LEATHER);
}
/*?} else {*/
/*
import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.registry.ReferenceRegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class FriendsAndFoesArmorMaterials
{
	public static final ResourcefulRegistry<ArmorMaterial> ARMOR_MATERIALS = ResourcefulRegistries.create(Registries.ARMOR_MATERIAL, FriendsAndFoes.MOD_ID);

	public final static ReferenceRegistryEntry<ArmorMaterial> WILDFIRE = ARMOR_MATERIALS.registerReference("wildfire", () -> createArmorMaterial(
		"wildfire",
		Util.make(new EnumMap<>(ArmorItem.Type.class), enumMap -> {
			enumMap.put(ArmorItem.Type.BOOTS, 3);
			enumMap.put(ArmorItem.Type.LEGGINGS, 6);
			enumMap.put(ArmorItem.Type.CHESTPLATE, 8);
			enumMap.put(ArmorItem.Type.HELMET, 3);
			enumMap.put(ArmorItem.Type.BODY, 11);
		}),
		9,
		SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
		1.0F,
		0.0F,
		() -> Ingredient.ofItems(FriendsAndFoesItems.WILDFIRE_CROWN_FRAGMENT.get()))
	);

	private static ArmorMaterial createArmorMaterial(
		String layerName,
		EnumMap<ArmorItem.Type, Integer> enumMap,
		int enchantmentValue,
		net.minecraft.registry.entry.RegistryEntry<SoundEvent> equipSound,
		float toughness,
		float knockback,
		Supplier<Ingredient> repairIngredient
	) {
		EnumMap<ArmorItem.Type, Integer> defenseMap = new EnumMap<>(ArmorItem.Type.class);

		for (ArmorItem.Type type : ArmorItem.Type.values()) {
			defenseMap.put(type, enumMap.get(type));
		}

		List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(Identifier.tryParse(layerName)));

		return new ArmorMaterial(defenseMap, enchantmentValue, equipSound, repairIngredient, layers, toughness, knockback);
	}

	private FriendsAndFoesArmorMaterials() {
	}
}
*//*?}*/