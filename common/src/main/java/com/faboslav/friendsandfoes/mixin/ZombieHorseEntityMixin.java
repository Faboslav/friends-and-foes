package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.entity.ZombieHorseEntityAccess;
import com.faboslav.friendsandfoes.entity.ai.goal.ZombieHorseTrapTriggerGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ZombieHorseEntity.class)
public abstract class ZombieHorseEntityMixin extends AbstractHorseEntity implements ZombieHorseEntityAccess
{
	private final ZombieHorseTrapTriggerGoal trapTriggerGoal = new ZombieHorseTrapTriggerGoal((ZombieHorseEntity) (Object) this);
	private static final int DESPAWN_AGE = 18000;
	private boolean isTrapped;
	private int trapTime;

	protected ZombieHorseEntityMixin(EntityType<? extends AbstractHorseEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);

		nbt.putBoolean("ZombieTrap", this.isTrapped());
		nbt.putInt("ZombieTrapTime", this.trapTime);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		this.setTrapped(nbt.getBoolean("ZombieTrap"));
		this.trapTime = nbt.getInt("ZombieTrapTime");
	}

	@Override
	public void tickMovement() {
		super.tickMovement();

		if (this.isTrapped() && this.trapTime++ >= DESPAWN_AGE) {
			this.discard();
		}
	}

	public boolean isTrapped() {
		return this.isTrapped;
	}

	public void setTrapped(boolean isTrapped) {
		if (isTrapped == this.isTrapped) {
			return;
		}

		this.isTrapped = isTrapped;

		if (isTrapped) {
			this.goalSelector.add(1, this.trapTriggerGoal);
		} else {
			this.goalSelector.remove(this.trapTriggerGoal);
		}
	}
}
