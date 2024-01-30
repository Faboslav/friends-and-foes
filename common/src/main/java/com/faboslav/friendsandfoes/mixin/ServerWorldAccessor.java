package com.faboslav.friendsandfoes.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.spawner.SpecialSpawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ServerWorld.class)
public interface ServerWorldAccessor
{
	@Accessor("spawners")
	List<SpecialSpawner> getSpawners();

	@Accessor("spawners")
	@Final
	@Mutable
	void setSpawners(List<SpecialSpawner> spawners);
}
