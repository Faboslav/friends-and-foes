package com.faboslav.friendsandfoes.mixin;

import java.util.Arrays;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import com.faboslav.friendsandfoes.registry.BlockRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

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
        newBeehives[newBeehives.length - 1] = BlockRegistry.ACACIA_BEEHIVE;
        newBeehives[newBeehives.length - 2] = BlockRegistry.BIRCH_BEEHIVE;
        newBeehives[newBeehives.length - 3] = BlockRegistry.CRIMSON_BEEHIVE;
        newBeehives[newBeehives.length - 4] = BlockRegistry.DARK_OAK_BEEHIVE;
        newBeehives[newBeehives.length - 5] = BlockRegistry.JUNGLE_BEEHIVE;
        newBeehives[newBeehives.length - 6] = BlockRegistry.SPRUCE_BEEHIVE;
        newBeehives[newBeehives.length - 7] = BlockRegistry.WARPED_BEEHIVE;
        
        return newBeehives;
    }
}
