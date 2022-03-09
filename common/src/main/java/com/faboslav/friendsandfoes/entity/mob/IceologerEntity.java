package com.faboslav.friendsandfoes.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer.Builder;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"rawtypes", "unchecked"})
public class IceologerEntity extends SpellcastingIllagerEntity
{
	public static final String SUMMON_ICE_CHUNK_SPELL_NAME = "SUMMON_ICE_CHUNK";

	public IceologerEntity(EntityType<? extends IceologerEntity> entityType, World world) {
		super(entityType, world);
		this.experiencePoints = 10;
	}

	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new LookAtTargetGoal());
		this.goalSelector.add(2, new FleeEntityGoal(this, PlayerEntity.class, 8.0F, 0.6D, 1.0D));
		this.goalSelector.add(2, new FleeEntityGoal(this, IronGolemEntity.class, 8.0F, 0.6D, 1.0D));
		this.goalSelector.add(3, new SummonIceChunkGoal());
		this.goalSelector.add(4, new WanderAroundGoal(this, 0.6D));
		this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
		this.goalSelector.add(6, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
		this.targetSelector.add(1, (new RevengeGoal(this, RaiderEntity.class)).setGroupRevenge());
		this.targetSelector.add(2, (new ActiveTargetGoal(this, PlayerEntity.class, true)).setMaxTimeWithoutVisibility(300));
		this.targetSelector.add(3, (new ActiveTargetGoal(this, MerchantEntity.class, false)).setMaxTimeWithoutVisibility(300));
		this.targetSelector.add(3, (new ActiveTargetGoal(this, IronGolemEntity.class, false)).setMaxTimeWithoutVisibility(300));
	}

	public static Builder createAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 18.0D).add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0D);
	}

	protected void initDataTracker() {
		super.initDataTracker();
	}

	public Box getVisibilityBoundingBox() {
		return this.getBoundingBox().expand(3.0D, 0.0D, 3.0D);
	}

	public SoundEvent getCelebratingSound() {
		return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
	}

	public boolean isTeammate(Entity other) {
		if (super.isTeammate(other)) {
			return true;
		} else if (other instanceof LivingEntity && ((LivingEntity) other).getGroup() == EntityGroup.ILLAGER) {
			return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
		} else {
			return false;
		}
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ILLUSIONER_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_ILLUSIONER_HURT;
	}

	protected SoundEvent getCastSpellSound() {
		return SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL;
	}

	public void addBonusForWave(int wave, boolean unused) {
	}

	private class SummonIceChunkGoal extends CastSpellGoal
	{
		@Override
		protected void castSpell() {
			LivingEntity target = IceologerEntity.this.getTarget();
			this.summonIceChunk(target);
		}

		@Override
		protected int getSpellTicks() {
			return 50;
		}

		@Override
		protected int startTimeDelay() {
			return 160;
		}

		@Nullable
		@Override
		protected SoundEvent getSoundPrepare() {
			return null;
		}

		@Override
		protected Spell getSpell() {
			return Spell.valueOf(SUMMON_ICE_CHUNK_SPELL_NAME);
		}

		private void summonIceChunk(LivingEntity target) {
			var world = IceologerEntity.this.world;
			var iceChunk = IceologerIceChunkEntity.createWithOwnerAndTarget(
				world,
				IceologerEntity.this,
				target
			);

			world.spawnEntity(iceChunk);
		}
	}
}
