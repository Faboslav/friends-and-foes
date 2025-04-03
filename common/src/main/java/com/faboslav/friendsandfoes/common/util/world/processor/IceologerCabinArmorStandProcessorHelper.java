package com.faboslav.friendsandfoes.common.util.world.processor;

import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;

/**
 * Inspired by use in Better Strongholds mod
 *
 * @author YUNGNICKYOUNG
 * <a href="https://github.com/YUNG-GANG/YUNGs-Better-Strongholds">https://github.com/YUNG-GANG/YUNGs-Better-Strongholds</a>
 */
public final class IceologerCabinArmorStandProcessorHelper
{
	public static StructureEntityInfo processEntity(
		StructureEntityInfo globalEntityInfo,
		StructurePlaceSettings structurePlacementData
	) {
		if (!globalEntityInfo.nbt.getString("id").equals("minecraft:armor_stand")) {
			return globalEntityInfo;
		}

		RandomSource random = structurePlacementData.getRandom(globalEntityInfo.blockPos);
		CompoundTag newNbtCompound = globalEntityInfo.nbt.copy();

		ListTag armorItems = VersionedNbt.getList(newNbtCompound, "ArmorItems");

		if (random.nextFloat() < 0.33F) {
			CompoundTag armorItem = ((CompoundTag)armorItems.get(0));
			armorItem.putString("id", Items.LEATHER_BOOTS.toString());
			armorItem.putByte("Count", (byte) 1);
			CompoundTag bootsNbtCompound = new CompoundTag();
			bootsNbtCompound.putInt("Damage", 0);
			armorItem.put("tag", bootsNbtCompound);
		}

		if (random.nextFloat() < 0.33F) {
			CompoundTag armorItem = ((CompoundTag)armorItems.get(1));
			armorItem.putString("id", Items.LEATHER_LEGGINGS.toString());
			armorItem.putByte("Count", (byte) 1);
			CompoundTag bootsNbtCompound = new CompoundTag();
			bootsNbtCompound.putInt("Damage", 0);
			armorItem.put("tag", bootsNbtCompound);
		}

		if (random.nextFloat() < 0.33F) {
			CompoundTag armorItem = ((CompoundTag)armorItems.get(2));
			armorItem.putString("id", Items.LEATHER_CHESTPLATE.toString());
			armorItem.putByte("Count", (byte) 1);
			CompoundTag bootsNbtCompound = new CompoundTag();
			bootsNbtCompound.putInt("Damage", 0);
			armorItem.put("tag", bootsNbtCompound);
		}

		CompoundTag armorItemHelmet = ((CompoundTag)armorItems.get(3));
		armorItemHelmet.putString("id", Items.LEATHER_HELMET.toString());
		armorItemHelmet.putByte("Count", (byte) 1);
		CompoundTag bootsNbtCompound = new CompoundTag();
		bootsNbtCompound.putInt("Damage", 0);
		armorItemHelmet.put("tag", bootsNbtCompound);

		globalEntityInfo = new StructureEntityInfo(
			globalEntityInfo.pos,
			globalEntityInfo.blockPos,
			newNbtCompound
		);

		return globalEntityInfo;
	}
}
