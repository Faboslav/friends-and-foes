package com.faboslav.friendsandfoes.entity.passive;

import com.faboslav.friendsandfoes.entity.ai.pathing.GlareNavigation;
import com.faboslav.friendsandfoes.entity.passive.ai.goal.*;
import com.faboslav.friendsandfoes.registry.SoundRegistry;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer.Builder;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@SuppressWarnings({"rawtypes", "unchecked"})
public class GlareEntity extends PathAwareEntity implements Tameable, Flutterer
{
	private static final int GRUMPY_BITMASK = 2;
	private static final float MOVEMENT_SPEED = 0.5F;
	public static final int MIN_TICKS_UNTIL_CAN_FIND_DARK_SPOT = 200;
	public static final int MAX_TICKS_UNTIL_CAN_FIND_DARK_SPOT = 400;

	private float currentBodyPitchProgress;
	private float lastBodyPitchProgress;
	private Vec2f currentEyesPositionOffset;
	private Vec2f targetEyesPositionOffset;
	private float currentLayerPitch;
	private float currentLayerRoll;
	private float currentLayerPitchAnimationProgress;
	private float currentLayerRollAnimationProgress;

	private static final TrackedData<Byte> TAMEABLE_FLAGS;
	private static final TrackedData<Byte> GLARE_FLAGS;
	private static final TrackedData<Optional<UUID>> OWNER_UUID;
	private static final TrackedData<Integer> TICKS_UNTIL_CAN_FIND_DARK_SPOT;

	public GlareEntity(EntityType<? extends GlareEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new FlightMoveControl(this, 4, true);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
		this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);

		this.currentEyesPositionOffset = new Vec2f(0.0F, 0.0F);
		this.targetEyesPositionOffset = new Vec2f(0.0F, 0.0F);
		this.currentLayerPitch = 0.0F;
		this.currentLayerRoll = 0.0F;
		this.currentLayerPitchAnimationProgress = 0.0F;
		this.currentLayerRollAnimationProgress = 0.0F;
	}

	static {
		TAMEABLE_FLAGS = DataTracker.registerData(GlareEntity.class, TrackedDataHandlerRegistry.BYTE);
		OWNER_UUID = DataTracker.registerData(GlareEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
		TICKS_UNTIL_CAN_FIND_DARK_SPOT = DataTracker.registerData(GlareEntity.class, TrackedDataHandlerRegistry.INTEGER);
		GLARE_FLAGS = DataTracker.registerData(GlareEntity.class, TrackedDataHandlerRegistry.BYTE);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(TAMEABLE_FLAGS, (byte) 0);
		this.dataTracker.startTracking(GLARE_FLAGS, (byte) 0);
		this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
		this.dataTracker.startTracking(
			TICKS_UNTIL_CAN_FIND_DARK_SPOT,
			RandomGenerator.generateInt(
				MIN_TICKS_UNTIL_CAN_FIND_DARK_SPOT,
				MAX_TICKS_UNTIL_CAN_FIND_DARK_SPOT
			)
		);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if (this.getOwnerUuid() != null) {
			nbt.putUuid("Owner", this.getOwnerUuid());
		}
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		UUID uUID;
		if (nbt.containsUuid("Owner")) {
			uUID = nbt.getUuid("Owner");
		} else {
			String string = nbt.getString("Owner");
			uUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
		}

		if (uUID != null) {
			try {
				this.setOwnerUuid(uUID);
				this.setTamed(true);
			} catch (Throwable var4) {
				this.setTamed(false);
			}
		}
	}

	private boolean hasGlareFlag(int bitmask) {
		return (this.dataTracker.get(GLARE_FLAGS) & bitmask) != 0;
	}

	private void setGlareFlag(int bitmask, boolean value) {
		byte glareFlags = this.dataTracker.get(GLARE_FLAGS);

		if (value) {
			this.dataTracker.set(GLARE_FLAGS, (byte) (glareFlags | bitmask));
		} else {
			this.dataTracker.set(GLARE_FLAGS, (byte) (glareFlags & ~bitmask));
		}

	}

	public static boolean canSpawn(
		EntityType<GlareEntity> glareEntityEntityType,
		ServerWorldAccess serverWorldAccess,
		SpawnReason spawnReason,
		BlockPos blockPos,
		Random random
	) {
		BlockState blockState = serverWorldAccess.getBlockState(blockPos.down());
		boolean isBelowYLevel63 = blockPos.getY() < 63;
		boolean isSkyVisible = serverWorldAccess.isSkyVisible(blockPos);
		boolean isRelatedBlock = (
			blockState.isOf(Blocks.MOSS_BLOCK)
			|| blockState.isOf(Blocks.MOSS_CARPET)
			|| blockState.isOf(Blocks.AZALEA)
			|| blockState.isOf(Blocks.FLOWERING_AZALEA)
			|| blockState.isOf(Blocks.GRASS)
			|| blockState.isOf(Blocks.SMALL_DRIPLEAF)
			|| blockState.isOf(Blocks.BIG_DRIPLEAF)
			|| blockState.isOf(Blocks.CLAY)
			|| blockState.isOf(Blocks.GRAVEL)
		);
		boolean isBlockBlockLightLevelDark = serverWorldAccess.getLightLevel(LightType.BLOCK, blockPos) == 0;
		boolean isBlockSkyLevelDark = serverWorldAccess.getLightLevel(LightType.SKY, blockPos) == 0;

		if (
			!isBelowYLevel63
			|| isSkyVisible
			|| !isRelatedBlock
			|| isBlockBlockLightLevelDark
			|| isBlockSkyLevelDark
		) {
			return false;
		}

		return true;
	}

	protected void initGoals() {
		this.goalSelector.add(1, new GlareAvoidMonsterGoal(this, AbstractSkeletonEntity.class, 24.0F));
		this.goalSelector.add(1, new GlareAvoidMonsterGoal(this, CreeperEntity.class, 24.0F));
		this.goalSelector.add(1, new GlareAvoidMonsterGoal(this, EndermanEntity.class, 24.0F));
		this.goalSelector.add(1, new GlareAvoidMonsterGoal(this, SpiderEntity.class, 24.0F));
		this.goalSelector.add(1, new GlareAvoidMonsterGoal(this, WitchEntity.class, 24.0F));
		this.goalSelector.add(1, new GlareAvoidMonsterGoal(this, ZombieEntity.class, 24.0F));
		this.goalSelector.add(2, new GlareFollowOwnerGoal(this, 10.0F, 20.0F, false));
		this.goalSelector.add(3, new GlareFlyToDarkSpotGoal(this));
		this.goalSelector.add(4, new GlareEatGlowBerriesGoal(this));
		this.goalSelector.add(5, new GlareWanderAroundGoal(this));
		this.goalSelector.add(6, new SwimGoal(this));
	}

	@Override
	public void tick() {
		super.tick();

		if (this.getTicksUntilCanFindDarkSpot() > 0) {
			this.setTicksUntilCanFindDarkSpot(this.getTicksUntilCanFindDarkSpot() - 1);
		}

		this.updateTargetEyesPositionOffset();
	}

	public Vec2f getCurrentEyesPositionOffset() {
		return this.currentEyesPositionOffset;
	}

	public void setCurrentEyesPositionOffset(Vec2f currentEyesPositionOffset) {
		this.currentEyesPositionOffset = currentEyesPositionOffset;
	}

	public Vec2f getTargetEyesPositionOffset() {
		return this.targetEyesPositionOffset;
	}

	private void updateTargetEyesPositionOffset() {
		if (this.getRandom().nextInt(40) == 0) {

			float xEyePosition = RandomGenerator.generateFloat(-0.5F, 0.5F);
			float yEyePosition = RandomGenerator.generateFloat(-0.4F, 0.4F);
			this.targetEyesPositionOffset = new Vec2f(
				xEyePosition,
				yEyePosition
			);
		}
	}

	public float getCurrentLayersPitch() {
		return this.currentLayerPitch;
	}

	public void setCurrentLayerPitch(float currentLayersPitch) {
		this.currentLayerPitch = currentLayersPitch;
	}

	public float getCurrentLayersRoll() {
		return this.currentLayerRoll;
	}

	public void setCurrentLayerRoll(float currentLayersRoll) {
		this.currentLayerRoll = currentLayersRoll;
	}

	public float getCurrentLayerPitchAnimationProgress() {
		return this.currentLayerPitchAnimationProgress;
	}

	public void setCurrentLayerPitchAnimationProgress(float currentLayersPitchAnimationProgress) {
		this.currentLayerPitchAnimationProgress = currentLayersPitchAnimationProgress;
	}

	public float getCurrentLayerRollAnimationProgress() {
		return this.currentLayerRollAnimationProgress;
	}

	public void setCurrentLayerRollAnimationProgress(float currentLayersRollAnimationProgress) {
		this.currentLayerRollAnimationProgress = currentLayersRollAnimationProgress;
	}

	public static Builder createGlareAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
			.add(EntityAttributes.GENERIC_FLYING_SPEED, MOVEMENT_SPEED)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D);
	}

	protected EntityNavigation createNavigation(World world) {
		GlareNavigation glareNavigation = new GlareNavigation(this, world)
		{
			public boolean isValidPosition(BlockPos pos) {
				return !this.world.getBlockState(pos.down()).isAir();
			}
		};

		glareNavigation.setCanPathThroughDoors(false);
		glareNavigation.setCanSwim(false);
		glareNavigation.setCanEnterOpenDoors(true);

		return glareNavigation;
	}

	protected void playStepSound(BlockPos pos, BlockState state) {
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundRegistry.ENTITY_GLARE_AMBIENT;
	}

	@Override
	public void playAmbientSound() {
		SoundEvent soundEvent = this.getAmbientSound();
		this.playSound(soundEvent, 0.25F, 1F);
	}

	@Override
	public SoundEvent getEatSound(ItemStack stack) {
		return SoundRegistry.ENTITY_GLARE_EAT;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundRegistry.ENTITY_GLARE_HURT;
	}

	@Override
	protected void playHurtSound(DamageSource source) {
		this.playSound(this.getHurtSound(source), 1.0F, 0.75F);
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_BEE_DEATH;
	}

	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	public ActionResult interactMob(
		PlayerEntity player,
		Hand hand
	) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item itemInHand = itemStack.getItem();
		boolean interactionResult = false;

		if (itemInHand == Items.GLOW_BERRIES) {
			if (this.isTamed()) {
				interactionResult = this.tryToHealWithGlowBerries(player, itemStack);
			} else {
				interactionResult = this.tryToTameWithGlowBerries(player, itemStack);
			}
		}

		if (interactionResult) {
			this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
			return ActionResult.success(this.world.isClient);
		}

		return super.interactMob(player, hand);
	}

	private boolean tryToHealWithGlowBerries(
		PlayerEntity player,
		ItemStack itemStack
	) {
		if (this.getHealth() == this.getMaxHealth()) {
			return false;
		}

		if (this.world.isClient) {
			return true;
		}

		Item glowBerries = itemStack.getItem();

		if (glowBerries.getFoodComponent() == null) {
			return false;
		}

		this.heal((float) glowBerries.getFoodComponent().getHunger());

		if (!player.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}

		this.playSound(this.getEatSound(itemStack), 1.0F, 1.0F);

		return true;
	}

	private boolean tryToTameWithGlowBerries(
		PlayerEntity player,
		ItemStack itemStack
	) {
		if (this.world.isClient) {
			return true;
		}

		if (!player.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}

		this.playSound(this.getEatSound(itemStack), 1.0F, 1.0F);

		if (this.random.nextInt(5) == 0) {
			this.setOwner(player);
			this.world.sendEntityStatus(this, (byte) 7);
		} else {
			this.world.sendEntityStatus(this, (byte) 6);
		}

		return true;
	}

	public boolean isInAir() {
		return !this.onGround;
	}

	protected void swimUpward(Tag<Fluid> fluid) {
		this.setVelocity(this.getVelocity().add(0.0D, 0.01D, 0.0D));
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
	}

	public boolean canBeLeashedBy(PlayerEntity player) {
		return !this.isLeashed();
	}

	protected void showEmoteParticle(boolean positive) {
		ParticleEffect particleEffect;

		if (positive) {
			particleEffect = ParticleTypes.HEART;
		} else {
			particleEffect = ParticleTypes.SMOKE;
		}

		for (int i = 0; i < 7; ++i) {
			double d = this.random.nextGaussian() * 0.02D;
			double e = this.random.nextGaussian() * 0.02D;
			double f = this.random.nextGaussian() * 0.02D;
			this.world.addParticle(particleEffect, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), d, e, f);
		}
	}

	public void handleStatus(byte status) {
		if (status == 7) {
			this.showEmoteParticle(true);
		} else if (status == 6) {
			this.showEmoteParticle(false);
		} else {
			super.handleStatus(status);
		}
	}

	public boolean isTamed() {
		return (this.dataTracker.get(TAMEABLE_FLAGS) & 4) != 0;
	}

	public void setTamed(boolean tamed) {
		byte b = this.dataTracker.get(TAMEABLE_FLAGS);
		if (tamed) {
			this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b | 4));
			this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0D);
			this.setHealth(this.getMaxHealth());
		} else {
			this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b & -5));
			this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(10.0D);
		}

		this.onTamedChanged();
	}

	protected void onTamedChanged() {
	}

	@Nullable
	public UUID getOwnerUuid() {
		return (UUID) ((Optional) this.dataTracker.get(OWNER_UUID)).orElse(null);
	}

	public void setOwnerUuid(@Nullable UUID uuid) {
		this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
	}

	public void setOwner(PlayerEntity player) {
		this.setTamed(true);
		this.setOwnerUuid(player.getUuid());
		if (player instanceof ServerPlayerEntity) {
			// maybe advancement for taming glare?
		}
	}

	@Nullable
	public LivingEntity getOwner() {
		try {
			UUID uUID = this.getOwnerUuid();
			return uUID == null ? null:this.world.getPlayerByUuid(uUID);
		} catch (IllegalArgumentException var2) {
			return null;
		}
	}

	public boolean isOwner(LivingEntity entity) {
		return entity == this.getOwner();
	}

	public float getBodyPitchProgress(float tickDelta) {
		if (!this.getWorld().isClient()) {
			return 0.0F;
		}

		return MathHelper.lerp(tickDelta, this.lastBodyPitchProgress, this.currentBodyPitchProgress);
	}

	private void updateGrumpyAnimation() {
		if (!this.getWorld().isClient()) {
			return;
		}

		this.lastBodyPitchProgress = this.currentBodyPitchProgress;
		boolean isMoving = !this.isOnGround() && this.getVelocity().lengthSquared() >= 0.0001;

		if (this.isGrumpy()) {
			this.currentBodyPitchProgress = Math.min(1.0F, this.currentBodyPitchProgress + 0.05F);
		} else {
			this.currentBodyPitchProgress = Math.max(0.0F, this.currentBodyPitchProgress - 0.1F);
		}
	}

	public int getTicksUntilCanFindDarkSpot() {
		return this.dataTracker.get(TICKS_UNTIL_CAN_FIND_DARK_SPOT);
	}

	public void setTicksUntilCanFindDarkSpot(int ticksUntilCanFindDarkSpot) {
		this.dataTracker.set(TICKS_UNTIL_CAN_FIND_DARK_SPOT, ticksUntilCanFindDarkSpot);
	}

	@Override
	public Vec3d getLeashOffset() {
		return new Vec3d(0.0D, this.getStandingEyeHeight() * 0.6D, 0.0D);
	}

	@Override
	protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
		return 1.1F;
	}

	@Override
	public float getMovementSpeed() {
		return MOVEMENT_SPEED;
	}

	public void setGrumpy(boolean grumpy) {
		this.setGlareFlag(GRUMPY_BITMASK, grumpy);
	}

	public boolean isGrumpy() {
		return this.hasGlareFlag(GRUMPY_BITMASK);
	}

	public boolean isMoving() {
		return !this.isOnGround() && this.getVelocity().lengthSquared() >= 0.0001;
	}
}
