package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Arrays;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin
{
	@ModifyArg(
		method = "<clinit>",
		slice = @Slice(from = @At(
			value = "CONSTANT",
			args = "stringValue=beehive")
		),
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/block/entity/BlockEntityType$Builder;create(Lnet/minecraft/block/entity/BlockEntityType$BlockEntityFactory;[Lnet/minecraft/block/Block;)Lnet/minecraft/block/entity/BlockEntityType$Builder;",
			ordinal = 0
		)
	)

	private static Block[] addBeehiveBlocks(Block... original) {
		Block[] newBeehives = Arrays.copyOf(original, original.length + 7);
		newBeehives[newBeehives.length - 1] = ModBlocks.ACACIA_BEEHIVE;
		newBeehives[newBeehives.length - 2] = ModBlocks.BIRCH_BEEHIVE;
		newBeehives[newBeehives.length - 3] = ModBlocks.CRIMSON_BEEHIVE;
		newBeehives[newBeehives.length - 4] = ModBlocks.DARK_OAK_BEEHIVE;
		newBeehives[newBeehives.length - 5] = ModBlocks.JUNGLE_BEEHIVE;
		newBeehives[newBeehives.length - 6] = ModBlocks.SPRUCE_BEEHIVE;
		newBeehives[newBeehives.length - 7] = ModBlocks.WARPED_BEEHIVE;

		return newBeehives;
	}
}
