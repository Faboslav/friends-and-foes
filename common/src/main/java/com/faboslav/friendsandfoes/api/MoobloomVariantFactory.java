package com.faboslav.friendsandfoes.api;

import com.faboslav.friendsandfoes.entity.MoobloomEntity;
import net.minecraft.block.FlowerBlock;

public final class MoobloomVariantFactory
{
	public static MoobloomEntity.Variant create(
		String name,
		FlowerBlock flowerBlock
	) {
		MoobloomEntity.Variant[] variants = MoobloomEntity.Variant.values();
		MoobloomEntity.Variant lastVariant = variants[variants.length-1];
		int ordinal = variants[variants.length-1].ordinal()+1;
		int id = lastVariant.getId()+1;
		MoobloomEntity.Variant variant = ModdedAxolotlVariantImpl.create(ordinal, id, name);
		((AxolotlTypeExtension) (Object) variant).mavapi$metadata().modded();
		((AxolotlTypeExtension) (Object) variant).mavapi$metadata().setId(this.id);

		return variant;
	}
}
