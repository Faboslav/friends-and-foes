package com.faboslav.friendsandfoes.entity.passive;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarrotsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.JumpControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.goal.PowderSnowJumpGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.DefaultAttributeContainer.Builder;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PassiveEntity.PassiveData;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.Precipitation;
import org.jetbrains.annotations.Nullable;

public class MaulerEntity extends PathAwareEntity
{
	public static final double field_30356 = 0.6D;
	public static final double field_30357 = 0.8D;
	public static final double field_30358 = 1.0D;
	public static final double ESCAPE_SPEED = 2.2D;
	public static final double field_30360 = 1.4D;
	private static final TrackedData<Integer> RABBIT_TYPE;
	public static final int BROWN_TYPE = 0;
	public static final int WHITE_TYPE = 1;
	public static final int BLACK_TYPE = 2;
	public static final int WHITE_SPOTTED_TYPE = 3;
	public static final int GOLD_TYPE = 4;
	public static final int SALT_TYPE = 5;
	public static final int KILLER_BUNNY_TYPE = 99;
	private static final Identifier KILLER_BUNNY;
	public static final int field_30368 = 8;
	public static final int field_30369 = 8;
	private static final int field_30370 = 40;
	private int jumpTicks;
	private int jumpDuration;
	private boolean lastOnGround;
	private int ticksUntilJump;
	int moreCarrotTicks;

	public MaulerEntity(EntityType<? extends MaulerEntity> entityType, World world) {
		super(entityType, world);
		this.jumpControl = new MaulerEntity.RabbitJumpControl(this);
		this.moveControl = new MaulerEntity.RabbitMoveControl(this);
		this.setSpeed(0.0D);
	}

	protected void initGoals() {
		this.goalSelector.add(1, new SwimGoal(this));
		this.goalSelector.add(1, new PowderSnowJumpGoal(this, this.world));
		this.goalSelector.add(1, new MaulerEntity.EscapeDangerGoal(this, 2.2D));
		this.goalSelector.add(3, new TemptGoal(this, 1.0D, Ingredient.ofItems(new ItemConvertible[]{Items.CARROT, Items.GOLDEN_CARROT, Blocks.DANDELION}), false));
		this.goalSelector.add(4, new MaulerEntity.FleeGoal(this, PlayerEntity.class, 8.0F, 2.2D, 2.2D));
		this.goalSelector.add(4, new MaulerEntity.FleeGoal(this, WolfEntity.class, 10.0F, 2.2D, 2.2D));
		this.goalSelector.add(4, new MaulerEntity.FleeGoal(this, HostileEntity.class, 4.0F, 2.2D, 2.2D));
		this.goalSelector.add(5, new MaulerEntity.EatCarrotCropGoal(this));
		this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.6D));
		this.goalSelector.add(11, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
	}

	protected float getJumpVelocity() {
		if (!this.horizontalCollision && (!this.moveControl.isMoving() || !(this.moveControl.getTargetY() > this.getY() + 0.5D))) {
			Path path = this.navigation.getCurrentPath();
			if (path != null && !path.isFinished()) {
				Vec3d vec3d = path.getNodePosition(this);
				if (vec3d.y > this.getY() + 0.5D) {
					return 0.5F;
				}
			}

			return this.moveControl.getSpeed() <= 0.6D ? 0.2F : 0.3F;
		} else {
			return 0.5F;
		}
	}

	protected void jump() {
		super.jump();
		double d = this.moveControl.getSpeed();
		if (d > 0.0D) {
			double e = this.getVelocity().horizontalLengthSquared();
			if (e < 0.01D) {
				this.updateVelocity(0.1F, new Vec3d(0.0D, 0.0D, 1.0D));
			}
		}

		if (!this.world.isClient) {
			this.world.sendEntityStatus(this, (byte)1);
		}

	}

	public float getJumpProgress(float delta) {
		return this.jumpDuration == 0 ? 0.0F : ((float)this.jumpTicks + delta) / (float)this.jumpDuration;
	}

	public void setSpeed(double speed) {
		this.getNavigation().setSpeed(speed);
		this.moveControl.moveTo(this.moveControl.getTargetX(), this.moveControl.getTargetY(), this.moveControl.getTargetZ(), speed);
	}

	public void setJumping(boolean jumping) {
		super.setJumping(jumping);
		if (jumping) {
			this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
		}

	}

	public void startJump() {
		this.setJumping(true);
		this.jumpDuration = 10;
		this.jumpTicks = 0;
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(RABBIT_TYPE, 0);
	}

	public void mobTick() {
		if (this.ticksUntilJump > 0) {
			--this.ticksUntilJump;
		}

		if (this.moreCarrotTicks > 0) {
			this.moreCarrotTicks -= this.random.nextInt(3);
			if (this.moreCarrotTicks < 0) {
				this.moreCarrotTicks = 0;
			}
		}

		if (this.onGround) {
			if (!this.lastOnGround) {
				this.setJumping(false);
				this.scheduleJump();
			}

			if (this.getRabbitType() == 99 && this.ticksUntilJump == 0) {
				LivingEntity livingEntity = this.getTarget();
				if (livingEntity != null && this.squaredDistanceTo(livingEntity) < 16.0D) {
					this.lookTowards(livingEntity.getX(), livingEntity.getZ());
					this.moveControl.moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), this.moveControl.getSpeed());
					this.startJump();
					this.lastOnGround = true;
				}
			}

			MaulerEntity.RabbitJumpControl rabbitJumpControl = (MaulerEntity.RabbitJumpControl)this.jumpControl;
			if (!rabbitJumpControl.isActive()) {
				if (this.moveControl.isMoving() && this.ticksUntilJump == 0) {
					Path path = this.navigation.getCurrentPath();
					Vec3d vec3d = new Vec3d(this.moveControl.getTargetX(), this.moveControl.getTargetY(), this.moveControl.getTargetZ());
					if (path != null && !path.isFinished()) {
						vec3d = path.getNodePosition(this);
					}

					this.lookTowards(vec3d.x, vec3d.z);
					this.startJump();
				}
			} else if (!rabbitJumpControl.canJump()) {
				this.enableJump();
			}
		}

		this.lastOnGround = this.onGround;
	}

	public boolean shouldSpawnSprintingParticles() {
		return false;
	}

	private void lookTowards(double x, double z) {
		this.setYaw((float)(MathHelper.atan2(z - this.getZ(), x - this.getX()) * 57.2957763671875D) - 90.0F);
	}

	private void enableJump() {
		((MaulerEntity.RabbitJumpControl)this.jumpControl).setCanJump(true);
	}

	private void disableJump() {
		((MaulerEntity.RabbitJumpControl)this.jumpControl).setCanJump(false);
	}

	private void doScheduleJump() {
		if (this.moveControl.getSpeed() < 2.2D) {
			this.ticksUntilJump = 10;
		} else {
			this.ticksUntilJump = 1;
		}

	}

	private void scheduleJump() {
		this.doScheduleJump();
		this.disableJump();
	}

	public void tickMovement() {
		super.tickMovement();
		if (this.jumpTicks != this.jumpDuration) {
			++this.jumpTicks;
		} else if (this.jumpDuration != 0) {
			this.jumpTicks = 0;
			this.jumpDuration = 0;
			this.setJumping(false);
		}

	}

	public static Builder createRabbitAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 3.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896D);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("RabbitType", this.getRabbitType());
		nbt.putInt("MoreCarrotTicks", this.moreCarrotTicks);
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setRabbitType(nbt.getInt("RabbitType"));
		this.moreCarrotTicks = nbt.getInt("MoreCarrotTicks");
	}

	protected SoundEvent getJumpSound() {
		return SoundEvents.ENTITY_RABBIT_JUMP;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_RABBIT_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_RABBIT_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_RABBIT_DEATH;
	}

	public boolean tryAttack(Entity target) {
		if (this.getRabbitType() == 99) {
			this.playSound(SoundEvents.ENTITY_RABBIT_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			return target.damage(DamageSource.mob(this), 8.0F);
		} else {
			return target.damage(DamageSource.mob(this), 3.0F);
		}
	}

	public SoundCategory getSoundCategory() {
		return this.getRabbitType() == 99 ? SoundCategory.HOSTILE : SoundCategory.NEUTRAL;
	}

	private static boolean isTempting(ItemStack stack) {
		return stack.isOf(Items.CARROT) || stack.isOf(Items.GOLDEN_CARROT) || stack.isOf(Blocks.DANDELION.asItem());
	}

	public int getRabbitType() {
		return (Integer)this.dataTracker.get(RABBIT_TYPE);
	}

	public void setRabbitType(int rabbitType) {
		if (rabbitType == 99) {
			this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(8.0D);
			this.goalSelector.add(4, new MaulerEntity.RabbitAttackGoal(this));
			this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge(new Class[0]));
			this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
			this.targetSelector.add(2, new ActiveTargetGoal(this, WolfEntity.class, true));
			if (!this.hasCustomName()) {
				this.setCustomName(new TranslatableText(Util.createTranslationKey("entity", KILLER_BUNNY)));
			}
		}

		this.dataTracker.set(RABBIT_TYPE, rabbitType);
	}

	@Nullable
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		int i = this.chooseType(world);
		if (entityData instanceof MaulerEntity.RabbitData) {
			i = ((MaulerEntity.RabbitData)entityData).type;
		} else {
			entityData = new MaulerEntity.RabbitData(i);
		}

		this.setRabbitType(i);
		return super.initialize(world, difficulty, spawnReason, (EntityData)entityData, entityNbt);
	}

	private int chooseType(WorldAccess world) {
		RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());
		int i = this.random.nextInt(100);
		if (((Biome)registryEntry.value()).getPrecipitation() == Precipitation.SNOW) {
			return i < 80 ? 1 : 3;
		} else if (Biome.getCategory(registryEntry) == Category.DESERT) {
			return 4;
		} else {
			return i < 50 ? 0 : (i < 90 ? 5 : 2);
		}
	}

	public static boolean canSpawn(EntityType<MaulerEntity> entity, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		return true;
	}

	boolean wantsCarrots() {
		return this.moreCarrotTicks == 0;
	}

	public void handleStatus(byte status) {
		if (status == 1) {
			this.spawnSprintingParticles();
			this.jumpDuration = 10;
			this.jumpTicks = 0;
		} else {
			super.handleStatus(status);
		}

	}

	public Vec3d getLeashOffset() {
		return new Vec3d(0.0D, (double)(0.6F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
	}

	static {
		RABBIT_TYPE = DataTracker.registerData(MaulerEntity.class, TrackedDataHandlerRegistry.INTEGER);
		KILLER_BUNNY = new Identifier("killer_bunny");
	}

	public static class RabbitJumpControl extends JumpControl {
		private final MaulerEntity rabbit;
		private boolean canJump;

		public RabbitJumpControl(MaulerEntity rabbit) {
			super(rabbit);
			this.rabbit = rabbit;
		}

		public boolean isActive() {
			return this.active;
		}

		public boolean canJump() {
			return this.canJump;
		}

		public void setCanJump(boolean canJump) {
			this.canJump = canJump;
		}

		public void tick() {
			if (this.active) {
				this.rabbit.startJump();
				this.active = false;
			}

		}
	}

	private static class RabbitMoveControl extends MoveControl {
		private final MaulerEntity rabbit;
		private double rabbitSpeed;

		public RabbitMoveControl(MaulerEntity owner) {
			super(owner);
			this.rabbit = owner;
		}

		public void tick() {
			if (this.rabbit.onGround && !this.rabbit.jumping && !((MaulerEntity.RabbitJumpControl)this.rabbit.jumpControl).isActive()) {
				this.rabbit.setSpeed(0.0D);
			} else if (this.isMoving()) {
				this.rabbit.setSpeed(this.rabbitSpeed);
			}

			super.tick();
		}

		public void moveTo(double x, double y, double z, double speed) {
			if (this.rabbit.isTouchingWater()) {
				speed = 1.5D;
			}

			super.moveTo(x, y, z, speed);
			if (speed > 0.0D) {
				this.rabbitSpeed = speed;
			}

		}
	}

	private static class EscapeDangerGoal extends net.minecraft.entity.ai.goal.EscapeDangerGoal {
		private final MaulerEntity rabbit;

		public EscapeDangerGoal(MaulerEntity rabbit, double speed) {
			super(rabbit, speed);
			this.rabbit = rabbit;
		}

		public void tick() {
			super.tick();
			this.rabbit.setSpeed(this.speed);
		}
	}

	private static class FleeGoal<T extends LivingEntity> extends FleeEntityGoal<T> {
		private final MaulerEntity rabbit;

		public FleeGoal(MaulerEntity rabbit, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
			super(rabbit, fleeFromType, distance, slowSpeed, fastSpeed);
			this.rabbit = rabbit;
		}

		public boolean canStart() {
			return this.rabbit.getRabbitType() != 99 && super.canStart();
		}
	}

	static class EatCarrotCropGoal extends MoveToTargetPosGoal {
		private final MaulerEntity rabbit;
		private boolean wantsCarrots;
		private boolean hasTarget;

		public EatCarrotCropGoal(MaulerEntity rabbit) {
			super(rabbit, 0.699999988079071D, 16);
			this.rabbit = rabbit;
		}

		public boolean canStart() {
			if (this.cooldown <= 0) {
				if (!this.rabbit.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
					return false;
				}

				this.hasTarget = false;
				this.wantsCarrots = this.rabbit.wantsCarrots();
				this.wantsCarrots = true;
			}

			return super.canStart();
		}

		public boolean shouldContinue() {
			return this.hasTarget && super.shouldContinue();
		}

		public void tick() {
			super.tick();
			this.rabbit.getLookControl().lookAt((double)this.targetPos.getX() + 0.5D, (double)(this.targetPos.getY() + 1), (double)this.targetPos.getZ() + 0.5D, 10.0F, (float)this.rabbit.getMaxLookPitchChange());
			if (this.hasReached()) {
				World world = this.rabbit.world;
				BlockPos blockPos = this.targetPos.up();
				BlockState blockState = world.getBlockState(blockPos);
				Block block = blockState.getBlock();
				if (this.hasTarget && block instanceof CarrotsBlock) {
					int i = (Integer)blockState.get(CarrotsBlock.AGE);
					if (i == 0) {
						world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 2);
						world.breakBlock(blockPos, true, this.rabbit);
					} else {
						world.setBlockState(blockPos, (BlockState)blockState.with(CarrotsBlock.AGE, i - 1), 2);
						world.syncWorldEvent(2001, blockPos, Block.getRawIdFromState(blockState));
					}

					this.rabbit.moreCarrotTicks = 40;
				}

				this.hasTarget = false;
				this.cooldown = 10;
			}

		}

		protected boolean isTargetPos(WorldView world, BlockPos pos) {
			BlockState blockState = world.getBlockState(pos);
			if (blockState.isOf(Blocks.FARMLAND) && this.wantsCarrots && !this.hasTarget) {
				blockState = world.getBlockState(pos.up());
				if (blockState.getBlock() instanceof CarrotsBlock && ((CarrotsBlock)blockState.getBlock()).isMature(blockState)) {
					this.hasTarget = true;
					return true;
				}
			}

			return false;
		}
	}

	private static class RabbitAttackGoal extends MeleeAttackGoal {
		public RabbitAttackGoal(MaulerEntity rabbit) {
			super(rabbit, 1.4D, true);
		}

		protected double getSquaredMaxAttackDistance(LivingEntity entity) {
			return (double)(4.0F + entity.getWidth());
		}
	}

	public static class RabbitData extends PassiveData {
		public final int type;

		public RabbitData(int type) {
			super(1.0F);
			this.type = type;
		}
	}
}
