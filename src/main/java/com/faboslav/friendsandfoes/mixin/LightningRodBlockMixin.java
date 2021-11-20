package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.entity.passive.CopperGolemEntity;
import com.faboslav.friendsandfoes.registry.EntityRegistry;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningRodBlock.class)
public class LightningRodBlockMixin
{
    @Inject(method = "onBlockAdded", at = @At("TAIL"))
    private void customOnBlockAdded(
            BlockState state,
            World world,
            BlockPos pos,
            BlockState oldState,
            boolean notify,
            CallbackInfo ci
    ) {
        if (!oldState.isOf(state.getBlock())) {
            this.trySpawnEntity(
                    world,
                    pos
            );
        }
    }

    private void trySpawnEntity(
            World world,
            BlockPos pos
    ) {
        BlockPattern copperGolemPattern = BlockPatternBuilder.start().aisle("|","#")
                .where('|', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.LIGHTNING_ROD)))
                .where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.COPPER_BLOCK)))
                .build();
        BlockPattern.Result patternSearchResult = copperGolemPattern.searchAround(world, pos);

        if (patternSearchResult==null) {
            return;
        }

        for (int i = 0; i < copperGolemPattern.getHeight(); ++i) {
            CachedBlockPosition cachedBlockPosition = patternSearchResult.translate(0, i, 0);
            world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), 1);
            world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
        }

        BlockPos cachedBlockPosition = patternSearchResult.translate(0, 1, 0).getBlockPos();
        CopperGolemEntity copperGolemEntity = EntityRegistry.COPPER_GOLEM.create(world);
        copperGolemEntity.refreshPositionAndAngles(
                (double) cachedBlockPosition.getX() + 0.5D,
                (double) cachedBlockPosition.getY() + 0.05D,
                (double) cachedBlockPosition.getZ() + 0.5D,
                0.0F,
                0.0F
        );
        world.spawnEntity(copperGolemEntity);

        for (ServerPlayerEntity serverPlayerEntity : world.getNonSpectatingEntities(
                ServerPlayerEntity.class,
                copperGolemEntity.getBoundingBox().expand(5.0D)
        )) {
            Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, copperGolemEntity);
        }

        for (int i = 0; i < copperGolemPattern.getHeight(); ++i) {
            CachedBlockPosition serverPlayerEntity = patternSearchResult.translate(0, i, 0);
            world.updateNeighbors(serverPlayerEntity.getBlockPos(), Blocks.AIR);
        }
    }
}
