package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PatrolSpawner.class)
public interface PatrolSpawnerAccessor
{
	@Invoker("spawnPatrolMember")
	boolean callSpawnPillager(ServerLevel world, BlockPos pos, RandomSource random, boolean captain);
}
