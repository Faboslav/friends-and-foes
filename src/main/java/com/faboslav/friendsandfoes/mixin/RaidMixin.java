package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.raid.Raid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Raid.class)
public class RaidMixin
{
	@Shadow
	@Final
	private ServerWorld world;

	@ModifyVariable(
		method = "spawnNextWave",
		ordinal = 1,
		at = @At(
			value = "STORE"
		)
	)
	private RaiderEntity modifyRaiderEntity(
		RaiderEntity raiderEntity
	) {
		if(raiderEntity instanceof EvokerEntity) {
			int randomRaiderNumber = RandomGenerator.generateInt(1, 2);

			if(randomRaiderNumber == 1) {
				return raiderEntity;
			}

			return EntityType.EVOKER.create(this.world);
		}

		return raiderEntity;
	}
}
