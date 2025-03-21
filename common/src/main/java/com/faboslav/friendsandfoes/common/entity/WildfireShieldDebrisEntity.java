package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public final class WildfireShieldDebrisEntity extends AbstractFireballEntity
{
	public WildfireShieldDebrisEntity(
		World world,
		LivingEntity owner,
		Vec3d velocity
	) {
		super(EntityType.SMALL_FIREBALL, owner, velocity, world);
	}

	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);

		if (this.getWorld().isClient()) {
			return;
		}

		Entity target = entityHitResult.getEntity();

		if (target.getType().isIn(FriendsAndFoesTags.WILDFIRE_ALLIES)) {
			return;
		}

		Entity wildfire = this.getOwner();

		int i = target.getFireTicks();
		target.setOnFireFor(5.0F);

		DamageSource damageSource = this.getDamageSources().fireball(this, wildfire);
		if (!target.damage(damageSource, 5.0F)) {
			target.setFireTicks(i);
		} else {
			EnchantmentHelper.onTargetDamaged((ServerWorld) this.getWorld(), target, damageSource);
		}
	}

	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);

		if (this.getWorld().isClient()) {
			return;
		}

		Entity entity = this.getOwner();

		if (
			!(entity instanceof MobEntity)
			|| this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)
		) {
			BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());

			if (this.getWorld().isAir(blockPos)) {
				this.getWorld().setBlockState(blockPos, AbstractFireBlock.getState(this.getWorld(), blockPos));
			}
		}
	}

	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);

		if (this.getWorld().isClient()) {
			return;
		}

		this.playImpactSound();
		this.discard();
	}

	public boolean canHit() {
		return false;
	}

	public boolean damage(DamageSource source, float amount) {
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

