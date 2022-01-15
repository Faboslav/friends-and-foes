package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.api.IllusionerAccess;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IllusionerEntity.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class IllusionerEntityMixin extends SpellcastingIllagerEntity implements RangedAttackMob, IllusionerAccess
{
	private static final int MAX_ILLUSIONS_COUNT = 9;
	private static final int ILLUSION_LIFETIME_TICKS = 600;

	private static final String IS_ILLUSION_NBT_NAME = "IsIllusion";
	private static final String WAS_ATTACKED_NBT_NAME = "WasAttacked";
	private static final String TICKS_UNTIL_DESPAWN_NBT_NAME = "TicksUntilDespawn";
	private static final String TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME = "TicksUntilCanCreateIllusions";

	private static final TrackedData<Boolean> IS_ILLUSION;
	private static final TrackedData<Boolean> WAS_ATTACKED;
	private static final TrackedData<Integer> TICKS_UNTIL_DESPAWN;
	private static final TrackedData<Integer> TICKS_UNTIL_CAN_CREATE_ILLUSIONS;

	private IllusionerEntity illusionerEntity;

	static {
		IS_ILLUSION = DataTracker.registerData(IllusionerEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);
		WAS_ATTACKED = DataTracker.registerData(IllusionerEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);
		TICKS_UNTIL_DESPAWN = DataTracker.registerData(IllusionerEntityMixin.class, TrackedDataHandlerRegistry.INTEGER);
		TICKS_UNTIL_CAN_CREATE_ILLUSIONS = DataTracker.registerData(IllusionerEntityMixin.class, TrackedDataHandlerRegistry.INTEGER);
	}

	protected IllusionerEntityMixin(
		EntityType<? extends SpellcastingIllagerEntity> entityType,
		World world
	) {
		super(entityType, world);
		this.illusionerEntity = null;
	}

	@Override
	public void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(IS_ILLUSION, false);
		this.dataTracker.startTracking(WAS_ATTACKED, false);
		this.dataTracker.startTracking(TICKS_UNTIL_DESPAWN, 0);
		this.dataTracker.startTracking(TICKS_UNTIL_CAN_CREATE_ILLUSIONS, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean(IS_ILLUSION_NBT_NAME, this.isIllusion());
		nbt.putBoolean(WAS_ATTACKED_NBT_NAME, this.wasAttacked());
		nbt.putInt(TICKS_UNTIL_DESPAWN_NBT_NAME, this.getTicksUntilDespawn());
		nbt.putInt(TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME, this.getTicksUntilCanCreateIllusions());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setIsIllusion(nbt.getBoolean(IS_ILLUSION_NBT_NAME));
		this.setWasAttacked(nbt.getBoolean(WAS_ATTACKED_NBT_NAME));
		this.setTicksUntilDespawn(nbt.getInt(TICKS_UNTIL_DESPAWN_NBT_NAME));
		this.setTicksUntilCanCreateIllusions(nbt.getInt(TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME));
	}

	@Override
	public void initGoals() {
		super.initGoals();
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new LookAtTargetGoal());

		if (!this.isIllusion()) {
			//this.goalSelector.add(5, new IllusionerEntity.BlindTargetGoal());
		}

		this.goalSelector.add(6, new BowAttackGoal(this, 0.5D, 20, 15.0F));
		this.goalSelector.add(8, new WanderAroundGoal(this, 0.6D));
		this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
		this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
		this.targetSelector.add(1, (new RevengeGoal(this, RaiderEntity.class)).setGroupRevenge());
		this.targetSelector.add(2, (new ActiveTargetGoal(this, PlayerEntity.class, true)).setMaxTimeWithoutVisibility(300));
		this.targetSelector.add(3, (new ActiveTargetGoal(this, MerchantEntity.class, false)).setMaxTimeWithoutVisibility(300));
		this.targetSelector.add(3, (new ActiveTargetGoal(this, IronGolemEntity.class, false)).setMaxTimeWithoutVisibility(300));
	}

	@Override
	public void tick() {
		super.tick();

		if(
			this.getWorld().isClient()
			|| this.isIllusion()
		) {
			return;
		}

		if (this.getTicksUntilCanCreateIllusions() > 0) {
			this.setTicksUntilCanCreateIllusions(this.getTicksUntilCanCreateIllusions() - 1);
		}

		if (
			this.getTarget() instanceof PlayerEntity
			&& this.wasAttacked()
			&& this.getTicksUntilCanCreateIllusions() == 0
		) {
			this.createIllusions();
		}
	}

	@Override
	public void tickMovement() {
		super.tickMovement();

		if (
			!this.world.isClient() &&
			this.isIllusion()
		) {
			if (this.getTicksUntilDespawn() > 0) {
				this.setTicksUntilDespawn(this.getTicksUntilDespawn() - 1);
			}

			if (
				this.getTicksUntilDespawn() == 0
				|| (
					this.getIllusionerEntity() != null
					&& !this.getIllusionerEntity().isAlive()
				)
			) {
				this.discardIllusion();
			}
		}
	}

	@Override
	protected boolean shouldDropXp() {
		return !this.isIllusion();
	}

	@Override
	protected boolean shouldDropLoot() {
		return !this.isIllusion();
	}

	@Override
	public boolean damage(
		DamageSource source,
		float amount
	) {
		if (
			this.getWorld().isClient()
			|| (
				source.getAttacker() instanceof PlayerEntity
				&& ((PlayerEntity) source.getAttacker()).getAbilities().creativeMode
			)
		) {
			return super.damage(source, amount);
		}

		if (this.isIllusion()) {
			this.discardIllusion();
			return false;
		}

		if (this.getTicksUntilCanCreateIllusions() == 0) {
			this.createIllusions();
		}

		return super.damage(source, amount);
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
		this.spawnCloudParticles();

		Vec3d illusionerPosition = this.getPos();
		float slice = 2.0F * (float) Math.PI / MAX_ILLUSIONS_COUNT;
		int radius = 7;
		for (int point = 0; point < MAX_ILLUSIONS_COUNT; ++point) {
			float angle = slice * point;
			int x = (int) (illusionerPosition.getX() + radius * MathHelper.cos(angle));
			int y = (int) illusionerPosition.getY();
			int z = (int) (illusionerPosition.getZ() + radius * MathHelper.sin(angle));
			Vec3d randomPosition = new Vec3d(x, y, z);
			int randomPoint = RandomGenerator.generateInt(1, MAX_ILLUSIONS_COUNT);

			this.createIllusion(randomPosition);

			if (randomPoint == point) {
				this.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 100));
				this.setPosition(randomPosition);
			}
		}
	}

	private void createIllusion(Vec3d position) {
		IllusionerEntity illusionEntity = EntityType.ILLUSIONER.create(this.world);
		illusionEntity.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
		illusionEntity.setHealth(this.getMaxHealth());
		illusionEntity.copyPositionAndRotation((IllusionerEntity) (Object) this);
		illusionEntity.setPosition(position);
		IllusionerAccess illusionerAccess = (IllusionerAccess) illusionEntity;
		illusionerAccess.setIsIllusion(true);
		illusionerAccess.setIllusionerEntity((IllusionerEntity) (Object) this);
		illusionerAccess.setTicksUntilDespawn(ILLUSION_LIFETIME_TICKS);
		this.getEntityWorld().spawnEntity(illusionEntity);
		illusionerAccess.spawnCloudParticles();
	}

	private void playMirrorSound() {
		this.playSound(
			SoundEvents.ENTITY_ILLUSIONER_MIRROR_MOVE,
			this.getSoundVolume(),
			this.getSoundPitch()
		);
	}

	public void spawnCloudParticles() {
		this.spawnParticles(ParticleTypes.CLOUD, 16);
	}

	private void spawnParticles(
		DefaultParticleType particleType,
		int amount
	) {
		if (!this.world.isClient()) {
			for (int i = 0; i < amount; i++) {
				((ServerWorld) this.getEntityWorld()).spawnParticles(
					particleType,
					this.getParticleX(0.5D),
					this.getRandomBodyY() + 0.5D,
					this.getParticleZ(0.5D),
					1,
					0.0D,
					0.0D,
					0.0D,
					0.0D
				);
			}
		}
	}

	public boolean isIllusion() {
		return this.dataTracker.get(IS_ILLUSION);
	}

	public void setIsIllusion(boolean isIllusion) {
		this.dataTracker.set(IS_ILLUSION, isIllusion);
	}

	public boolean wasAttacked() {
		return this.dataTracker.get(WAS_ATTACKED);
	}

	public void setWasAttacked(boolean wasAttacked) {
		this.dataTracker.set(WAS_ATTACKED, wasAttacked);
	}

	@Nullable
	public IllusionerEntity getIllusionerEntity() {
		return this.illusionerEntity;
	}

	public void setIllusionerEntity(IllusionerEntity illusionerEntity) {
		this.illusionerEntity = illusionerEntity;
	}

	public int getTicksUntilDespawn() {
		return this.dataTracker.get(TICKS_UNTIL_DESPAWN);
	}

	public void setTicksUntilDespawn(int ticksUntilDespawn) {
		this.dataTracker.set(TICKS_UNTIL_DESPAWN, ticksUntilDespawn);
	}

	public int getTicksUntilCanCreateIllusions() {
		return this.dataTracker.get(TICKS_UNTIL_CAN_CREATE_ILLUSIONS);
	}

	public void setTicksUntilCanCreateIllusions(int ticksUntilCanCreateIllusions) {
		this.dataTracker.set(TICKS_UNTIL_CAN_CREATE_ILLUSIONS, ticksUntilCanCreateIllusions);
	}
}
