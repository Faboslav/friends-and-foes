package com.faboslav.friendsandfoes.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.spawner.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PatrolSpawner.class)
public interface PatrolSpawnerAccessor
{
	@Invoker
	boolean callSpawnPillager(ServerWorld world, BlockPos pos, Random random, boolean captain);
}
