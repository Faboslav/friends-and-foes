package com.faboslav.friendsandfoes.world.processor;

import com.faboslav.friendsandfoes.init.FriendsAndFoesStructureProcessorTypes;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate.StructureBlockInfo;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;

/**
 * Inspired by use in Better Witch Huts mod
 *
 * @author YUNGNICKYOUNG
 * <a href="https://github.com/YUNG-GANG/YUNGs-Better-Witch-Huts">https://github.com/YUNG-GANG/YUNGs-Better-Witch-Huts</a>
 */
public final class IllusionerShackBrewingStandProcessor extends StructureProcessor
{
	public static final IllusionerShackBrewingStandProcessor INSTANCE = new IllusionerShackBrewingStandProcessor();
	public static final Codec<IllusionerShackBrewingStandProcessor> CODEC = Codec.unit(() -> INSTANCE);

	@Override
	public StructureBlockInfo process(
		WorldView world,
		BlockPos pos,
		BlockPos pivot,
		StructureBlockInfo originalBlockInfo,
		StructureBlockInfo currentBlockInfo,
		StructurePlacementData structurePlacementData
	) {
		if (currentBlockInfo.state.getBlock() != Blocks.BREWING_STAND) {
			return currentBlockInfo;
		}

		Random random = structurePlacementData.getRandom(currentBlockInfo.pos);
		NbtCompound nbt = currentBlockInfo.nbt;
		NbtList itemsListNbt = nbt.getList("Items", 10);

		int randomNumber = random.nextInt(2);

		switch (randomNumber) {
			case 0 -> addBrewingRecipe(
				itemsListNbt,
				"minecraft:golden_carrot",
				"minecraft:night_vision",
				random
			);
			case 1 -> addBrewingRecipe(
				itemsListNbt,
				"minecraft:fermented_spider_eye",
				"minecraft:invisibility",
				random
			);
		}

		currentBlockInfo = new StructureBlockInfo(currentBlockInfo.pos, currentBlockInfo.state, nbt);

		return currentBlockInfo;
	}

	private void addBrewingRecipe(
		NbtList itemsListTag,
		String inputItemId,
		String outputPotionId,
		Random randomSource
	) {
		itemsListTag.add(Util.make(new NbtCompound(), itemTag -> {
			putInputItem(itemTag, inputItemId, (byte) (randomSource.nextInt(1) + 2));
		}));

		itemsListTag.add(Util.make(new NbtCompound(), itemTag -> {
			putPotionInSlot(itemTag, (byte) 1, outputPotionId);
			if (randomSource.nextFloat() < .5f) putPotionInSlot(itemTag, (byte) 0, outputPotionId);
			if (randomSource.nextFloat() < .5f) putPotionInSlot(itemTag, (byte) 2, outputPotionId);
		}));
	}

	private void putInputItem(NbtCompound itemTag, String itemId, byte count) {
		itemTag.putByte("Slot", (byte) 3);
		itemTag.putString("id", itemId);
		itemTag.putByte("Count", count);
	}

	private void putPotionInSlot(NbtCompound itemTag, byte slot, String potionId) {
		itemTag.putByte("Slot", slot);
		itemTag.putString("id", "minecraft:potion");
		itemTag.putByte("Count", (byte) 1);
		itemTag.put("tag", Util.make(new NbtCompound(), potionTag -> {
			potionTag.putString("Potion", potionId);
		}));
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return FriendsAndFoesStructureProcessorTypes.ILLUSIONER_SHACK_BREWING_STAND_PROCESSOR;
	}
}
