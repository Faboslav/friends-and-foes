package com.faboslav.friendsandfoes.common.util.world.processor;

import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;

//? if < 1.21.5 {
/*import net.minecraft.nbt.ListTag;
*///?}

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
		if (!VersionedNbt.getString(globalEntityInfo.nbt, "id", "").equals("minecraft:armor_stand")) {
			return globalEntityInfo;
		}

		RandomSource random = structurePlacementData.getRandom(globalEntityInfo.blockPos);
		CompoundTag newNbtCompound = globalEntityInfo.nbt.copy();

		//? if >= 1.21.5 {
		CompoundTag equipment = VersionedNbt.getCompound(newNbtCompound, "equipment");

		if (random.nextFloat() < 0.33F) {
			CompoundTag armorItem = new CompoundTag();
			armorItem.putString("id", Items.LEATHER_BOOTS.toString());
			armorItem.putInt("count", 1);
			equipment.put("feet", armorItem);
		}

		if (random.nextFloat() < 0.33F) {
			CompoundTag armorItem = new CompoundTag();
			armorItem.putString("id", Items.LEATHER_LEGGINGS.toString());
			armorItem.putInt("count", 1);
			equipment.put("legs", armorItem);
		}

		if (random.nextFloat() < 0.33F) {
			CompoundTag armorItem = new CompoundTag();
			armorItem.putString("id", Items.LEATHER_CHESTPLATE.toString());
			armorItem.putInt("count", 1);
			equipment.put("chest", armorItem);
		}

		CompoundTag armorItemHelmet = new CompoundTag();
		armorItemHelmet.putString("id", Items.LEATHER_HELMET.toString());
		armorItemHelmet.putInt("count", 1);
		equipment.put("head", armorItemHelmet);

		newNbtCompound.put("equipment", equipment);
		//?} else {
		/*ListTag armorItems = VersionedNbt.getList(newNbtCompound, "ArmorItems");

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
		*///?}

		globalEntityInfo = new StructureEntityInfo(
			globalEntityInfo.pos,
			globalEntityInfo.blockPos,
			newNbtCompound
		);

		return globalEntityInfo;
	}
}
