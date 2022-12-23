package com.faboslav.friendsandfoes.api;

import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class MoobloomVariants
{
	public static final String DEFAULT_VARIANT_NAME = "buttercup";

	private static final HashMap<String, MoobloomVariant> MOOBLOOM_VARIANT_MAP = new HashMap<>();

	public static boolean contains(String name) {
		return MOOBLOOM_VARIANT_MAP.containsKey(name);
	}

	public static int getNumberOfVariants() {
		return MOOBLOOM_VARIANT_MAP.size();
	}

	public static MoobloomVariant getRandomVariant() {
		Object[] values = MOOBLOOM_VARIANT_MAP.values().toArray();
		return (MoobloomVariant) values[RandomGenerator.generateInt(0, values.length - 1)];
	}

	@Nullable
	public static MoobloomVariant getByName(String name) {
		return MOOBLOOM_VARIANT_MAP.get(name);
	}

	@Nullable
	public static MoobloomVariant getByFlowerBlock(FlowerBlock flower) {
		for (MoobloomVariant moobloomVariant : MOOBLOOM_VARIANT_MAP.values()) {
			if (moobloomVariant.getFlower() == flower) {
				return moobloomVariant;
			}
		}

		return null;
	}

	@Nullable
	public static MoobloomVariant getByFlowerItem(Item flowerItem) {
		for (MoobloomVariant moobloomVariant : MOOBLOOM_VARIANT_MAP.values()) {
			if (moobloomVariant.getFlowerAsItem() == flowerItem) {
				return moobloomVariant;
			}
		}

		return null;
	}

	public static void add(String name, PlantBlock flower) {
		if (contains(name)) {
			throw new IllegalArgumentException(
				String.format(
					"Variant \"%s\" with flower block \"%s\" is already added.",
					name,
					flower.toString()
				)
			);
		}

		MOOBLOOM_VARIANT_MAP.put(name, new MoobloomVariant(name, flower));
	}

	public static void addMultiple(Map<String, FlowerBlock> items) {
		items.forEach(MoobloomVariants::add);
	}
}
