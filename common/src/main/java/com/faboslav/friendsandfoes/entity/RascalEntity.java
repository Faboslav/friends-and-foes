package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.animation.KeyframeAnimation;
import com.faboslav.friendsandfoes.client.render.entity.animation.RascalAnimations;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.entity.ai.brain.RascalBrain;
import com.faboslav.friendsandfoes.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.mixin.ChunkRegionAccessor;
import com.faboslav.friendsandfoes.mixin.ServerWorldAccessor;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.StructureTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.structure.Structure;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public final class RascalEntity extends PassiveEntity implements AnimatedEntity
{
	private AnimationContextTracker animationContextTracker;

	public RascalEntity(EntityType<? extends PassiveEntity> entityType, World world) {
		super(entityType, world);
	}

	public static boolean canSpawn(
			EntityType<? extends MobEntity> rascalEntityType,
			ServerWorldAccess serverWorldAccess,
			SpawnReason spawnReason,
			BlockPos blockPos,
			Random random
	) {
		if(spawnReason == SpawnReason.NATURAL) {
			StructureAccessor structureAccessor = serverWorldAccess.toServerWorld().getStructureAccessor();

			if (
					structureAccessor.getStructureContaining(
							blockPos,
							StructureTags.MINESHAFT
					).hasChildren()
					&& blockPos.getY() < 63
					&& serverWorldAccess.isSkyVisible(blockPos) == false
			) {
				return true;
			}
		}

		return false;
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
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.8D)
			.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	public void tick() {
		if (FriendsAndFoes.getConfig().enableRascal == false) {
			this.discard();
		}

		super.tick();
	}

	@Override
	public boolean damage(
		DamageSource source, float amount
	) {
		Entity attacker = source.getAttacker();

		if (
			attacker == null
			|| attacker instanceof PlayerEntity == false
		) {
			return super.damage(source, amount);
		}

		this.playHurtSound(source);
		this.spawnCloudParticles();
		this.discard();

		return false;
	}

	public SoundEvent getApproveSound() {
		return FriendsAndFoesSoundEvents.ENTITY_RASCAL_APPROVE.get();
	}

	public void playApproveSound() {
		this.playSound(this.getApproveSound(), 1.0F, RandomGenerator.generateFloat(1.1F, 1.3F));
	}

	public SoundEvent getRewardSound() {
		return FriendsAndFoesSoundEvents.ENTITY_RASCAL_REWARD.get();
	}

	public void playRewardSound() {
		this.playSound(this.getRewardSound(), 1.0F, RandomGenerator.generateFloat(1.1F, 1.3F));
	}

	public SoundEvent getBadRewardSound() {
		return FriendsAndFoesSoundEvents.ENTITY_RASCAL_REWARD_BAD.get();
	}

	public void playBadRewardSound() {
		this.playSound(this.getBadRewardSound(), 1.0F, RandomGenerator.generateFloat(1.1F, 1.3F));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FriendsAndFoesSoundEvents.ENTITY_RASCAL_AMBIENT.get();
	}

	@Override
	public void playAmbientSound() {
		SoundEvent soundEvent = this.getAmbientSound();
		this.playSound(soundEvent, 1.0F, RandomGenerator.generateFloat(1.1F, 1.3F));
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FriendsAndFoesSoundEvents.ENTITY_RASCAL_HURT.get();
	}

	@Override
	protected void playHurtSound(DamageSource source) {
		this.ambientSoundChance = -this.getMinAmbientSoundDelay();
		this.playSound(this.getHurtSound(source), 1.0F, RandomGenerator.generateFloat(1.1F, 1.3F));
	}

	public void spawnCloudParticles() {
		this.spawnParticles(ParticleTypes.CLOUD, 16);
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

