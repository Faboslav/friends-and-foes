package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.registry.ReferenceRegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * @see net.minecraft.world.item.ArmorMaterials
 */
public class FriendsAndFoesArmorMaterials
{
	public static final ResourcefulRegistry<ArmorMaterial> ARMOR_MATERIALS = ResourcefulRegistries.create(BuiltInRegistries.ARMOR_MATERIAL, FriendsAndFoes.MOD_ID);

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
		SoundEvents.ARMOR_EQUIP_NETHERITE,
		1.0F,
		0.0F,
		() -> Ingredient.of(FriendsAndFoesItems.WILDFIRE_CROWN_FRAGMENT.get()))
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

		List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(ResourceLocation.tryParse(layerName)));

		return new ArmorMaterial(defenseMap, enchantmentValue, equipSound, repairIngredient, layers, toughness, knockback);
	}

	private FriendsAndFoesArmorMaterials() {
	}
}
