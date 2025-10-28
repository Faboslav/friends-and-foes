package com.faboslav.friendsandfoes.common.mixin;

import org.spongepowered.asm.mixin.Mixin;

//? if >= 1.21.9 {
import net.minecraft.world.entity.decoration.Mannequin;
import org.spongepowered.asm.mixin.Unique;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.util.particle.ParticleSpawner;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import com.faboslav.friendsandfoes.common.entity.MannequinEntityAccess;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Optional;
import java.util.UUID;

@Mixin(Mannequin.class)
public abstract class MannequinMixin extends MannequinAvatarMixin implements MannequinEntityAccess
{
	@Unique
	private static final String  FRIENDSANDFOES_IS_ILLUSION_NBT_NAME = "FriendsAndFoesIsIllusion";

	@Unique
	private static final String FRIENDSANDFOES_TICKS_UNTIL_DESPAWN_NBT_NAME = "FriendsAndFoesTicksUntilDespawn";

	@Unique
	private boolean friendsandfoes$isIllusion = false;

	@Unique
	private int friendsandfoes$ticksUntilDespawn = 0;

	@Unique
	private Optional<UUID> friendsandfoes$playerUuid = Optional.empty();

	@Unique
	@Nullable
	private Player friendsandfoes$player = null;

	@Unique
	public void friendsandfoes$setPlayerUuid(@Nullable UUID uuid) {
		this.friendsandfoes$playerUuid = Optional.ofNullable(uuid);
	}

	@Nullable
	public Player friendsandfoes$getPlayer() {
		return this.friendsandfoes$player;
	}

	public void friendsandfoes$setPlayer(Player player) {
		this.friendsandfoes$player = player;
	}

	public void friendsandfoes$setIsIllusion(boolean isIllusion) {
		this.friendsandfoes$isIllusion = isIllusion;
	}

	public void friendsandfoes$setTicksUntilDespawn(int ticksUntilDespawn) {
		this.friendsandfoes$ticksUntilDespawn = ticksUntilDespawn;
	}

	private void friendsandfoes$discardIllusion() {
		this.friendsandfoes$playMirrorSound();
		ParticleSpawner.spawnParticles((Mannequin) (Object) this, ParticleTypes.CLOUD, 16, 0.1D);
		this.discard();
	}

	private void friendsandfoes$playMirrorSound() {
		this.playSound(
			FriendsAndFoesSoundEvents.ENTITY_PLAYER_MIRROR_MOVE.get(),
			this.getSoundVolume(),
			this.getVoicePitch()
		);
	}

	@Override
	protected void friendsandfoes$readAdditionalSaveData(ValueInput valueInput, Operation<Void> original) {
		super.friendsandfoes$readAdditionalSaveData(valueInput, original);

		this.friendsandfoes$setIsIllusion(valueInput.getBooleanOr(FRIENDSANDFOES_IS_ILLUSION_NBT_NAME, false));
		this.friendsandfoes$setTicksUntilDespawn(valueInput.getIntOr(FRIENDSANDFOES_TICKS_UNTIL_DESPAWN_NBT_NAME, 0));
	}

	@Override
	protected void friendsandfoes$addAdditionalSaveData(ValueOutput valueOutput, Operation<Void> original) {
		super.friendsandfoes$addAdditionalSaveData(valueOutput, original);

		valueOutput.putBoolean(FRIENDSANDFOES_IS_ILLUSION_NBT_NAME, this.friendsandfoes$isIllusion);
		valueOutput.putInt(FRIENDSANDFOES_TICKS_UNTIL_DESPAWN_NBT_NAME, this.friendsandfoes$ticksUntilDespawn);
	}

	@Override
	protected void friendsandfoes$aiStep(Operation<Void> original) {
		super.friendsandfoes$aiStep(original);

		if (this.level().isClientSide() || !this.friendsandfoes$isIllusion) {
			return;
		}

		if (this.friendsandfoes$ticksUntilDespawn > 0) {
			this.friendsandfoes$setTicksUntilDespawn(this.friendsandfoes$ticksUntilDespawn - 1);
		}

		boolean isPlayerNonExistingOrDead = this.friendsandfoes$getPlayer() != null && !this.friendsandfoes$getPlayer().isAlive();

		if (
			this.friendsandfoes$ticksUntilDespawn == 0
			|| isPlayerNonExistingOrDead
		) {
			this.friendsandfoes$discardIllusion();
		}
	}

	@Override
	protected boolean friendsandfoes$mannequinHurtServer(
		ServerLevel serverLevel, DamageSource damageSource, float f, Operation<Boolean> original
	) {
		if(!this.friendsandfoes$isIllusion) {
			return super.friendsandfoes$mannequinHurtServer(serverLevel, damageSource, f, original);
		}

		this.friendsandfoes$discardIllusion();
		return false;
	}
}
//?} else {
/*import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntity.class)
public abstract class MannequinMixin
{
}
*///?}

