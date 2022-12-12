package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.entity.BlazeEntityAccess;
import com.faboslav.friendsandfoes.entity.WildfireEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.UUID;

@Mixin(BlazeEntity.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class BlazeEntityMixin extends LivingEntity implements BlazeEntityAccess
{
	private static final String WILDFIRE_UUID_NBT_NAME = "WildfireUuid";
	private static final TrackedData<Optional<UUID>> WILDFIRE_UUID;

	static {
		WILDFIRE_UUID = DataTracker.registerData(BlazeEntityMixin.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
	}

	protected BlazeEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(
		at = @At("TAIL"),
		method = "initDataTracker"
	)
	public void initDataTracker(CallbackInfo ci) {
		this.dataTracker.startTracking(WILDFIRE_UUID, Optional.empty());
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);

		if (this.getWildfireUuid() != null) {
			nbt.putUuid(WILDFIRE_UUID_NBT_NAME, this.getWildfireUuid());
		}
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		if (nbt.containsUuid(WILDFIRE_UUID_NBT_NAME)) {
			this.setWildfireUuid(nbt.getUuid(WILDFIRE_UUID_NBT_NAME));
		}
	}

	@Override
	public void onDeath(DamageSource damageSource) {
		if (this.getWorld() instanceof ServerWorld) {
			WildfireEntity wildfireEntity = this.getWildfire();

			if (wildfireEntity != null) {
				wildfireEntity.setSummonedBlazesCount(wildfireEntity.getSummonedBlazesCount() - 1);
			}
		}

		super.onDeath(damageSource);
	}

	@Nullable
	public UUID getWildfireUuid() {
		return (UUID) ((Optional) this.dataTracker.get(WILDFIRE_UUID)).orElse(null);
	}

	public void setWildfireUuid(@Nullable UUID uuid) {
		this.dataTracker.set(WILDFIRE_UUID, Optional.ofNullable(uuid));
	}

	public void setWildfire(WildfireEntity wildfire) {
		this.setWildfireUuid(wildfire.getUuid());
	}

	@Nullable
	public WildfireEntity getWildfire() {
		try {
			ServerWorld serverWorld = (ServerWorld) this.getWorld();
			UUID uUID = this.getWildfireUuid();
			return uUID == null ? null:(WildfireEntity) serverWorld.getEntity(uUID);
		} catch (IllegalArgumentException var2) {
			return null;
		}
	}
}
