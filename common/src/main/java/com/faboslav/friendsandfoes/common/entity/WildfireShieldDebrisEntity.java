package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedGameRulesProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.gamerules.GameRules;
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

		if(!(this.level() instanceof ServerLevel serverLevel)) {
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
		boolean hurtResult = VersionedEntity.hurt(target, damageSource, 5.0F);

		if (!hurtResult) {
			target.setRemainingFireTicks(i);
		} else {
			EnchantmentHelper.doPostAttackEffects(serverLevel, target, damageSource);
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
			|| VersionedGameRulesProvider.getBoolean(this, VersionedGameRulesProvider.MOB_GRIEFING)
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

	@Override
	/*? if >=1.21.3 {*/
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount)
	/*?} else {*/
	/*public boolean hurt(DamageSource damageSource, float amount)
	*//*?}*/
	{
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

