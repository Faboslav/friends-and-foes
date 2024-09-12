package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.BlazeEntityAccess;
import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.UUID;

@Mixin(BlazeEntity.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class BlazeEntityMixin extends BlazeLivingEntityMixin implements BlazeEntityAccess
{
	private static final String WILDFIRE_UUID_NBT_NAME = "WildfireUuid";

	private Optional<UUID> friendsandfoes_wildfireUuid = Optional.empty();

	protected BlazeEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);

		this.friendsandfoes_wildfireUuid = Optional.empty();
	}

	@Override
	public void friendsandfoes_writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		if (this.friendsandfoes_getWildfireUuid() != null) {
			nbt.putUuid(WILDFIRE_UUID_NBT_NAME, this.friendsandfoes_getWildfireUuid());
		}
	}

	@Override
	public void friendsandfoes_readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		if (nbt.containsUuid(WILDFIRE_UUID_NBT_NAME)) {
			this.friendsandfoes_setWildfireUuid(nbt.getUuid(WILDFIRE_UUID_NBT_NAME));
		}
	}

	@Override
	public void friendsandfoes_onDeath(DamageSource damageSource, CallbackInfo ci) {
		if (this.getWorld() instanceof ServerWorld) {
			WildfireEntity wildfireEntity = this.friendsandfoes_getWildfire();

			if (wildfireEntity != null) {
				wildfireEntity.setSummonedBlazesCount(wildfireEntity.getSummonedBlazesCount() - 1);
			}
		}
	}

	@Nullable
	public UUID friendsandfoes_getWildfireUuid() {
		return this.friendsandfoes_wildfireUuid.orElse(null);
	}

	public void friendsandfoes_setWildfireUuid(@Nullable UUID uuid) {
		this.friendsandfoes_wildfireUuid = Optional.ofNullable(uuid);
	}

	public void friendsandfoes_setWildfire(WildfireEntity wildfire) {
		this.friendsandfoes_setWildfireUuid(wildfire.getUuid());
	}

	@Nullable
	public WildfireEntity friendsandfoes_getWildfire() {
		try {
			ServerWorld serverWorld = (ServerWorld) this.getWorld();
			UUID uUID = this.friendsandfoes_getWildfireUuid();
			return uUID == null ? null:(WildfireEntity) serverWorld.getEntity(uUID);
		} catch (IllegalArgumentException var2) {
			return null;
		}
	}
}
