package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.IllusionerEntityAccess;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
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
public abstract class IllusionerEntityMixin extends SpellcastingIllagerEntity implements RangedAttackMob, IllusionerEntityAccess
{
	private static final int MAX_ILLUSIONS_COUNT = 9;
	private static final int ILLUSION_LIFETIME_TICKS = 600;
	private static final int INVISIBILITY_TICKS = 60;

	private static final String IS_ILLUSION_NBT_NAME = "IsIllusion";
	private static final String WAS_ATTACKED_NBT_NAME = "WasAttacked";
	private static final String TICKS_UNTIL_DESPAWN_NBT_NAME = "TicksUntilDespawn";
	private static final String TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME = "TicksUntilCanCreateIllusions";

	private static final TrackedData<Boolean> IS_ILLUSION;
	private static final TrackedData<Boolean> WAS_ATTACKED;
	private static final TrackedData<Integer> TICKS_UNTIL_DESPAWN;
	private static final TrackedData<Integer> TICKS_UNTIL_CAN_CREATE_ILLUSIONS;

	private IllusionerEntity illusioner;

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
		this.illusioner = null;
	}

	@Override
	public void initDataTracker() {
		super.initDataTracker();

		if (FriendsAndFoes.getConfig().enableIllusioner == false) {
			return;
		}

		this.dataTracker.startTracking(IS_ILLUSION, false);
		this.dataTracker.startTracking(WAS_ATTACKED, false);
		this.dataTracker.startTracking(TICKS_UNTIL_DESPAWN, 0);
		this.dataTracker.startTracking(TICKS_UNTIL_CAN_CREATE_ILLUSIONS, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);

		if (FriendsAndFoes.getConfig().enableIllusioner == false) {
			return;
		}

		nbt.putBoolean(IS_ILLUSION_NBT_NAME, this.isIllusion());
		nbt.putBoolean(WAS_ATTACKED_NBT_NAME, this.wasAttacked());
		nbt.putInt(TICKS_UNTIL_DESPAWN_NBT_NAME, this.getTicksUntilDespawn());
		nbt.putInt(TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME, this.getTicksUntilCanCreateIllusions());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		if (FriendsAndFoes.getConfig().enableIllusioner == false) {
			return;
		}

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
		this.goalSelector.add(2, new FleeEntityGoal(this, IronGolemEntity.class, 8.0F, 0.6D, 1.0D));

		if (FriendsAndFoes.getConfig().enableIllusioner == false || this.isIllusion() == false) {
			this.goalSelector.add(3, BlindTargetGoalFactory.newBlindTargetGoal((IllusionerEntity) (Object) this));
		}

		this.goalSelector.add(4, new BowAttackGoal(this, 0.5D, 20, 15.0F));
		this.goalSelector.add(5, new WanderAroundGoal(this, 0.6D));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
		this.goalSelector.add(7, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
		this.targetSelector.add(1, (new RevengeGoal(this, RaiderEntity.class)).setGroupRevenge());
		this.targetSelector.add(2, (new ActiveTargetGoal(this, PlayerEntity.class, true)).setMaxTimeWithoutVisibility(300));
		this.targetSelector.add(3, (new ActiveTargetGoal(this, IronGolemEntity.class, false)).setMaxTimeWithoutVisibility(300));
		this.targetSelector.add(4, (new ActiveTargetGoal(this, MerchantEntity.class, false)).setMaxTimeWithoutVisibility(300));
	}

	@Override
	public void tick() {
		super.tick();

		if (FriendsAndFoes.getConfig().enableIllusioner == false) {
			return;
		}

		if (this.getWorld().isClient()) {
			return;
		}

		if (this.getTicksUntilCanCreateIllusions() > 0) {
			this.setTicksUntilCanCreateIllusions(this.getTicksUntilCanCreateIllusions() - 1);
		}

		if (
			(
				this.getTarget() instanceof PlayerEntity
				|| this.getTarget() instanceof IronGolemEntity
			)
			&& this.wasAttacked()
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
	}

	@Override
	public void tickMovement() {
		super.tickMovement();

		if (FriendsAndFoes.getConfig().enableIllusioner == false) {
			return;
		}

		if (
			this.getWorld().isClient()
			|| this.isIllusion() == false
		) {
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
	public boolean shouldDropXp() {
		if (FriendsAndFoes.getConfig().enableIllusioner == false) {
			return super.shouldDropXp();
		}

		return this.isIllusion() == false;
	}

	@Override
	protected boolean shouldDropLoot() {
		if (FriendsAndFoes.getConfig().enableIllusioner == false) {
			return super.shouldDropLoot();
		}

		return this.isIllusion() == false;
	}

	@Override
	public boolean damage(
		DamageSource source,
		float amount
	) {
		if (FriendsAndFoes.getConfig().enableIllusioner == false) {
			return super.damage(source, amount);
		}

		if (
			source.getAttacker() instanceof IllusionerEntity
			|| (
				this.isIllusion()
				&& !(source.getAttacker() instanceof LivingEntity)
			)
		) {
			return false;
		}

		if (
			this.getWorld().isClient()
			|| (
				source.getAttacker() instanceof PlayerEntity
				&& !this.isIllusion()
				&& ((PlayerEntity) source.getAttacker()).getAbilities().creativeMode
			)
		) {
			return super.damage(source, amount);
		}

		if (this.isIllusion()) {
			this.discardIllusion();
			return false;
		}

		if (
			(
				source.getAttacker() instanceof PlayerEntity
				|| source.getAttacker() instanceof IronGolemEntity
			)
			&& this.getTicksUntilCanCreateIllusions() == 0) {
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

		Vec3d illusionerPosition = this.getPos();
		float slice = 2.0F * (float) Math.PI / MAX_ILLUSIONS_COUNT;
		int radius = 9;
		int randomPoint = RandomGenerator.generateInt(0, MAX_ILLUSIONS_COUNT - 1);

		for (int point = 0; point < MAX_ILLUSIONS_COUNT; ++point) {
			float angle = slice * point;
			int x = (int) (illusionerPosition.getX() + radius * MathHelper.cos(angle));
			int y = (int) illusionerPosition.getY();
			int z = (int) (illusionerPosition.getZ() + radius * MathHelper.sin(angle));

			this.createIllusion(x, y, z);

			if (randomPoint == point) {
				boolean teleportResult = this.tryToTeleport(x, y, z);

				if (teleportResult) {
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, INVISIBILITY_TICKS));
					this.spawnCloudParticles();
				}
			}
		}
	}

	private void createIllusion(int x, int y, int z) {
		IllusionerEntity illusioner = (IllusionerEntity) (Object) this;
		IllusionerEntity illusion = EntityType.ILLUSIONER.create(this.getWorld());

		illusion.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
		IllusionerEntityAccess illusionerAccess = (IllusionerEntityAccess) illusion;
		illusionerAccess.setIsIllusion(true);
		illusionerAccess.setIllusioner(illusioner);
		illusionerAccess.setTicksUntilDespawn(ILLUSION_LIFETIME_TICKS);

		illusion.setHealth(this.getMaxHealth());
		illusion.copyPositionAndRotation(illusioner);
		illusion.setTarget(illusioner.getTarget());

		boolean teleportResult = illusionerAccess.tryToTeleport(x, y, z);

		if (teleportResult) {
			this.getEntityWorld().spawnEntity(illusion);
			illusionerAccess.spawnCloudParticles();
		}
	}

	public boolean tryToTeleport(int x, int y, int z) {
		y -= 8;
		double bottomY = Math.max(y, getWorld().getBottomY());
		double topY = Math.min(bottomY + 16, ((ServerWorld) this.getWorld()).getLogicalHeight() - 1);

		for (int i = 0; i < 16; ++i) {
			y = (int) MathHelper.clamp(y + 1, bottomY, topY);
			boolean teleportResult = this.teleport(x, y, z, false);

			if (teleportResult) {
				return true;
			}
		}

		return false;
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
		if (this.getWorld().isClient()) {
			return;
		}

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
	public IllusionerEntity getIllusioner() {
		return this.illusioner;
	}

	public void setIllusioner(IllusionerEntity illusioner) {
		this.illusioner = illusioner;
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
