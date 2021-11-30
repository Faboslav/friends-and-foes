package com.faboslav.friendsandfoes.mixin;

import java.util.Optional;

import com.faboslav.friendsandfoes.entity.passive.CopperGolemEntity;
import com.faboslav.friendsandfoes.registry.EntityRegistry;
import org.apache.logging.log4j.core.jmx.Server;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

@Mixin(ServerWorld.class)
public class ServerWorldMixin
{
    /*
    @Inject(method = "getSurface", at = @At("TAIL"))
    protected void customGetSurface(BlockPos pos)
    {
        BlockPos blockPos = ServerWorld.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos);
        Optional<BlockPos> optional = this.getCopperGolemPos(blockPos);
        if (optional.isPresent()) {
            return (BlockPos)optional.get();
        }
    }

    private Optional<BlockPos> getCopperGolemPos(BlockPos pos) {
        Optional<BlockPos> optional = this.getPointOfInterestStorage().method_34712((poiType) -> {
            return poiType == PointOfInterestType.LIGHTNING_ROD;
        }, (posx) -> {
            return posx.getY() == this.toServerWorld().getTopY(Heightmap.Type.WORLD_SURFACE, posx.getX(), posx.getZ()) - 1;
        }, pos, 128, PointOfInterestStorage.OccupationStatus.ANY);
        return optional.map((posx) -> {
            return posx.up(1);
        });
    }*/
}
