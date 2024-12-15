package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public final class WildfireShieldDebrisEntity extends Fireball
{
	public WildfireShieldDebrisEntity(
		Level world,
		LivingEntity owner,
		Vec3 velocity
	) {
		super(EntityType.SMALL_FIREBALL, owner, velocity, world);
	}

	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);

		if (this.level().isClientSide()) {
			return;
		}

		Entity target = entityHitResult.getEntity();

		if (target.getType().is(FriendsAndFoesTags.WILDFIRE_ALLIES)) {
			return;
		}

		Entity wildfire = this.getOwner();

		int i = target.getRemainingFireTicks();
		target.igniteForSeconds(5.0F);

		DamageSource damageSource = this.damageSources().fireball(this, wildfire);
		if (!target.hurt(damageSource, 5.0F)) {
			target.setRemainingFireTicks(i);
		} else {
			EnchantmentHelper.doPostAttackEffects((ServerLevel) this.level(), target, damageSource);
		}
	}

	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);

		if (this.level().isClientSide()) {
			return;
		}

		Entity entity = this.getOwner();

		if (
			!(entity instanceof Mob)
			|| this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)
		) {
			BlockPos blockPos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());

			if (this.level().isEmptyBlock(blockPos)) {
				this.level().setBlockAndUpdate(blockPos, BaseFireBlock.getState(this.level(), blockPos));
			}
		}
	}

	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);

		if (this.level().isClientSide()) {
			return;
		}

		this.playImpactSound();
		this.discard();
	}

	public boolean isPickable() {
		return false;
	}

	public boolean hurt(DamageSource source, float amount) {
		return false;
	}

	private SoundEvent getImpactSound() {
		return FriendsAndFoesSoundEvents.ENTITY_WILDFIRE_SHIELD_DEBRIS_IMPACT.get();
	}

	private void playImpactSound() {
		SoundEvent soundEvent = this.getImpactSound();
		this.playSound(soundEvent, 1.0F, 0.95F + RandomGenerator.generateFloat(0.95F, 1.05F));
	}
}

