package com.faboslav.friendsandfoes.common.init;

//? >=1.21.3 {
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import net.minecraft.Util;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.EnumMap;

/**
 * @see net.minecraft.world.item.equipment.ArmorMaterials
 */
public interface FriendsAndFoesArmorMaterials {
	ArmorMaterial WILDFIRE = new ArmorMaterial(37, Util.make(new EnumMap(ArmorType.class), enumMap -> {
		enumMap.put(ArmorType.BOOTS, 3);
		enumMap.put(ArmorType.LEGGINGS, 6);
		enumMap.put(ArmorType.CHESTPLATE, 8);
		enumMap.put(ArmorType.HELMET, 3);
		enumMap.put(ArmorType.BODY, 11);
	}), 9, FriendsAndFoesSoundEvents.ITEM_EQUIP_WILDFIRE_CROWN.holder(), 1.0F, 0.0F, FriendsAndFoesTags.REPAIRS_WILDFIRE_CROWN, FriendsAndFoesEquipmentAssets.WILDFIRE_CROWN);
}
//?} else {
/*import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import net.minecraft.world.item.ArmorMaterial;
import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.crafting.Ingredient;

public final class FriendsAndFoesArmorMaterials
{
	public static final ResourcefulRegistry<ArmorMaterial> ARMOR_MATERIALS = ResourcefulRegistries.create(BuiltInRegistries.ARMOR_MATERIAL, FriendsAndFoes.MOD_ID);

	public final static HolderRegistryEntry<ArmorMaterial> WILDFIRE = ARMOR_MATERIALS.registerHolder("wildfire_crown", () -> createArmorMaterial(
		"wildfire_crown",
		Util.make(new EnumMap<>(ArmorItem.Type.class), enumMap -> {
			enumMap.put(ArmorItem.Type.BOOTS, 3);
			enumMap.put(ArmorItem.Type.LEGGINGS, 6);
			enumMap.put(ArmorItem.Type.CHESTPLATE, 8);
			enumMap.put(ArmorItem.Type.HELMET, 3);
			enumMap.put(ArmorItem.Type.BODY, 11);
		}),
		9,
		FriendsAndFoesSoundEvents.ITEM_EQUIP_WILDFIRE_CROWN.holder(),
		1.0F,
		0.0F,
		() -> Ingredient.of(FriendsAndFoesTags.REPAIRS_WILDFIRE_CROWN))
	);

	private static ArmorMaterial createArmorMaterial(
		String layerName,
		EnumMap<ArmorItem.Type, Integer> enumMap,
		int enchantmentValue,
		net.minecraft.core.Holder<SoundEvent> equipSound,
		float toughness,
		float knockback,
		Supplier<Ingredient> repairIngredient
	) {
		EnumMap<ArmorItem.Type, Integer> defenseMap = new EnumMap<>(ArmorItem.Type.class);

		for (ArmorItem.Type type : ArmorItem.Type.values()) {
			defenseMap.put(type, enumMap.get(type));
		}

		List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(FriendsAndFoes.makeID(layerName)));

		return new ArmorMaterial(defenseMap, enchantmentValue, equipSound, repairIngredient, layers, toughness, knockback);
	}

	private FriendsAndFoesArmorMaterials() {
	}
}
*///?}
