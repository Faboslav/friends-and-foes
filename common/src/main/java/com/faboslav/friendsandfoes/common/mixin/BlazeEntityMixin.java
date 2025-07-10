package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.BlazeEntityAccess;
import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

//? >=1.21.6 {
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
//?} else {
/*import net.minecraft.nbt.CompoundTag;
 *///?}

@Mixin(Blaze.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class BlazeEntityMixin extends BlazeLivingEntityMixin implements BlazeEntityAccess
{
	private static final String WILDFIRE_UUID_NBT_NAME = "WildfireUuid";

	@Nullable
	private UUID friendsandfoes_wildfireUuid;

	protected BlazeEntityMixin(EntityType<? extends Monster> entityType, Level world) {
		super(entityType, world);

		this.friendsandfoes_wildfireUuid = null;
	}

	@Override
	//? if >=1.21.6 {
	public void friendsandfoes_writeCustomDataToNbt(ValueOutput nbt, CallbackInfo ci)
	//?} else {
	/*public void friendsandfoes_writeCustomDataToNbt(CompoundTag nbt, CallbackInfo ci)
	*///?}
	{
		VersionedNbt.putUUID(nbt, WILDFIRE_UUID_NBT_NAME, this.friendsandfoes_getWildfireUuid());
	}

	@Override
	//? if >=1.21.6 {
	public void friendsandfoes_writeCustomDataToNbt(ValueInput nbt, CallbackInfo ci)
	//?} else {
	/*public void friendsandfoes_writeCustomDataFromNbt(CompoundTag nbt, CallbackInfo ci)
	*///?}
	{
		this.friendsandfoes_setWildfireUuid(VersionedNbt.getUUID(nbt, WILDFIRE_UUID_NBT_NAME));
	}

	@Override
	public void friendsandfoes_onDeath(DamageSource damageSource, CallbackInfo ci) {
		if (this.level() instanceof ServerLevel) {
			WildfireEntity wildfireEntity = this.friendsandfoes_getWildfire();

			if (wildfireEntity != null) {
				wildfireEntity.setSummonedBlazesCount(wildfireEntity.getSummonedBlazesCount() - 1);
			}
		}
	}

	@Nullable
	public UUID friendsandfoes_getWildfireUuid() {
		return this.friendsandfoes_wildfireUuid;
	}

	public void friendsandfoes_setWildfireUuid(@Nullable UUID uuid) {
		this.friendsandfoes_wildfireUuid = uuid;
	}

	public void friendsandfoes_setWildfire(WildfireEntity wildfire) {
		this.friendsandfoes_setWildfireUuid(wildfire.getUUID());
	}

	@Nullable
	public WildfireEntity friendsandfoes_getWildfire() {
		try {
			ServerLevel serverWorld = (ServerLevel) this.level();
			UUID uUID = this.friendsandfoes_getWildfireUuid();
			return uUID == null ? null:(WildfireEntity) serverWorld.getEntity(uUID);
		} catch (IllegalArgumentException var2) {
			return null;
		}
	}
}
