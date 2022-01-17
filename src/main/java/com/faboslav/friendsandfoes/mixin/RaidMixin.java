package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.raid.Raid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raid.class)
public class RaidMixin
{
	@Shadow
	@Final
	private ServerWorld world;

	@Inject(method = "addRaider", at = @At("HEAD"), cancellable = true)
	public void addRaider(
		int wave,
		RaiderEntity raider,
		BlockPos pos,
		boolean existing,
		CallbackInfo ci
	) {
		if (
			raider instanceof IllusionerEntity
			&& FriendsAndFoes.CONFIG.enableIllusionerInRaids == false
		) {
			ci.cancel();
		}
	}

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
		if (
			raiderEntity instanceof EvokerEntity
			&& FriendsAndFoes.CONFIG.enableIllusionerInRaids
		) {
			int randomRaiderNumber = RandomGenerator.generateInt(0, 1);

			if (randomRaiderNumber == 1) {
				return raiderEntity;
			}

			return EntityType.EVOKER.create(this.world);
		}

		return raiderEntity;
	}
}
