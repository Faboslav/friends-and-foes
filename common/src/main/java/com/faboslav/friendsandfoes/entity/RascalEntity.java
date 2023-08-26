package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.client.render.entity.animation.RascalAnimations;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.entity.ai.brain.RascalBrain;
import com.faboslav.friendsandfoes.entity.animation.AnimatedEntity;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public final class RascalEntity extends PassiveEntity implements AnimatedEntity
{
	private AnimationContextTracker animationContextTracker;

	public RascalEntity(EntityType<? extends PassiveEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public AnimationContextTracker getAnimationContextTracker() {
		if (this.animationContextTracker == null) {
			this.animationContextTracker = new AnimationContextTracker();

			for (KeyframeAnimation keyframeAnimation : RascalAnimations.ANIMATIONS) {
				this.animationContextTracker.add(keyframeAnimation);
			}
		}

		return this.animationContextTracker;
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return null;
	}

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return RascalBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<RascalEntity> getBrain() {
		return (Brain<RascalEntity>) super.getBrain();
	}

	@Override
	protected void mobTick() {
		this.getWorld().getProfiler().push("rascalBrain");
		this.getBrain().tick((ServerWorld) this.getWorld(), this);
		this.getWorld().getProfiler().pop();
		this.getWorld().getProfiler().push("rascalActivityUpdate");
		RascalBrain.updateActivities(this);
		this.getWorld().getProfiler().pop();

		super.mobTick();
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0D)
			.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	public void tick() {
		if (FriendsAndFoes.getConfig().enableRascal == false) {
			this.discard();
		}

		super.tick();
	}

	public void spawnParticles(
		ParticleEffect particleEffect,
		int amount
	) {
		World world = this.getWorld();

		if (world.isClient()) {
			return;
		}

		for (int i = 0; i < amount; i++) {
			((ServerWorld) world).spawnParticles(
				particleEffect,
				this.getParticleX(1.0D),
				this.getRandomBodyY() + 0.5D,
				this.getParticleZ(1.0D),
				1,
				this.getRandom().nextGaussian() * 0.02D,
				this.getRandom().nextGaussian() * 0.02D,
				this.getRandom().nextGaussian() * 0.02D,
				1.0D
			);
		}
	}
}

