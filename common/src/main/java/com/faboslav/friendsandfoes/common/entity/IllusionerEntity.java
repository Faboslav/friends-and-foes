package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

//? if >= 1.21.9 {
/*import net.minecraft.world.entity.decoration.Mannequin;
*///?}

//? if >=1.21.6 {
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
//?} else {
/*import net.minecraft.nbt.CompoundTag;
 *///?}

//? if >=1.21.4 {
import net.minecraft.world.entity.monster.creaking.Creaking;
//?}

public class IllusionerEntity extends SpellcasterIllager implements RangedAttackMob
{
	private static final int MAX_ILLUSIONS_COUNT = FriendsAndFoes.getConfig().illusionerMaxIllusionsCount;
	private static final int ILLUSION_LIFETIME_TICKS = FriendsAndFoes.getConfig().illusionerIllusionLifetimeTicks;
	private static final int INVISIBILITY_TICKS =  FriendsAndFoes.getConfig().illusionerInvisibilityTicks;

	private static final String IS_ILLUSION_NBT_NAME = "IsIllusion";
	private static final String WAS_ATTACKED_NBT_NAME = "WasAttacked";
	private static final String TICKS_UNTIL_DESPAWN_NBT_NAME = "TicksUntilDespawn";
	private static final String TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME = "TicksUntilCanCreateIllusions";

	private IllusionerEntity illusioner;
	private boolean isIllusion;
	private boolean wasAttacked;
	private int ticksUntilDespawn;
	private int ticksUntilCanCreateIllusion;

	public IllusionerEntity(EntityType<? extends IllusionerEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 5;
		this.illusioner = null;
		this.isIllusion = false;
		this.wasAttacked = false;
		this.ticksUntilDespawn = 0;
		this.ticksUntilCanCreateIllusion = 0;

	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new SpellcasterIllager.SpellcasterCastingSpellGoal());
		//? if >=1.21.4 {
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Creaking.class, 8.0F, 1.0F, 1.2));
		//?}
		this.goalSelector.addGoal(5, new IllusionerBlindnessSpellGoal());
		this.goalSelector.addGoal(6, new RangedBowAttackGoal<>(this, 0.5F, 20, 15.0F));
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		//? if >= 1.21.9 {
		/*this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(this, Mannequin.class, true)).setUnseenMemoryTicks(300));
		*///?} else {
		this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(this, PlayerIllusionEntity.class, true)).setUnseenMemoryTicks(300));
		//?}
		this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, Player.class, true)).setUnseenMemoryTicks(300));
		this.targetSelector.addGoal(4, (new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false)).setUnseenMemoryTicks(300));
		this.targetSelector.addGoal(4, (new NearestAttackableTargetGoal<>(this, IronGolem.class, false)).setUnseenMemoryTicks(300));
	}

	@Override
	//? if >= 1.21.6 {
	public void addAdditionalSaveData(ValueOutput nbt)
	//?} else {
	/*public void addAdditionalSaveData(CompoundTag nbt)
	*///?}
	{
		nbt.putBoolean(IS_ILLUSION_NBT_NAME, this.isIllusion());
		nbt.putBoolean(WAS_ATTACKED_NBT_NAME, this.wasAttacked());
		nbt.putInt(TICKS_UNTIL_DESPAWN_NBT_NAME, this.getTicksUntilDespawn());
		nbt.putInt(TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME, this.getTicksUntilCanCreateIllusions());
	}

	@Override
	//? if >= 1.21.6 {
	public void readAdditionalSaveData(ValueInput nbt)
	//?} else {
	/*public void readAdditionalSaveData(CompoundTag nbt)
	*///?}
	{
		this.setIsIllusion(VersionedNbt.getBoolean(nbt, IS_ILLUSION_NBT_NAME, false));
		this.setWasAttacked(VersionedNbt.getBoolean(nbt, WAS_ATTACKED_NBT_NAME, false));
		this.setTicksUntilDespawn(VersionedNbt.getInt(nbt, TICKS_UNTIL_DESPAWN_NBT_NAME, 0));
		this.setTicksUntilCanCreateIllusions(VersionedNbt.getInt(nbt, TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME, 0));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.5F).add(Attributes.FOLLOW_RANGE, 18.0F).add(Attributes.MAX_HEALTH, 24.0F);
	}

	@Override
	public SpawnGroupData finalizeSpawn(
		ServerLevelAccessor level,
		DifficultyInstance difficulty,
		/*? if >=1.21.3 {*/
		EntitySpawnReason spawnReason,
		/*?} else {*/
		/*MobSpawnType spawnReason,
		 *//*?}*/
		@Nullable SpawnGroupData entityData
	) {
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
		return super.finalizeSpawn(level, difficulty, spawnReason, entityData);
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.level().isClientSide()) {
			return;
		}

		if (this.getTicksUntilCanCreateIllusions() > 0) {
			this.setTicksUntilCanCreateIllusions(this.getTicksUntilCanCreateIllusions() - 1);
		}

		if (
			this.wasAttacked()
			&& this.getTicksUntilCanCreateIllusions() == 0
		) {
			this.createIllusions();
		}

		if (
			this.wasAttacked()
			&& this.getTarget() == null
			&& this.getTicksUntilCanCreateIllusions() < ILLUSION_LIFETIME_TICKS / 3
		) {
			this.setWasAttacked(false);
			this.setTicksUntilCanCreateIllusions(0);
		}

		if (!this.isIllusion()) {
			return;
		}

		if (this.getTicksUntilDespawn() > 0) {
			this.setTicksUntilDespawn(this.getTicksUntilDespawn() - 1);
		}

		boolean isIllusionerNonExistingOrDead = this.getIllusioner() != null && !this.getIllusioner().isAlive();

		if (
			this.getTicksUntilDespawn() == 0
			|| isIllusionerNonExistingOrDead
		) {
			this.discardIllusion();
		}
	}

	@Override
	/*? if >=1.21.3 {*/
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount)
	/*?} else {*/
	/*public boolean hurt(DamageSource damageSource, float amount)
	*//*?}*/
	{
		Entity attacker = damageSource.getEntity();
		EntityType<?> attackerType = null;

		if(attacker != null) {
			attackerType = attacker.getType();
		}

		if (
			(
				attackerType != null
				&& (
					attackerType.is(EntityTypeTags.ILLAGER_FRIENDS)
					|| attackerType.is(EntityTypeTags.RAIDERS)
				)
			)
			|| (
				this.isIllusion()
				&& !(attacker instanceof LivingEntity)
			)
		) {
			return false;
		}

		if (!this.level().isClientSide()) {
			if (attackerType != null && !attackerType.is(EntityTypeTags.ILLAGER_FRIENDS) && !attackerType.is(EntityTypeTags.RAIDERS)) {
				if (this.isIllusion()) {
					this.discardIllusion();
					return false;
				}

				if (
					this.getTicksUntilCanCreateIllusions() == 0
					&& (
						!attackerType.is(EntityTypeTags.ILLAGER_FRIENDS)
						&& !attackerType.is(EntityTypeTags.RAIDERS)
						&& !(damageSource.getEntity() instanceof Player player && player.getAbilities().instabuild)
					)
				) {
					this.createIllusions();
				}
			}
		}

		/*? if >=1.21.3 {*/
		return super.hurtServer(level, damageSource, amount);
		/*?} else {*/
		/*return super.hurt(damageSource, amount);
		*//*?}*/
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return SoundEvents.ILLUSIONER_AMBIENT;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ILLUSIONER_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ILLUSIONER_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.ILLUSIONER_HURT;
	}

	@Override
	protected SoundEvent getCastingSoundEvent() {
		return SoundEvents.ILLUSIONER_CAST_SPELL;
	}

	public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
	}

	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		ItemStack itemStack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW));
		ItemStack itemStack2 = this.getProjectile(itemStack);
		AbstractArrow abstractArrow = ProjectileUtil.getMobArrow(this, itemStack2, velocity, itemStack);
		double d = target.getX() - this.getX();
		double e = target.getY(0.3333333333333333) - abstractArrow.getY();
		double f = target.getZ() - this.getZ();
		double g = Math.sqrt(d * d + f * f);
		Level var15 = this.level();

		//? if >= 1.21.4 {
		if (var15 instanceof ServerLevel serverLevel) {
			Projectile.spawnProjectileUsingShoot(abstractArrow, serverLevel, itemStack2, d, e + g * (double)0.2F, f, 1.6F, (float)(14 - serverLevel.getDifficulty().getId() * 4));
		}
		//?} else {
		/*abstractArrow.shoot(d, e + g * (double)0.2F, f, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
		this.level().addFreshEntity(abstractArrow);
		*///?}

		this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
	}

	@Override
	public AbstractIllager.IllagerArmPose getArmPose() {
		if (this.isCastingSpell()) {
			return IllagerArmPose.SPELLCASTING;
		} else {
			return this.isAggressive() ? IllagerArmPose.BOW_AND_ARROW : IllagerArmPose.CROSSED;
		}
	}

	private void discardIllusion() {
		this.playMirrorSound();
		this.spawnCloudParticles();
		this.discard();
	}

	private void createIllusions() {
		this.setWasAttacked(true);
		this.setTicksUntilCanCreateIllusions(ILLUSION_LIFETIME_TICKS);
		this.playMirrorSound();
		this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, INVISIBILITY_TICKS));
		this.spawnCloudParticles();

		if(MAX_ILLUSIONS_COUNT == 0) {
			return;
		}

		Vec3 illusionerPosition = this.position();
		float slice = 2.0F * (float) Math.PI / MAX_ILLUSIONS_COUNT;
		int radius = 9;
		int randomPoint = this.getRandom().nextIntBetweenInclusive(0, MAX_ILLUSIONS_COUNT - 1);

		for (int point = 0; point < MAX_ILLUSIONS_COUNT; ++point) {
			float angle = slice * point;
			int x = (int) (illusionerPosition.x() + radius * Mth.cos(angle));
			int y = (int) illusionerPosition.y();
			int z = (int) (illusionerPosition.z() + radius * Mth.sin(angle));

			if (randomPoint == point) {
				this.tryToTeleport(x, y, z);
			} else {
				this.createIllusion(x, y, z);
			}
		}
	}

	private void createIllusion(int x, int y, int z) {
		IllusionerEntity illusioner = this;
		IllusionerEntity illusion = FriendsAndFoesEntityTypes.ILLUSIONER.get().create(this.level()/*? if >=1.21.3 {*/, EntitySpawnReason.MOB_SUMMONED/*?}*/);

		illusion.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
		illusion.setIsIllusion(true);
		illusion.setIllusioner(illusioner);
		illusion.setTicksUntilDespawn(ILLUSION_LIFETIME_TICKS);
		illusion.setHealth(this.getMaxHealth());
		illusion.copyPosition(illusioner);

		boolean teleportResult = this.tryToTeleport(x, y, z);

		if (teleportResult) {
			this.level().addFreshEntity(illusion);
			this.spawnCloudParticles();
		}
	}

	public boolean tryToTeleport(int x, int y, int z) {
		y -= 8;
		//? if >=1.21.3 {
		int worldBottomY = this.level().getMinY();
		//?} else {
		/*int worldBottomY = this.level().getMinBuildHeight();
		 *///?}
		double bottomY = Math.max(y, worldBottomY);
		double topY = Math.min(bottomY + 16, ((ServerLevel) this.level()).getLogicalHeight() - 1);

		for (int i = 0; i < 16; ++i) {
			y = (int) Mth.clamp(y + 1, bottomY, topY);
			boolean teleportResult = this.randomTeleport(x, y, z, false);

			if (teleportResult) {
				return true;
			}
		}

		return false;
	}

	private void playMirrorSound() {
		this.playSound(
			SoundEvents.ILLUSIONER_MIRROR_MOVE,
			this.getSoundVolume(),
			this.getVoicePitch()
		);
	}

	public void spawnCloudParticles() {
		this.spawnParticles(ParticleTypes.CLOUD, 16);
	}

	private <T extends ParticleOptions> void spawnParticles(
		T particleType,
		int amount
	) {
		if (this.level().isClientSide()) {
			return;
		}

		for (int i = 0; i < amount; i++) {
			((ServerLevel) this.level()).sendParticles(
				particleType,
				this.getRandomX(0.5D),
				this.getRandomY() + 0.5D,
				this.getRandomZ(0.5D),
				1,
				0.0D,
				0.0D,
				0.0D,
				0.0D
			);
		}
	}

	public boolean isIllusion() {
		return this.isIllusion;
	}

	public void setIsIllusion(boolean isIllusion) {
		this.isIllusion = isIllusion;
	}

	public boolean wasAttacked() {
		return this.wasAttacked;
	}

	public void setWasAttacked(boolean wasAttacked) {
		this.wasAttacked = wasAttacked;
	}

	@Nullable
	public IllusionerEntity getIllusioner() {
		return this.illusioner;
	}

	public void setIllusioner(IllusionerEntity illusioner) {
		this.illusioner = illusioner;
	}

	public int getTicksUntilDespawn() {
		return this.ticksUntilDespawn;
	}

	public void setTicksUntilDespawn(int ticksUntilDespawn) {
		this.ticksUntilDespawn = ticksUntilDespawn;
	}

	public int getTicksUntilCanCreateIllusions() {
		return this.ticksUntilCanCreateIllusion;
	}

	public void setTicksUntilCanCreateIllusions(int ticksUntilCanCreateIllusions) {
		this.ticksUntilCanCreateIllusion = ticksUntilCanCreateIllusions;
	}
	
	public class IllusionerBlindnessSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
		private int lastTargetId;

		IllusionerBlindnessSpellGoal() {
			super();
		}

		public boolean canUse() {
			if (!super.canUse()) {
				return false;
			} else if (IllusionerEntity.this.getTarget() == null) {
				return false;
			} else if (IllusionerEntity.this.getTarget().getId() == this.lastTargetId) {
				return false;
			} else {
				return IllusionerEntity.this.level().getCurrentDifficultyAt(IllusionerEntity.this.blockPosition()).isHarderThan((float)Difficulty.NORMAL.ordinal());
			}
		}

		public void start() {
			super.start();
			LivingEntity livingEntity = IllusionerEntity.this.getTarget();
			if (livingEntity != null) {
				this.lastTargetId = livingEntity.getId();
			}

		}

		protected int getCastingTime() {
			return 20;
		}

		protected int getCastingInterval() {
			return 180;
		}

		protected void performSpellCasting() {
			IllusionerEntity.this.getTarget().addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200), IllusionerEntity.this);
		}

		protected SoundEvent getSpellPrepareSound() {
			return SoundEvents.ILLUSIONER_PREPARE_BLINDNESS;
		}

		protected SpellcasterIllager.IllagerSpell getSpell() {
			return IllagerSpell.BLINDNESS;
		}
	}
}
