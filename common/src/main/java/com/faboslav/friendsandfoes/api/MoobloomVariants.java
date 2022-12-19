package com.faboslav.friendsandfoes.api;

import net.minecraft.block.FlowerBlock;
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

	@Nullable
	public static MoobloomVariant getByName(String name) {
		return MOOBLOOM_VARIANT_MAP.get(name);
	}

	@Nullable
	public static MoobloomVariant getByFlowerBlock(FlowerBlock flowerBlock) {
		for (MoobloomVariant moobloomVariant : MOOBLOOM_VARIANT_MAP.values()) {
			if (moobloomVariant.getFlowerBlock() == flowerBlock) {
				return moobloomVariant;
			}
		}

		return null;
	}

	@Nullable
	public static MoobloomVariant getByFlowerItem(Item flowerItem) {
		for (MoobloomVariant moobloomVariant : MOOBLOOM_VARIANT_MAP.values()) {
			if (moobloomVariant.getFlowerBlockAsItem() == flowerItem) {
				return moobloomVariant;
			}
		}

		return null;
	}

	public static void add(String name, FlowerBlock flowerBlock) {
		if (contains(name)) {
			throw new IllegalArgumentException(
				String.format(
					"Variant \"%s\" with flower block \"%s\" is already added.",
					name,
					flowerBlock.toString()
				)
			);
		}

		MOOBLOOM_VARIANT_MAP.put(name, new MoobloomVariant(name, flowerBlock));
	}

	public static void addMultiple(Map<String, FlowerBlock> items) {
		items.forEach(MoobloomVariants::add);
	}
}
