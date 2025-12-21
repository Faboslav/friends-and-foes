package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.ZombieHorseEntityAccess;
import com.faboslav.friendsandfoes.common.entity.ai.goal.zombiehorse.ZombieHorseTrapTriggerGoal;
import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.animal.equine.ZombieHorse;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.21.6 {
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
//?} else {
/*import net.minecraft.nbt.CompoundTag;
*///?}

@Mixin(ZombieHorse.class)
public abstract class ZombieHorseEntityMixin extends ZombieHorseAbstractHorseEntityMixin implements ZombieHorseEntityAccess
{
	@Unique
	private final ZombieHorseTrapTriggerGoal friendsandfoes_trapTriggerGoal = new ZombieHorseTrapTriggerGoal((ZombieHorse) (Object) this);

	@Unique
	private boolean friendsandfoes_isTrapped;

	@Unique
	private int friendsandfoes_trapTime;

	protected ZombieHorseEntityMixin(EntityType<? extends AbstractHorse> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	//? if >=1.21.6 {
	public void friendsandfoes_writeCustomDataToNbt(ValueOutput nbt, CallbackInfo ci)
	//?} else {
	/*public void friendsandfoes_writeCustomDataToNbt(CompoundTag nbt, CallbackInfo ci)
	*///?}
	{
		nbt.putBoolean("ZombieTrap", this.friendsandfoes_isTrapped());
		nbt.putInt("ZombieTrapTime", this.friendsandfoes_trapTime);
	}

	@Override
	//? if >=1.21.6 {
	public void friendsandfoes_readCustomDataFromNbt(ValueInput nbt, CallbackInfo ci)
	//?} else {
	/*public void friendsandfoes_readCustomDataFromNbt(CompoundTag nbt, CallbackInfo ci)
	*///?}
	{
		this.friendsandfoes_setTrapped(VersionedNbt.getBoolean(nbt, "ZombieTrap", false));
		this.friendsandfoes_trapTime = VersionedNbt.getInt(nbt, "ZombieTrapTime", 0);
	}

	@Override
	protected void friendsandfoes_tickMovement(CallbackInfo ci) {
		if (this.friendsandfoes_isTrapped() && this.friendsandfoes_trapTime++ >= 18000) {
			this.discard();
		}
	}

	public boolean friendsandfoes_isTrapped() {
		return this.friendsandfoes_isTrapped;
	}

	public void friendsandfoes_setTrapped(boolean isTrapped) {
		if (isTrapped == this.friendsandfoes_isTrapped) {
			return;
		}

		this.friendsandfoes_isTrapped = isTrapped;

		if (isTrapped) {
			this.goalSelector.addGoal(1, this.friendsandfoes_trapTriggerGoal);
		} else {
			this.goalSelector.removeGoal(this.friendsandfoes_trapTriggerGoal);
		}
	}
}
