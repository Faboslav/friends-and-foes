package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.animation.TuffGolemAnimations;
import com.faboslav.friendsandfoes.client.render.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.entity.ai.brain.TuffGolemBrain;
import com.faboslav.friendsandfoes.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.entity.pose.TuffGolemEntityPose;
import com.faboslav.friendsandfoes.init.FriendsAndFoesSoundEvents;
import com.mojang.serialization.Dynamic;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public final class TuffGolemEntity extends GolemEntity implements AnimatedEntity
{
	private static final TrackedData<String> COLOR;
	private static final TrackedData<EntityPose> PREV_POSE;
	private static final TrackedData<Boolean> SHOWING_ITEM;
	private static final TrackedData<Boolean> SLEEPING;

	private static final int TUFF_HEAL_AMOUNT = 5;

	private static final String COLOR_NBT_NAME = "Color";
	private static final String POSE_NBT_NAME = "Pose";
	private static final String SLEEPING_NBT_NAME = "Sleeping";

	@Environment(EnvType.CLIENT)
	private AnimationContextTracker animationTickTracker;

	public TuffGolemEntity(
		EntityType<? extends TuffGolemEntity> entityType,
		World world
	) {
		super(entityType, world);
		this.stepHeight = 1.0F;
	}

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return TuffGolemBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<TuffGolemEntity> getBrain() {
		return (Brain<TuffGolemEntity>) super.getBrain();
	}

	@Override
	protected void mobTick() {
		this.getWorld().getProfiler().push("tuffgolemBrain");
		this.getBrain().tick((ServerWorld) this.getWorld(), this);
		this.getWorld().getProfiler().pop();
		this.getWorld().getProfiler().push("tuffgolemActivityUpdate");
		TuffGolemBrain.updateActivities(this);
		this.getWorld().getProfiler().pop();

		super.mobTick();
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25F)
			.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(COLOR, Color.RED.getName());
		this.dataTracker.startTracking(PREV_POSE, TuffGolemEntityPose.DEFAULT.get());
		this.dataTracker.startTracking(SHOWING_ITEM, false);
		this.dataTracker.startTracking(SLEEPING, false);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putString(COLOR_NBT_NAME, this.getColor().getName());
		nbt.putString(POSE_NBT_NAME, this.getPose().name());
		nbt.putBoolean(SLEEPING_NBT_NAME, this.isSleeping());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setColor(TuffGolemEntity.Color.fromName(nbt.getString(COLOR_NBT_NAME)));
		this.setSleeping(nbt.getBoolean(SLEEPING_NBT_NAME));

		String savedPose = nbt.getString(POSE_NBT_NAME);
		if (savedPose != "") {
			this.setPose(EntityPose.valueOf(nbt.getString(POSE_NBT_NAME)));
		}
	}

	public SoundEvent getMoveSound() {
		return FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_MOVE.get();
	}

	public void playMoveSound() {
		this.playSound(this.getMoveSound(), this.getSoundVolume(), 1.0F + this.getRandom().nextFloat() * 0.1F);
	}


	@Override
	public ActionResult interactMob(
		PlayerEntity player,
		Hand hand
	) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item itemInHand = itemStack.getItem();
		boolean interactionResult = false;

		if (itemInHand == Items.TUFF) {
			interactionResult = this.tryToInteractMobWithTuff(player, itemStack);
		} else if (itemInHand instanceof DyeItem) {
			interactionResult = this.tryToInteractMobWithDye(player, itemStack);
		}

		if (interactionResult == false) {
			interactionResult = this.tryToInteractMobWithItem(player, itemStack);
		}

		if (interactionResult) {
			this.emitGameEvent(GameEvent.ENTITY_INTERACT, this);
			return ActionResult.success(this.getWorld().isClient());
		}

		return super.interactMob(player, hand);
	}

	private boolean tryToInteractMobWithTuff(
		PlayerEntity player,
		ItemStack itemStack
	) {
		if (this.getHealth() == this.getMaxHealth()) {
			return false;
		}

		this.heal(TUFF_HEAL_AMOUNT);

		if (player.getAbilities().creativeMode == false) {
			itemStack.decrement(1);
		}

		// todo change sound
		this.playSound(FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_REPAIR.get(), 1.0F, this.getSoundPitch());

		return true;
	}

	private boolean tryToInteractMobWithDye(
		PlayerEntity player,
		ItemStack itemStack
	) {
		Color usedColor = TuffGolemEntity.Color.fromDyeColor(((DyeItem) itemStack.getItem()).getColor());

		if (this.getColor() == usedColor) {
			return false;
		}

		this.setColor(usedColor);

		if (player.getAbilities().creativeMode == false) {
			itemStack.decrement(1);
		}

		this.playSound(SoundEvents.ITEM_DYE_USE, 1.0F, 1.0F);

		return true;
	}

	private boolean tryToInteractMobWithItem(
		PlayerEntity player,
		ItemStack itemStack
	) {
		if (
			(
				this.getEquippedStack(EquipmentSlot.MAINHAND).getItem() == Items.AIR
				&& itemStack.getItem() == Items.AIR
			) || itemStack.getItem() instanceof SpawnEggItem
		) {
			return false;
		}

		// Pop item out
		if (this.isHoldingItem()) {
			if (player.getAbilities().creativeMode == false) {
				this.dropStack(this.getEquippedStack(EquipmentSlot.MAINHAND));
			}

			this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
			this.setPose(TuffGolemEntityPose.DEFAULT.get());
			this.playMoveSound();
		} else {
			if (player.getAbilities().creativeMode == false) {
				itemStack.decrement(1);
			}

			ItemStack itemsStackToBeEquipped = itemStack.copy();
			itemsStackToBeEquipped.setCount(1);

			this.equipStack(EquipmentSlot.MAINHAND, itemsStackToBeEquipped);
			this.setPose(TuffGolemEntityPose.SHOWING_ITEM.get());
			this.playMoveSound();
		}

		return true;
	}

	public void setColor(TuffGolemEntity.Color color) {
		this.dataTracker.set(COLOR, color.getName());
	}

	public TuffGolemEntity.Color getColor() {
		return TuffGolemEntity.Color.fromName(this.dataTracker.get(COLOR));
	}

	@Override
	public void setPose(EntityPose pose) {
		this.setPrevPose(this.getPose());
		super.setPose(pose);
	}

	public void setPrevPose(EntityPose pose) {
		this.dataTracker.set(PREV_POSE, pose);
	}

	public EntityPose getPrevPose() {
		return this.dataTracker.get(PREV_POSE);
	}

	public void setSleeping(boolean isSleeping) {
		this.dataTracker.set(SLEEPING, isSleeping);
	}

	public boolean isSleeping() {
		return this.dataTracker.get(SLEEPING);
	}

	public boolean isHoldingItem() {
		return this.getEquippedStack(EquipmentSlot.MAINHAND).getItem() != Items.AIR;
	}

	public boolean isShowingItem() {
		return
			this.isHoldingItem()
			&& this.getPose() == TuffGolemEntityPose.SHOWING_ITEM.get();
	}

	public void tick() {
		if (FriendsAndFoes.getConfig().enableTuffGolem == false) {
			this.discard();
		}

		if (this.world.isClient() == false) {
			if (this.shouldWalk()) {
				//this.walkingAnimation.startIfNotRunning(this.age);
			} else {
				//this.walkingAnimation.stop();
			}
		}

		super.tick();
	}

	@Override
	public void onTrackedDataSet(TrackedData<?> data) {
		if (
			POSE.equals(data) == false
			|| this.world.isClient() == false
		) {
			super.onTrackedDataSet(data);
			return;
		}

		EntityPose prevPose = this.getPrevPose();
		EntityPose pose = this.getPose();

		if (pose == TuffGolemEntityPose.SHOWING_ITEM.get()) {
			this.stopKeyframeAnimation(TuffGolemAnimations.HIDE_ITEM);
			this.startKeyframeAnimation(TuffGolemAnimations.SHOW_ITEM, this.age);
		} else {
			this.stopKeyframeAnimation(TuffGolemAnimations.SHOW_ITEM);

			if (prevPose == TuffGolemEntityPose.SHOWING_ITEM.get()) {
				this.startKeyframeAnimation(TuffGolemAnimations.HIDE_ITEM, this.age);
			}
		}

		super.onTrackedDataSet(data);
	}

	@Override
	protected int getNextAirUnderwater(int air) {
		return air;
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

	private boolean shouldWalk() {
		return
			this.onGround
			&& this.getVelocity().horizontalLengthSquared() > 1.0E-6
			&& this.isInsideWaterOrBubbleColumn() == false;
	}

	private boolean shouldShowItem() {
		return this.getEquippedStack(EquipmentSlot.MAINHAND).getItem() != Items.AIR;
	}

	static {
		COLOR = DataTracker.registerData(TuffGolemEntity.class, TrackedDataHandlerRegistry.STRING);
		PREV_POSE = DataTracker.registerData(TuffGolemEntity.class, TrackedDataHandlerRegistry.ENTITY_POSE);
		SHOWING_ITEM = DataTracker.registerData(TuffGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		SLEEPING = DataTracker.registerData(TuffGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}

	public enum Color
	{
		RED("red"),
		BLACK("black"),
		BLUE("blue"),
		BROWN("brown"),
		CYAN("cyan"),
		GRAY("gray"),
		GREEN("green"),
		LIGHT_BLUE("light_blue"),
		LIGHT_GRAY("light_gray"),
		LIME("lime"),
		MAGENTA("magenta"),
		ORANGE("orange"),
		PINK("pink"),
		PURPLE("purple"),
		WHITE("white"),
		YELLOW("yellow");

		private final String name;

		Color(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		private static TuffGolemEntity.Color fromName(String name) {
			TuffGolemEntity.Color[] colors = values();

			for (TuffGolemEntity.Color color : colors) {
				if (color.name.equals(name)) {
					return color;
				}
			}

			return RED;
		}

		public static TuffGolemEntity.Color fromDyeColor(DyeColor dyeColor) {
			return switch (dyeColor) {
				case BLACK -> Color.BLACK;
				case BLUE -> Color.BLUE;
				case BROWN -> Color.BROWN;
				case CYAN -> Color.CYAN;
				case GRAY -> Color.GRAY;
				case GREEN -> Color.GREEN;
				case LIGHT_BLUE -> Color.LIGHT_BLUE;
				case LIGHT_GRAY -> Color.LIGHT_GRAY;
				case LIME -> Color.LIME;
				case MAGENTA -> Color.MAGENTA;
				case ORANGE -> Color.ORANGE;
				case PINK -> Color.PINK;
				case PURPLE -> Color.PURPLE;
				case WHITE -> Color.WHITE;
				case YELLOW -> Color.YELLOW;
				default -> Color.RED;
			};
		}

		public static TuffGolemEntity.Color fromWool(Block block) {
			if (block == Blocks.BLACK_WOOL) {
				return Color.BLACK;
			} else if (block == Blocks.BLUE_WOOL) {
				return Color.BLUE;
			} else if (block == Blocks.BROWN_WOOL) {
				return Color.BROWN;
			} else if (block == Blocks.CYAN_WOOL) {
				return Color.CYAN;
			} else if (block == Blocks.GRAY_WOOL) {
				return Color.GRAY;
			} else if (block == Blocks.GREEN_WOOL) {
				return Color.GREEN;
			} else if (block == Blocks.LIGHT_BLUE_WOOL) {
				return Color.LIGHT_BLUE;
			} else if (block == Blocks.LIGHT_GRAY_WOOL) {
				return Color.LIGHT_GRAY;
			} else if (block == Blocks.LIME_WOOL) {
				return Color.LIME;
			} else if (block == Blocks.MAGENTA_WOOL) {
				return Color.MAGENTA;
			} else if (block == Blocks.ORANGE_WOOL) {
				return Color.ORANGE;
			} else if (block == Blocks.PINK_WOOL) {
				return Color.PINK;
			} else if (block == Blocks.PURPLE_WOOL) {
				return Color.PURPLE;
			} else if (block == Blocks.WHITE_WOOL) {
				return Color.WHITE;
			} else if (block == Blocks.YELLOW_WOOL) {
				return Color.YELLOW;
			}

			return Color.RED;
		}
	}
}

