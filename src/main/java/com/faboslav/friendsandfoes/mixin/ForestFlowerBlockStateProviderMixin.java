package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.registry.BlockRegistry;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
//import net.minecraft.world.gen.stateprovider.ForestFlowerBlockStateProvider;

/*
@Mixin(ForestFlowerBlockStateProvider.class)
abstract public class ForestFlowerBlockStateProviderMixin
{
    @Accessor("FLOWERS")
    public static BlockState[] getFlowers() {
        throw new AssertionError();
    }

    @Shadow
    @Final
    @Mutable
    private static final BlockState[] FLOWERS = addFlowers();

    private static BlockState[] addFlowers() {
        BuiltinRegistries.PLACED_FEATURE.get(new Identifier("flower_forest_flowers")).getDecoratedFeatures().toArray(addFlowers())
        BlockState[] minecraftFlowers = getFlowers();
        BlockState[] customFlowers = new BlockState[]{BlockRegistry.BUTTERCUP.getDefaultState()};
        BlockState[] newFlowers = ArrayUtils.addAll(minecraftFlowers, customFlowers);

        return newFlowers;
    }
}*/
