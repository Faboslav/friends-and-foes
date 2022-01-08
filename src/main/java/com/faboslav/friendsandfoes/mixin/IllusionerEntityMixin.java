package com.faboslav.friendsandfoes.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IllusionerEntity.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class IllusionerEntityMixin extends SpellcastingIllagerEntity implements RangedAttackMob
{
	private IllusionerEntity illusionerEntity = null;
	//private int ticksUntilDespawn =

	protected IllusionerEntityMixin(
		EntityType<? extends SpellcastingIllagerEntity> entityType,
		World world
	) {
		super(entityType, world);
	}

	protected IllusionerEntityMixin(
		EntityType<? extends SpellcastingIllagerEntity> entityType,
		World world,
		IllusionerEntity illusionerEntity
	) {
		super(entityType, world);
		this.illusionerEntity = illusionerEntity;
	}

	@Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
	protected void initGoals(CallbackInfo ci) {
		super.initGoals();
		IllusionerEntity entity = (IllusionerEntity) (Object) this;

		if (this.isIllusion()) {
			this.goalSelector.add(0, new SwimGoal(entity));
			//this.goalSelector.add(1, new LookAtTargetGoal(entity));
			this.goalSelector.add(6, new BowAttackGoal(this, 0.5D, 20, 15.0F));
			this.goalSelector.add(8, new WanderAroundGoal(this, 0.6D));
			this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
			this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
			this.targetSelector.add(1, (new RevengeGoal(this, RaiderEntity.class)).setGroupRevenge());
			this.targetSelector.add(2, (new ActiveTargetGoal(this, PlayerEntity.class, true)).setMaxTimeWithoutVisibility(300));
			this.targetSelector.add(3, (new ActiveTargetGoal(this, MerchantEntity.class, false)).setMaxTimeWithoutVisibility(300));
			this.targetSelector.add(3, (new ActiveTargetGoal(this, IronGolemEntity.class, false)).setMaxTimeWithoutVisibility(300));
			ci.cancel();
		}
	}

	private boolean isIllusion() {
		return this.illusionerEntity != null;
	}

	private void createIllusions() {
		IllusionerEntity illusionEntity = EntityType.ILLUSIONER.create(this.world);


		/*
		if (cowEntity == null) {
			return;
		}

		cowEntity.setHealth(this.getHealth());
		cowEntity.copyPositionAndRotation(this);
		cowEntity.prevBodyYaw = this.prevBodyYaw;
		cowEntity.bodyYaw = this.bodyYaw;
		cowEntity.prevHeadYaw = this.prevHeadYaw;
		cowEntity.headYaw = this.headYaw;

		if (this.hasCustomName()) {
			cowEntity.setCustomName(this.getCustomName());
			cowEntity.setCustomNameVisible(this.isCustomNameVisible());
		}

		if (this.isPersistent()) {
			cowEntity.setPersistent();
		}*/
	}
}
