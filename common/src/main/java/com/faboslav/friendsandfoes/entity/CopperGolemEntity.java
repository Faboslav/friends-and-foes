package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.animation.AnimationContextTracker;
import com.faboslav.friendsandfoes.entity.ai.goal.*;
import com.faboslav.friendsandfoes.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.util.ModelAnimationHelper;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.function.Predicate;

public final class CopperGolemEntity extends GolemEntity implements AnimatedEntity
{
	private static final float MOVEMENT_SPEED = 0.35F;
	private static final int COPPER_INGOT_HEAL_AMOUNT = 5;
	private static final float OXIDATION_CHANCE = 0.00002F;
	public static final int MIN_TICKS_UNTIL_CAN_PRESS_BUTTON = 200;
	public static final int MAX_TICKS_UNTIL_CAN_PRESS_BUTTON = 1200;
	public static final int MIN_TICKS_UNTIL_NEXT_HEAD_SPIN = 150;
	public static final int MAX_TICKS_UNTIL_NEXT_HEAD_SPIN = 300;

	private static final String OXIDATION_LEVEL_NBT_NAME = "OxidationLevel";
	private static final String IS_WAXED_NBT_NAME = "IsWaxed";
	private static final String BUTTON_PRESS_ANIMATION_PROGRESS_NBT_NAME = "ButtonPressAnimationProgress";
	private static final String LAST_BUTTON_PRESS_ANIMATION_PROGRESS_NBT_NAME = "LastButtonPressAnimationProgress";
	private static final String HEAD_SPIN_ANIMATION_PROGRESS_NBT_NAME = "HeadSpinAnimationProgress";
	private static final String LAST_HEAD_SPIN_ANIMATION_PROGRESS_NBT_NAME = "LastHeadSpinAnimationProgress";
	private static final String ENTITY_SNAPSHOT_NBT_NAME = "EntitySnapshot";

	private static final TrackedData<Integer> OXIDATION_LEVEL;
	private static final TrackedData<Integer> OXIDATION_TICKS;
	private static final TrackedData<Boolean> IS_WAXED;
	private static final TrackedData<Boolean> IS_PRESSING_BUTTON;
	private static final TrackedData<Boolean> IS_SPINNING_HEAD;
	private static final TrackedData<Boolean> IS_MOVING;
	private static final TrackedData<Integer> TICKS_UNTIL_CAN_PRESS_BUTTON;
	private static final TrackedData<Integer> TICKS_UNTIL_NEXT_HEAD_SPIN;
	private static final TrackedData<Float> BUTTON_PRESS_ANIMATION_PROGRESS;
	private static final TrackedData<Float> LAST_BUTTON_PRESS_ANIMATION_PROGRESS;
	private static final TrackedData<Float> HEAD_SPIN_ANIMATION_PROGRESS;
	private static final TrackedData<Float> LAST_HEAD_SPIN_ANIMATION_PROGRESS;
	private static final TrackedData<NbtCompound> ENTITY_SNAPSHOT;
	private static final Predicate<Entity> NOTICEABLE_PLAYER_FILTER;

	public CopperGolemPressButtonGoal pressButtonGoal;

	@Environment(EnvType.CLIENT)
	private AnimationContextTracker animationTickTracker;

	static {
		OXIDATION_LEVEL = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
		OXIDATION_TICKS = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
		IS_WAXED = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		IS_PRESSING_BUTTON = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		TICKS_UNTIL_CAN_PRESS_BUTTON = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
		IS_SPINNING_HEAD = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		IS_MOVING = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		TICKS_UNTIL_NEXT_HEAD_SPIN = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
		BUTTON_PRESS_ANIMATION_PROGRESS = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.FLOAT);
		LAST_BUTTON_PRESS_ANIMATION_PROGRESS = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.FLOAT);
		HEAD_SPIN_ANIMATION_PROGRESS = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.FLOAT);
		LAST_HEAD_SPIN_ANIMATION_PROGRESS = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.FLOAT);
		ENTITY_SNAPSHOT = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
		NOTICEABLE_PLAYER_FILTER = (entity) -> {
			PlayerEntity player = (PlayerEntity) entity;
			ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);
			Item itemInHand = itemStack.getItem();

			return itemInHand instanceof AxeItem && EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(player);
		};
	}

	public CopperGolemEntity(
		EntityType<? extends CopperGolemEntity> entityType,
		World world
	) {
		super(entityType, world);
		this.stepHeight = 0.3F;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(1, new CopperGolemFleeEntityGoal(this, PlayerEntity.class, 16.0F, this.getMovementSpeed(), this.getMovementSpeed(), (entity) -> {
			return NOTICEABLE_PLAYER_FILTER.test((PlayerEntity) entity) && this.isWaxed();
		}));
		this.pressButtonGoal = new CopperGolemPressButtonGoal(this);
		this.goalSelector.add(2, this.pressButtonGoal);
		this.goalSelector.add(3, new CopperGolemTemptGoal(this, Ingredient.ofItems(Items.HONEYCOMB)));
		this.goalSelector.add(4, new CopperGolemSpinHeadGoal(this));
		this.goalSelector.add(6, new CopperGolemWanderAroundGoal(this));
		this.goalSelector.add(7, new CopperGolemLookAtEntityGoal(this, CopperGolemEntity.class, 6.0F));
		this.goalSelector.add(7, new CopperGolemLookAtEntityGoal(this, IronGolemEntity.class, 6.0F));
		this.goalSelector.add(8, new CopperGolemLookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(10, new LookAroundGoal(this));
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(OXIDATION_LEVEL, Oxidizable.OxidationLevel.UNAFFECTED.ordinal());
		this.dataTracker.startTracking(OXIDATION_TICKS, 0);
		this.dataTracker.startTracking(IS_WAXED, false);
		this.dataTracker.startTracking(IS_PRESSING_BUTTON, false);
		this.dataTracker.startTracking(TICKS_UNTIL_CAN_PRESS_BUTTON, RandomGenerator.generateInt(MIN_TICKS_UNTIL_CAN_PRESS_BUTTON, MAX_TICKS_UNTIL_CAN_PRESS_BUTTON));
		this.dataTracker.startTracking(IS_SPINNING_HEAD, false);
		this.dataTracker.startTracking(IS_MOVING, false);
		this.dataTracker.startTracking(TICKS_UNTIL_NEXT_HEAD_SPIN, RandomGenerator.generateInt(MIN_TICKS_UNTIL_NEXT_HEAD_SPIN, MAX_TICKS_UNTIL_NEXT_HEAD_SPIN));
		this.dataTracker.startTracking(BUTTON_PRESS_ANIMATION_PROGRESS, 0.0F);
		this.dataTracker.startTracking(LAST_BUTTON_PRESS_ANIMATION_PROGRESS, 0.0F);
		this.dataTracker.startTracking(HEAD_SPIN_ANIMATION_PROGRESS, 0.0F);
		this.dataTracker.startTracking(LAST_HEAD_SPIN_ANIMATION_PROGRESS, 0.0F);
		this.dataTracker.startTracking(ENTITY_SNAPSHOT, new NbtCompound());
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt(OXIDATION_LEVEL_NBT_NAME, this.getOxidationLevel().ordinal());
		nbt.putFloat(BUTTON_PRESS_ANIMATION_PROGRESS_NBT_NAME, this.getButtonPressAnimationProgress());
		nbt.putFloat(LAST_BUTTON_PRESS_ANIMATION_PROGRESS_NBT_NAME, this.getLastButtonPressAnimationProgress());
		nbt.putFloat(HEAD_SPIN_ANIMATION_PROGRESS_NBT_NAME, this.getHeadSpinAnimationProgress());
		nbt.putFloat(LAST_HEAD_SPIN_ANIMATION_PROGRESS_NBT_NAME, this.getLastHeadSpinAnimationProgress());
		nbt.putBoolean(IS_WAXED_NBT_NAME, this.isWaxed());
		nbt.put(ENTITY_SNAPSHOT_NBT_NAME, this.getEntitySnapshot());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setOxidationLevel(Oxidizable.OxidationLevel.values()[nbt.getInt(OXIDATION_LEVEL_NBT_NAME)]);
		this.setIsWaxed(nbt.getBoolean(IS_WAXED_NBT_NAME));
		this.setButtonPressAnimationProgress(nbt.getFloat(BUTTON_PRESS_ANIMATION_PROGRESS_NBT_NAME));
		this.setLastButtonPressAnimationProgress(nbt.getFloat(LAST_BUTTON_PRESS_ANIMATION_PROGRESS_NBT_NAME));
		this.setHeadSpinAnimationProgress(nbt.getFloat(HEAD_SPIN_ANIMATION_PROGRESS_NBT_NAME));
		this.setLastHeadSpinAnimationProgress(nbt.getFloat(LAST_HEAD_SPIN_ANIMATION_PROGRESS_NBT_NAME));
		this.setEntitySnapshot(nbt.getCompound(ENTITY_SNAPSHOT_NBT_NAME));
		this.applyEntitySnapshot();
	}

	public void setEntitySnapshot(NbtCompound entitySnapshot) {
		dataTracker.set(ENTITY_SNAPSHOT, entitySnapshot);
	}

	public void applyEntitySnapshot() {
		NbtCompound entitySnapshot = this.getEntitySnapshot();

		if (entitySnapshot.isEmpty()) {
			return;
		}

		this.serverYaw = entitySnapshot.getDouble("serverYaw");
		this.prevYaw = entitySnapshot.getFloat("prevYaw");
		this.setYaw(this.prevYaw);
		this.prevPitch = entitySnapshot.getFloat("prevPitch");
		this.serverPitch = this.prevPitch;
		this.setPitch(this.prevPitch);
		this.roll = entitySnapshot.getInt("roll");
		this.prevBodyYaw = entitySnapshot.getFloat("prevBodyYaw");
		this.bodyYaw = this.prevBodyYaw;
		this.serverHeadYaw = entitySnapshot.getDouble("serverHeadYaw");
		this.prevHeadYaw = entitySnapshot.getFloat("prevHeadYaw");
		this.headYaw = this.prevHeadYaw;
		this.lastHandSwingProgress = entitySnapshot.getFloat("lastHandSwingProgress");
		this.handSwingProgress = this.lastHandSwingProgress;
		this.lastLimbDistance = entitySnapshot.getFloat("lastLimbDistance");
		this.limbDistance = this.lastLimbDistance;
		this.limbAngle = entitySnapshot.getFloat("limbAngle");
		this.prevLookDirection = entitySnapshot.getFloat("prevLookDirection");
		this.lookDirection = this.prevLookDirection;
		this.age = entitySnapshot.getInt("age");
		this.prevStepBobbingAmount = entitySnapshot.getFloat("prevStepBobbingAmount");
		this.stepBobbingAmount = this.prevStepBobbingAmount;
	}

	public NbtCompound getEntitySnapshot() {
		return this.dataTracker.get(ENTITY_SNAPSHOT);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED)
			.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected void pushAway(Entity entity) {
		if (this.isOxidized()) {
			return;
		}

		super.pushAway(entity);
	}


	@Override
	protected int getNextAirUnderwater(int air) {
		return air;
	}

	@Override
	public float getSoundPitch() {
		return (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 2.5F;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_DEATH.get();
	}

	@Override
	protected void playStepSound(
		BlockPos pos,
		BlockState state
	) {
		if (
			this.isOxidized()
			|| state.getMaterial().isLiquid()
		) {
			return;
		}

		BlockState blockState = this.getWorld().getBlockState(pos.up());
		BlockSoundGroup blockSoundGroup = blockState.isIn(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? blockState.getSoundGroup():state.getSoundGroup();
		this.playSound(FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_STEP.get(), blockSoundGroup.getVolume() * 0.15F, this.getSoundPitch());
	}

	@Override
	public boolean damage(
		DamageSource source,
		float amount
	) {
		if (source.getAttacker() instanceof LightningEntity || source == DamageSource.SWEET_BERRY_BUSH) {
			return false;
		}

		if (this.getWorld().isClient() == false) {
			if (this.pressButtonGoal.isRunning()) {
				this.pressButtonGoal.stop();
			}
		}

		boolean damageResult = super.damage(source, amount);

		if (this.isOxidized()) {
			NbtCompound entitySnapshot = this.getEntitySnapshot();
			this.lastLimbDistance = entitySnapshot.getFloat("lastLimbDistance");
			this.limbDistance = this.lastLimbDistance;
			this.limbAngle = entitySnapshot.getFloat("limbAngle");
		}

		return damageResult;
	}

	@Override
	public Vec3d getLeashOffset() {
		return new Vec3d(0.0D, this.getStandingEyeHeight() * 0.4D, 0.0D);
	}

	@Override
	protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
		return 0.75F;
	}

	@Override
	public float getMovementSpeed() {
		return MOVEMENT_SPEED - this.getOxidationLevel().ordinal() * 0.05F;
	}

	@Override
	public ActionResult interactMob(
		PlayerEntity player,
		Hand hand
	) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item itemInHand = itemStack.getItem();
		boolean interactionResult = false;

		if (itemInHand == Items.COPPER_INGOT) {
			interactionResult = this.tryToInteractMobWithCopperIngot(player, itemStack);
		} else if (itemInHand == Items.HONEYCOMB) {
			interactionResult = this.tryToInteractMobWithHoneycomb(player, itemStack);
		} else if (itemInHand instanceof AxeItem) {
			interactionResult = this.tryToInteractMobWithAxe(player, hand, itemStack);
		}

		if (interactionResult) {
			this.emitGameEvent(GameEvent.ENTITY_INTERACT, this);
			return ActionResult.success(this.getWorld().isClient());
		}

		return super.interactMob(player, hand);
	}

	private boolean tryToInteractMobWithCopperIngot(
		PlayerEntity player,
		ItemStack itemStack
	) {
		if (this.getHealth() == this.getMaxHealth()) {
			return false;
		}

		this.heal(COPPER_INGOT_HEAL_AMOUNT);

		if (!player.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}

		this.playSound(FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_REPAIR.get(), 1.0F, this.getSoundPitch() - 1.0F);

		return true;
	}

	private boolean tryToInteractMobWithHoneycomb(
		PlayerEntity player,
		ItemStack itemStack
	) {
		if (this.isWaxed() || this.isOxidized()) {
			return false;
		}

		this.setIsWaxed(true);

		if (!player.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}

		this.playSound(SoundEvents.ITEM_HONEYCOMB_WAX_ON, 1.0F, 1.0F);
		this.spawnParticles(ParticleTypes.WAX_ON, 7);

		return true;
	}

	private boolean tryToInteractMobWithAxe(
		PlayerEntity player,
		Hand hand,
		ItemStack itemStack
	) {
		if (!this.isWaxed() && !this.isDegraded()) {
			return false;
		}

		if (this.isWaxed()) {
			this.setIsWaxed(false);

			this.playSound(SoundEvents.ITEM_AXE_WAX_OFF, 1.0F, 1.0F);
			this.spawnParticles(ParticleTypes.WAX_OFF, 7);

		} else if (isDegraded()) {
			if (!this.getEntityWorld().isClient()) {
				int increasedOxidationLevelOrdinal = getOxidationLevel().ordinal() - 1;
				Oxidizable.OxidationLevel[] OxidationLevels = Oxidizable.OxidationLevel.values();
				this.setOxidationLevel(OxidationLevels[increasedOxidationLevelOrdinal]);
			}

			this.playSound(SoundEvents.ITEM_AXE_SCRAPE, 1.0F, 1.0F);
			this.spawnParticles(ParticleTypes.SCRAPE, 7);
		}

		if (this.getWorld().isClient() == false && !player.getAbilities().creativeMode) {
			itemStack.damage(1, player, (playerEntity) -> {
				player.sendToolBreakStatus(hand);
			});
		}

		return true;
	}

	@Override
	public void tick() {
		if (FriendsAndFoes.getConfig().enableCopperGolem == false) {
			this.discard();
		}

		super.tick();

		if (this.isOxidized()) {
			this.applyEntitySnapshot();
			return;
		}

		if (this.getTicksUntilCanPressButton() > 0) {
			this.setTicksUntilCanPressButton(this.getTicksUntilCanPressButton() - 1);
		}

		if (this.getTicksUntilNextHeadSpin() > 0) {
			this.setTicksUntilNextHeadSpin(this.getTicksUntilNextHeadSpin() - 1);
		}

		this.updateButtonPressAnimation();
		this.updateHeadSpinAnimation();

		if (this.getWorld().isClient() == false) {
			this.setIsMoving(this.getNavigation().isFollowingPath());
		}

		if (!this.isWaxed()) {
			this.handleOxidationIncrease();
		}
	}

	@Override
	public void travel(Vec3d movementInput) {
		if (this.isOxidized() == false) {
			super.travel(movementInput);
			return;
		}

		if (this.getEntityWorld().isClient()) {
			return;
		}

		this.applyGravityToTravel(movementInput);
	}

	private void applyGravityToTravel(Vec3d movementInput) {
		double d = 0.08;
		boolean bl = this.getVelocity().y <= 0.0;
		if (bl && this.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
			d = 0.01;
			this.onLanding();
		}

		if (this.isFallFlying() == false) {
			BlockPos blockPos = this.getVelocityAffectingPos();
			float p = this.getWorld().getBlockState(blockPos).getBlock().getSlipperiness();
			float f = this.isOnGround() ? p * 0.91F:0.91F;
			Vec3d vec3d6 = this.applyMovementInput(movementInput, p);
			double q = vec3d6.y;
			if (this.hasStatusEffect(StatusEffects.LEVITATION)) {
				q += (0.05 * (double) (this.getStatusEffect(StatusEffects.LEVITATION).getAmplifier() + 1) - vec3d6.y) * 0.2;
				this.onLanding();
			} else if (this.getWorld().isClient() && this.getWorld().isChunkLoaded(blockPos) == false) {
				if (this.getY() > (double) this.getWorld().getBottomY()) {
					q = -0.1;
				} else {
					q = 0.0;
				}
			} else if (!this.hasNoGravity()) {
				q -= d;
			}

			if (this.hasNoDrag()) {
				this.setVelocity(vec3d6.x, q, vec3d6.z);
			} else {
				this.setVelocity(vec3d6.x * (double) f, q * 0.9800000190734863, vec3d6.z * (double) f);
			}
		}
	}

	@Override
	public void tickRiding() {
		super.tickRiding();

		if (this.isOxidized()) {
			this.applyEntitySnapshot();
		}
	}

	@Override
	public void onStruckByLightning(
		ServerWorld serverWorld,
		LightningEntity lightning
	) {
		super.onStruckByLightning(serverWorld, lightning);

		this.setOnFire(false);
		this.setHealth(this.getMaxHealth());

		if (this.isDegraded()) {
			this.spawnParticles(ParticleTypes.WAX_OFF, 7);
		}

		if (this.isWaxed() == false && this.getEntityWorld().isClient() == false) {
			this.setOxidationLevel(Oxidizable.OxidationLevel.UNAFFECTED);
		}
	}

	private void updateButtonPressAnimation() {
		this.setLastButtonPressAnimationProgress(this.getButtonPressAnimationProgress());

		if (this.isPressingButton()) {
			this.setButtonPressAnimationProgress(Math.min(1.0F, this.getButtonPressAnimationProgress() + 0.20F));
		} else {
			this.setButtonPressAnimationProgress(Math.max(0.0F, this.getButtonPressAnimationProgress() - 0.20F));
		}
	}

	private void updateHeadSpinAnimation() {
		this.setLastHeadSpinAnimationProgress(this.getHeadSpinAnimationProgress());

		if (this.isSpinningHead()) {
			this.setHeadSpinAnimationProgress(Math.min(1.0F, this.getHeadSpinAnimationProgress() + 0.075F));
		} else {
			this.setHeadSpinAnimationProgress(0);
		}
	}

	public void handleOxidationIncrease() {
		if (this.getEntityWorld().isClient() || this.isOxidized() || this.isWaxed()) {
			return;
		}

		if (RandomGenerator.generateRandomFloat() < OXIDATION_CHANCE) {
			int degradedOxidationLevelOrdinal = getOxidationLevel().ordinal() + 1;
			Oxidizable.OxidationLevel[] OxidationLevels = Oxidizable.OxidationLevel.values();
			this.setOxidationLevel(OxidationLevels[degradedOxidationLevelOrdinal]);
		}
	}

	public boolean isOxidized() {
		return this.getOxidationLevel() == Oxidizable.OxidationLevel.OXIDIZED;
	}

	public boolean isDegraded() {
		return this.getOxidationLevel().ordinal() > Oxidizable.OxidationLevel.UNAFFECTED.ordinal();
	}

	public Oxidizable.OxidationLevel getOxidationLevel() {
		return Oxidizable.OxidationLevel.values()[this.dataTracker.get(OXIDATION_LEVEL)];
	}

	public void setOxidationLevel(Oxidizable.OxidationLevel OxidationLevel) {
		this.dataTracker.set(OXIDATION_LEVEL, OxidationLevel.ordinal());

		if (this.isOxidized() && this.isAiDisabled() == false) {
			this.becomeStatue();
		} else if (this.isOxidized() == false && this.isAiDisabled()) {
			this.becomeEntity();
		}
	}

	private void becomeStatue() {
		NbtCompound entitySnapshot = this.takeEntitySnapshot();
		this.setEntitySnapshot(entitySnapshot);

		this.setAiDisabled(true);

		for (PrioritizedGoal goal : this.goalSelector.getRunningGoals().toList()) {
			goal.stop();
		}

		this.getNavigation().setSpeed(0);
		this.getNavigation().stop();
		this.getMoveControl().moveTo(this.getX(), this.getY(), this.getZ(), 0);
		this.getMoveControl().tick();
		this.getLookControl().lookAt(this.getLookControl().getLookX(), this.getLookControl().getLookY(), this.getLookControl().getLookZ());
		this.getLookControl().tick();

		this.intersectionChecked = false;
		this.jumping = false;
		this.setMovementSpeed(0.0F);
		this.prevHorizontalSpeed = 0.0F;
		this.horizontalSpeed = 0.0F;
		this.sidewaysSpeed = 0.0F;
		this.upwardSpeed = 0.0F;
		this.setVelocity(Vec3d.ZERO);
		this.velocityDirty = true;
	}

	private void becomeEntity() {
		this.setAiDisabled(false);
		this.intersectionChecked = true;
	}

	private NbtCompound takeEntitySnapshot() {
		NbtCompound entitySnapshot = new NbtCompound();

		entitySnapshot.putDouble("serverYaw", this.serverYaw);
		entitySnapshot.putFloat("prevYaw", this.prevYaw); // Same as serverYaw and yaw
		entitySnapshot.putDouble("serverPitch", this.serverPitch);
		entitySnapshot.putFloat("prevPitch", this.prevPitch); // Same as pitch
		entitySnapshot.putInt("roll", this.getRoll());
		entitySnapshot.putFloat("prevBodyYaw", this.prevBodyYaw); // Same as bodyYaw
		entitySnapshot.putDouble("serverHeadYaw", this.serverHeadYaw);
		entitySnapshot.putFloat("prevHeadYaw", this.prevHeadYaw); // Same as headYaw
		entitySnapshot.putFloat("lastHandSwingProgress", this.lastHandSwingProgress); // Same as handSwingProgress
		entitySnapshot.putFloat("lastLimbDistance", this.lastLimbDistance); // Same as limbDistance
		entitySnapshot.putFloat("limbAngle", this.limbAngle);
		entitySnapshot.putFloat("prevLookDirection", this.prevLookDirection); // Same as lookDirection
		entitySnapshot.putInt("age", this.age);
		entitySnapshot.putFloat("tickDelta", ModelAnimationHelper.getTickDelta());
		entitySnapshot.putFloat("prevStepBobbingAmount", this.prevStepBobbingAmount); // Same as stepBobbingAmount

		return entitySnapshot;
	}

	public boolean isWaxed() {
		return this.dataTracker.get(IS_WAXED);
	}

	public void setIsWaxed(boolean isWaxed) {
		this.dataTracker.set(IS_WAXED, isWaxed);
	}

	public boolean isPressingButton() {
		return this.dataTracker.get(IS_PRESSING_BUTTON);
	}

	public void setIsPressingButton(boolean isPressingButton) {
		this.dataTracker.set(IS_PRESSING_BUTTON, isPressingButton);
	}

	public int getTicksUntilCanPressButton() {
		return this.dataTracker.get(TICKS_UNTIL_CAN_PRESS_BUTTON);
	}

	public void setTicksUntilCanPressButton(int ticksUntilCanPressButton) {
		this.dataTracker.set(TICKS_UNTIL_CAN_PRESS_BUTTON, ticksUntilCanPressButton);
	}

	public boolean isSpinningHead() {
		return this.dataTracker.get(IS_SPINNING_HEAD);
	}

	public void setIsSpinningHead(boolean isSpinningHead) {
		this.dataTracker.set(IS_SPINNING_HEAD, isSpinningHead);
	}

	public boolean isMoving() {
		return this.dataTracker.get(IS_MOVING);
	}

	public void setIsMoving(boolean isMoving) {
		this.dataTracker.set(IS_MOVING, isMoving);
	}

	public int getTicksUntilNextHeadSpin() {
		return this.dataTracker.get(TICKS_UNTIL_NEXT_HEAD_SPIN);
	}

	public void setTicksUntilNextHeadSpin(int ticksUntilNextHeadSpin) {
		this.dataTracker.set(TICKS_UNTIL_NEXT_HEAD_SPIN, ticksUntilNextHeadSpin);
	}

	public float getButtonPressAnimationProgress() {
		return this.dataTracker.get(BUTTON_PRESS_ANIMATION_PROGRESS);
	}

	public void setButtonPressAnimationProgress(float buttonPressAnimationProgress) {
		this.dataTracker.set(BUTTON_PRESS_ANIMATION_PROGRESS, buttonPressAnimationProgress);
	}

	public float getLastButtonPressAnimationProgress() {
		return this.dataTracker.get(LAST_BUTTON_PRESS_ANIMATION_PROGRESS);
	}

	public void setLastButtonPressAnimationProgress(float lastButtonPressAnimationProgress) {
		this.dataTracker.set(LAST_BUTTON_PRESS_ANIMATION_PROGRESS, lastButtonPressAnimationProgress);
	}

	public float getHeadSpinAnimationProgress() {
		return this.dataTracker.get(HEAD_SPIN_ANIMATION_PROGRESS);
	}

	public void setHeadSpinAnimationProgress(float headSpinAnimationProgress) {
		this.dataTracker.set(HEAD_SPIN_ANIMATION_PROGRESS, headSpinAnimationProgress);
	}

	public float getLastHeadSpinAnimationProgress() {
		return this.dataTracker.get(LAST_HEAD_SPIN_ANIMATION_PROGRESS);
	}

	public void setLastHeadSpinAnimationProgress(float lastHeadSpinAnimationProgress) {
		this.dataTracker.set(LAST_HEAD_SPIN_ANIMATION_PROGRESS, lastHeadSpinAnimationProgress);
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

	public void setSpawnYaw(float yaw) {
		this.serverYaw = yaw;
		this.prevYaw = yaw;
		this.setYaw(yaw);
		this.prevBodyYaw = yaw;
		this.bodyYaw = yaw;
		this.serverHeadYaw = yaw;
		this.prevHeadYaw = yaw;
		this.headYaw = yaw;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public AnimationContextTracker getAnimationContextTracker() {
		if (this.animationTickTracker == null) {
			this.animationTickTracker = new AnimationContextTracker();
		}

		return this.animationTickTracker;
	}
}