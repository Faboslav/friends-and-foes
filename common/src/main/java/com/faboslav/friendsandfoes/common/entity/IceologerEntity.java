package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class IceologerEntity extends SpellcasterIllager
{
	public IceologerEntity(EntityType<? extends IceologerEntity> entityType, Level world) {
		super(entityType, world);

		if (!FriendsAndFoes.getConfig().enableIceologer && !this.level().isClientSide()) {
			this.discard();
		}

		this.xpReward = 10;
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new SpellcasterCastingSpellGoal());
		this.goalSelector.addGoal(2, new AvoidEntityGoal(this, Player.class, 8.0F, 0.6D, 1.0D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal(this, IronGolem.class, 8.0F, 0.6D, 1.0D));
		this.goalSelector.addGoal(3, new SummonIceChunkGoal());
		this.goalSelector.addGoal(4, new SlowTargetGoal());
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.6D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal(this, Player.class, true)).setUnseenMemoryTicks(300));
		this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal(this, IronGolem.class, false)).setUnseenMemoryTicks(300));
		this.targetSelector.addGoal(4, (new NearestAttackableTargetGoal(this, AbstractVillager.class, false)).setUnseenMemoryTicks(300));
		this.targetSelector.addGoal(5, (new NearestAttackableTargetGoal(this, GlowSquid.class, false)).setUnseenMemoryTicks(300));
	}

	public static Builder createIceologerAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.5D).add(Attributes.FOLLOW_RANGE, 18.0D).add(Attributes.MAX_HEALTH, 40.0D);
	}

	public SoundEvent getCelebrateSound() {
		return FriendsAndFoesSoundEvents.ENTITY_ICEOLOGER_AMBIENT.get();
	}

	public void applyRaidBuffs(ServerLevel world, int wave, boolean unused) {
	}

	@Override
	public void tick() {
		if (!FriendsAndFoes.getConfig().enableIceologer) {
			this.discard();
		}

		super.tick();
	}

	protected SoundEvent getAmbientSound() {
		return FriendsAndFoesSoundEvents.ENTITY_ICEOLOGER_AMBIENT.get();
	}

	protected SoundEvent getDeathSound() {
		return FriendsAndFoesSoundEvents.ENTITY_ICEOLOGER_DEATH.get();
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return FriendsAndFoesSoundEvents.ENTITY_ICEOLOGER_HURT.get();
	}

	protected SoundEvent getCastingSoundEvent() {
		return FriendsAndFoesSoundEvents.ENTITY_ICEOLOGER_CAST_SPELL.get();
	}

	public void addBonusForWave(int wave, boolean unused) {
	}

	private class SummonIceChunkGoal extends SpellcasterUseSpellGoal
	{
		@Override
		protected void performSpellCasting() {
			LivingEntity target = IceologerEntity.this.getTarget();
			this.summonIceChunk(target);
		}

		@Override
		protected int getCastingTime() {
			return 30;
		}

		@Override
		protected int getCastingInterval() {
			return 160;
		}

		@Override
		protected SoundEvent getSpellPrepareSound() {
			return FriendsAndFoesSoundEvents.ENTITY_ICEOLOGER_PREPARE_SUMMON.get();
		}

		@Override
		protected IllagerSpell getSpell() {
			return IllagerSpell.FANGS;
		}

		private void summonIceChunk(LivingEntity target) {
			var world = IceologerEntity.this.level();
			var iceChunk = IceologerIceChunkEntity.createWithOwnerAndTarget(
				world,
				IceologerEntity.this,
				target
			);

			world.addFreshEntity(iceChunk);
		}
	}

	public final class SlowTargetGoal extends SpellcasterUseSpellGoal
	{
		@Override
		public boolean canUse() {
			return super.canUse()
				   && IceologerEntity.this.getTarget() != null;
		}

		@Override
		protected int getCastingTime() {
			return 20;
		}

		@Override
		protected int getCastingInterval() {
			return 220;
		}

		@Override
		protected void performSpellCasting() {
			var target = IceologerEntity.this.getTarget();

			if (target != null && target.isAlive() && target.canFreeze()) {
				target.setTicksFrozen(400);
			}
		}

		@Override
		protected SoundEvent getSpellPrepareSound() {
			return FriendsAndFoesSoundEvents.ENTITY_ICEOLOGER_PREPARE_SLOWNESS.get();
		}

		@Override
		protected IllagerSpell getSpell() {
			return IllagerSpell.BLINDNESS;
		}
	}
}
