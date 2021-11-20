package com.faboslav.friendsandfoes.entity.passive;

import com.faboslav.friendsandfoes.entity.passive.ai.goal.*;
import com.faboslav.friendsandfoes.mixin.EntityNavigationAccessor;
import com.faboslav.friendsandfoes.registry.SoundRegistry;
import com.faboslav.friendsandfoes.util.ModelAnimationHelper;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.GoToVillageGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Iterator;
import java.util.function.Predicate;

public class CopperGolemEntity extends GolemEntity
{
    private static final int MAX_HEALTH = 20;
    private static final float DEFAULT_MOVEMENT_SPEED = 0.45F;
    private static final int COPPER_INGOT_HEAL_AMOUNT = 5;
    public static final int MIN_TICKS_UNTIL_NEXT_HEAD_SPIN = 200;
    public static final int MAX_TICKS_UNTIL_NEXT_HEAD_SPIN = 400;

    private static final String OXIDATION_LEVEL_NBT_NAME = "OxidationLevel";
    private static final String IS_WAXED_NBT_NAME = "IsWaxed";
    private static final String BUTTON_PRESS_ANIMATION_PROGRESS_NBT_NAME = "ButtonPressAnimationProgress";
    private static final String LAST_BUTTON_PRESS_ANIMATION_PROGRESS_NBT_NAME = "LastButtonPressAnimationProgress";
    private static final String HEAD_SPIN_ANIMATION_PROGRESS_NBT_NAME = "HeadSpinAnimationProgress";
    private static final String MODEL_ANGLES_NBT_NAME = "ModelAngles";
    private static final TrackedData<Integer> OXIDATION_LEVEL;
    private static final TrackedData<Integer> OXIDATION_TICKS;
    private static final TrackedData<Boolean> IS_WAXED;
    private static final TrackedData<Boolean> IS_PRESSING_BUTTON;
    private static final TrackedData<Boolean> IS_SPINNING_HEAD;
    private static final TrackedData<Integer> TICKS_UNTIL_CAN_PRESS_BUTTON;
    private static final TrackedData<Integer> TICKS_UNTIL_NEXT_HEAD_SPIN;
    private static final TrackedData<Float> BUTTON_PRESS_ANIMATION_PROGRESS;
    private static final TrackedData<Float> LAST_BUTTON_PRESS_ANIMATION_PROGRESS;
    private static final TrackedData<Float> HEAD_SPIN_ANIMATION_PROGRESS;
    private static final TrackedData<Float> CUSTOM_MOVEMENT_SPEED;
    private static final TrackedData<NbtCompound> MODEL_ANGLES;
    private static final Predicate<Entity> NOTICEABLE_PLAYER_FILTER;

    private CopperGolemPressButtonGoal pressButtonGoal;

    static {
        OXIDATION_LEVEL = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
        OXIDATION_TICKS = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
        IS_WAXED = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        IS_PRESSING_BUTTON = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        TICKS_UNTIL_CAN_PRESS_BUTTON = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
        IS_SPINNING_HEAD = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        TICKS_UNTIL_NEXT_HEAD_SPIN = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
        BUTTON_PRESS_ANIMATION_PROGRESS = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.FLOAT);
        LAST_BUTTON_PRESS_ANIMATION_PROGRESS = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.FLOAT);
        HEAD_SPIN_ANIMATION_PROGRESS = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.FLOAT);
        CUSTOM_MOVEMENT_SPEED = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.FLOAT);
        MODEL_ANGLES = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
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
        EntityNavigationAccessor entityNavigation = (EntityNavigationAccessor) this.getNavigation();
        entityNavigation.setNodeReachProximity(0.1F);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new CopperGolemFleeEntityGoal(this, PlayerEntity.class, 16.0F, this.getCustomMovementSpeed(), this.getCustomMovementSpeed(), (entity) -> {
            return NOTICEABLE_PLAYER_FILTER.test((PlayerEntity) entity) && this.isWaxed();
        }));
        this.pressButtonGoal = new CopperGolemPressButtonGoal(this);
        this.goalSelector.add(2, this.pressButtonGoal);
        this.goalSelector.add(3, new CopperGolemTemptGoal(this, Ingredient.ofItems(Items.HONEYCOMB)));
        this.goalSelector.add(4, new CopperGolemSpinHeadGoal(this));
        this.goalSelector.add(6, new CopperGolemWanderAroundGoal(this));
        this.goalSelector.add(7, new CopperGolemLookAtEntityGoal(this, CopperGolemEntity.class, 6.0F)); //only oxidized
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
        this.dataTracker.startTracking(TICKS_UNTIL_CAN_PRESS_BUTTON, RandomGenerator.generateInt(MIN_TICKS_UNTIL_NEXT_HEAD_SPIN, MAX_TICKS_UNTIL_NEXT_HEAD_SPIN));
        this.dataTracker.startTracking(IS_SPINNING_HEAD, false);
        this.dataTracker.startTracking(TICKS_UNTIL_NEXT_HEAD_SPIN, RandomGenerator.generateInt(MIN_TICKS_UNTIL_NEXT_HEAD_SPIN, MAX_TICKS_UNTIL_NEXT_HEAD_SPIN));
        this.dataTracker.startTracking(BUTTON_PRESS_ANIMATION_PROGRESS, 0.0F);
        this.dataTracker.startTracking(LAST_BUTTON_PRESS_ANIMATION_PROGRESS, 0.0F);
        this.dataTracker.startTracking(HEAD_SPIN_ANIMATION_PROGRESS, 0.0F);
        this.dataTracker.startTracking(CUSTOM_MOVEMENT_SPEED, DEFAULT_MOVEMENT_SPEED);
        this.dataTracker.startTracking(MODEL_ANGLES, new NbtCompound());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt(OXIDATION_LEVEL_NBT_NAME, this.getOxidationLevel().ordinal());
        nbt.putFloat(BUTTON_PRESS_ANIMATION_PROGRESS_NBT_NAME, this.getButtonPressAnimationProgress());
        nbt.putFloat(LAST_BUTTON_PRESS_ANIMATION_PROGRESS_NBT_NAME, this.getLastButtonPressAnimationProgress());
        nbt.putFloat(HEAD_SPIN_ANIMATION_PROGRESS_NBT_NAME, this.getHeadSpinAnimationProgress());
        nbt.putBoolean(IS_WAXED_NBT_NAME, this.isWaxed());
        nbt.put(MODEL_ANGLES_NBT_NAME, this.getModelAngles());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setOxidationLevel(Oxidizable.OxidationLevel.values()[nbt.getInt(OXIDATION_LEVEL_NBT_NAME)]);
        this.setIsWaxed(nbt.getBoolean(IS_WAXED_NBT_NAME));
        this.setButtonPressAnimationProgress(nbt.getFloat(BUTTON_PRESS_ANIMATION_PROGRESS_NBT_NAME));
        this.setLastButtonPressAnimationProgress(nbt.getFloat(LAST_BUTTON_PRESS_ANIMATION_PROGRESS_NBT_NAME));
        this.setHeadSpinAnimationProgress(nbt.getFloat(HEAD_SPIN_ANIMATION_PROGRESS_NBT_NAME));
        this.setModelAngles(nbt.getCompound(MODEL_ANGLES_NBT_NAME));
        this.applyModelAngles();
    }

    public void setModelAngles(NbtCompound modelAngles) {
        dataTracker.set(
                MODEL_ANGLES,
                modelAngles
        );
    }

    public void applyModelAngles() {
        NbtCompound modelAngles = this.getModelAngles();

        if (modelAngles.isEmpty()) {
            return;
        }

        // Rotations
        this.serverYaw = modelAngles.getDouble("serverYaw");
        this.prevYaw = modelAngles.getFloat("prevYaw");
        this.setYaw(this.prevYaw);
        this.prevPitch = modelAngles.getFloat("prevPitch");
        this.serverPitch = this.prevPitch;
        this.setPitch(this.prevPitch);
        this.roll = modelAngles.getInt("roll");
        this.prevBodyYaw = modelAngles.getFloat("prevBodyYaw");
        this.bodyYaw = this.prevBodyYaw;
        this.serverHeadYaw = modelAngles.getDouble("serverHeadYaw");
        this.prevHeadYaw = modelAngles.getFloat("prevHeadYaw");
        this.headYaw = this.prevHeadYaw;

        // Limbs
        this.lastHandSwingProgress = modelAngles.getFloat("lastHandSwingProgress");
        this.handSwingProgress = this.lastHandSwingProgress;
        this.lastLimbDistance = modelAngles.getFloat("lastLimbDistance");
        this.limbDistance = this.lastLimbDistance;
        this.limbAngle = modelAngles.getFloat("limbAngle");

        // Look direction
        this.prevLookDirection = modelAngles.getFloat("prevLookDirection");
        this.lookDirection = this.prevLookDirection;

        // Age
        this.age = modelAngles.getInt("age");

        // Other
        this.prevStepBobbingAmount = modelAngles.getFloat("prevStepBobbingAmount");
        this.stepBobbingAmount = this.prevStepBobbingAmount;
    }

    public NbtCompound getModelAngles() {
        return this.dataTracker.get(MODEL_ANGLES);
    }

    public static DefaultAttributeContainer.Builder createCopperGolemAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
    }

    protected int getNextAirUnderwater(int air) {
        return air;
    }

    public float getSoundPitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 2.5F;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundRegistry.ENTITY_COPPER_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.ENTITY_COPPER_GOLEM_DEATH;
    }

    @Override
    protected void playStepSound(
            BlockPos pos,
            BlockState state
    ) {
        this.playSound(SoundEvents.ENTITY_IRON_GOLEM_STEP, 1.0F, this.getSoundPitch());
    }

    @Override
    public boolean damage(
            DamageSource source,
            float amount
    ) {
        if (
                source.getAttacker() instanceof LightningEntity ||
                source == DamageSource.SWEET_BERRY_BUSH
        ) {
            return false;
        }

        if (!this.world.isClient) {
            this.pressButtonGoal.stop();
        }

        boolean damageResult = super.damage(source, amount);

        if (this.isOxidized()) {
            NbtCompound modelAngles = new NbtCompound();
            this.lastLimbDistance = modelAngles.getFloat("lastLimbDistance");
            this.limbDistance = this.lastLimbDistance;
            this.limbAngle = modelAngles.getFloat("limbAngle");
        }

        return damageResult;
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0D, this.getStandingEyeHeight() * 0.4D, 0.0D);
    }

    public ActionResult interactMob(
            PlayerEntity player,
            Hand hand
    ) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item itemInHand = itemStack.getItem();
        boolean successfulInteraction = false;

        if (itemInHand == Items.COPPER_INGOT && this.getHealth() < MAX_HEALTH) {
            this.heal(COPPER_INGOT_HEAL_AMOUNT);

            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            this.playSound(SoundRegistry.ENTITY_COPPER_GOLEM_REPAIR, 1.0F, this.getSoundPitch());

            successfulInteraction = true;
        } else if (itemInHand == Items.HONEYCOMB && !this.isWaxed()) {
            this.setIsWaxed(true);

            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            this.world.playSoundFromEntity(null, this, SoundEvents.ITEM_HONEYCOMB_WAX_ON, SoundCategory.BLOCKS, 1.0F, 1.0F);
            this.spawnParticles(ParticleTypes.WAX_ON, 7);

            successfulInteraction = true;
        } else if (itemInHand instanceof AxeItem) {
            boolean wasAxeUsed = false;

            if (this.isWaxed()) {
                this.setIsWaxed(false);

                this.world.playSoundFromEntity(player, this, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
                this.spawnParticles(ParticleTypes.WAX_OFF, 7);

                wasAxeUsed = true;
            } else if (isDegraded()) {
                int increasedOxidationLevelOrdinal = getOxidationLevel().ordinal() - 1;
                Oxidizable.OxidationLevel[] OxidationLevels = Oxidizable.OxidationLevel.values();
                this.setOxidationLevel(OxidationLevels[increasedOxidationLevelOrdinal]);

                this.world.playSoundFromEntity(player, this, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                this.spawnParticles(ParticleTypes.SCRAPE, 7);

                wasAxeUsed = true;
            }

            if (wasAxeUsed) {
                if (!this.world.isClient && !player.getAbilities().creativeMode) {
                    itemStack.damage(1, player, (playerEntity) -> {
                        player.sendToolBreakStatus(hand);
                    });
                }

                successfulInteraction = true;
            }
        }

        if (successfulInteraction) {
            this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
            return ActionResult.success(this.world.isClient);
        }

        return super.interactMob(player, hand);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isOxidized()) {
            this.applyModelAngles();
            return;
        }

        if (!this.isWaxed()) {
            this.handleOxidationIncrease();
        }

        if (this.getTicksUntilCanPressButton() > 0) {
            this.setTicksUntilCanPressButton(this.getTicksUntilCanPressButton() - 1);
        }

        if (this.getTicksUntilNextHeadSpin() > 0) {
            this.setTicksUntilNextHeadSpin(this.getTicksUntilNextHeadSpin() - 1);
        }

        this.updateButtonPressAnimation();
        this.updateHeadSpinAnimation();

        if (!this.isOxidized()) {
            NbtCompound modelAngles = new NbtCompound();

            // Rotations
            modelAngles.putDouble("serverYaw", this.serverYaw);
            modelAngles.putFloat("prevYaw", this.prevYaw); // Same as yaw and yaw
            modelAngles.putDouble("serverPitch", this.serverPitch);
            modelAngles.putFloat("prevPitch", this.prevPitch); // Same as pitch
            modelAngles.putInt("roll", this.getRoll());
            modelAngles.putFloat("prevBodyYaw", this.prevBodyYaw); // Same as bodyYaw
            modelAngles.putDouble("serverHeadYaw", this.serverHeadYaw);
            modelAngles.putFloat("prevHeadYaw", this.prevHeadYaw); // Same as headYaw

            // Limbs
            modelAngles.putFloat("lastHandSwingProgress", this.lastHandSwingProgress); // Same as handSwingProgress
            modelAngles.putFloat("lastLimbDistance", this.lastLimbDistance); // Same as limbDistance
            modelAngles.putFloat("limbAngle", this.limbAngle);

            // Look direction
            modelAngles.putFloat("prevLookDirection", this.prevLookDirection); // Same as lookDirection

            // Age
            modelAngles.putInt("age", this.age);

            // TickDelta
            modelAngles.putFloat("tickDelta", ModelAnimationHelper.getTickDelta());

            // Other
            modelAngles.putFloat("prevStepBobbingAmount", this.prevStepBobbingAmount); // Same as stepBobbingAmount

            this.setModelAngles(modelAngles);
        }
    }

    @Override
    public void tickRiding() {
        super.tickRiding();

        if (this.isOxidized()) {
            this.applyModelAngles();
        }
    }

    private void updateButtonPressAnimation() {
        this.setLastButtonPressAnimationProgress(this.getButtonPressAnimationProgress());

        if (this.isPressingButton()) {
            this.setButtonPressAnimationProgress(Math.min(1.0F, this.getButtonPressAnimationProgress() + 0.25F));
        } else {
            this.setButtonPressAnimationProgress(Math.max(0.0F, this.getButtonPressAnimationProgress() - 0.25F));
        }
    }

    private void updateHeadSpinAnimation() {
        if (this.isSpinningHead()) {
            this.setHeadSpinAnimationProgress(Math.min(1.0F, this.getHeadSpinAnimationProgress() + 0.05F));
        } else {
            this.setHeadSpinAnimationProgress(0);
        }
    }

    public void handleOxidationIncrease() {
        if (this.isOxidized() || this.isWaxed()) {
            return;
        }

        if (this.random.nextFloat() < 0.00004166666) {
            int degradedOxidationLevelOrdinal = getOxidationLevel().ordinal() + 1;
            Oxidizable.OxidationLevel[] OxidationLevels = Oxidizable.OxidationLevel.values();
            this.setOxidationLevel(OxidationLevels[degradedOxidationLevelOrdinal]);
        }
    }

    public void onStruckByLightning(
            ServerWorld serverWorld,
            LightningEntity lightning
    ) {
        super.onStruckByLightning(
                serverWorld,
                lightning
        );

        this.setHealth(MAX_HEALTH);

        if (this.isDegraded()) {
            this.spawnParticles(ParticleTypes.WAX_OFF, 7);
        }

        if (!this.isWaxed()) {
            this.setOxidationLevel(Oxidizable.OxidationLevel.UNAFFECTED);
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
        this.setCustomMovementSpeed(DEFAULT_MOVEMENT_SPEED - this.getOxidationLevel().ordinal() * 0.05F);

        if (this.isOxidized() && !this.isAiDisabled()) {
            // Maybe dont call all of this on client?
            this.inanimate = false;

            // Stop movement
            this.jumping = false;
            this.setMovementSpeed(0.0F);
            this.prevHorizontalSpeed = 0.0F;
            this.horizontalSpeed = 0.0F;
            this.sidewaysSpeed = 0.0F;
            this.upwardSpeed = 0.0F;
            this.setVelocity(Vec3d.ZERO);
            this.velocityDirty = true;
            this.getNavigation().stop();

            // Disable AI
            if (!this.world.isClient) {
                // Maybe stop only currently picked(running) goal
                for (Iterator<PrioritizedGoal> it = this.goalSelector.getRunningGoals().iterator(); it.hasNext(); ) {
                    PrioritizedGoal goal = it.next();
                    goal.stop();
                }
                this.setAiDisabled(true);
            }

            // Switch internal states
            this.setCustomMovementSpeed(0.0F);
            this.setIsPressingButton(false);
            this.setIsSpinningHead(false);
        }
        if (!this.isOxidized() && this.isAiDisabled()) {
            this.inanimate = true;
            this.setAiDisabled(false);
        }
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

    public float getCustomMovementSpeed() {
        return this.dataTracker.get(CUSTOM_MOVEMENT_SPEED);
    }

    public void setCustomMovementSpeed(float customMovementSpeed) {
        this.dataTracker.set(CUSTOM_MOVEMENT_SPEED, customMovementSpeed);
    }

    private void spawnParticles(
            DefaultParticleType particleType,
            int amount
    ) {
        if (!this.world.isClient()) {
            for (int i = 0; i < amount; i++) {
                ((ServerWorld) this.getEntityWorld()).spawnParticles(
                        particleType,
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
}