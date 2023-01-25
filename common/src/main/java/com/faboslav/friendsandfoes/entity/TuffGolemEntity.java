package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.animation.AnimationContextTracker;
import com.faboslav.friendsandfoes.entity.ai.brain.TuffGolemBrain;
import com.mojang.serialization.Dynamic;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;

public final class TuffGolemEntity extends GolemEntity implements AnimatedEntity
{
	private static final TrackedData<String> COLOR;

	private static final String COLOR_NBT_NAME = "Color";

	@Environment(EnvType.CLIENT)
	private AnimationContextTracker animationTickTracker;

	public TuffGolemEntity(
		EntityType<? extends TuffGolemEntity> entityType,
		World world
	) {
		super(entityType, world);
		this.stepHeight = 0.3F;
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
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35F)
			.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(COLOR, Color.RED.getName());
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putString(COLOR_NBT_NAME, this.getColor().getName());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setColor(TuffGolemEntity.Color.fromName(nbt.getString(COLOR_NBT_NAME)));

	}

	public void setColor(TuffGolemEntity.Color color) {
		this.dataTracker.set(COLOR, color.getName());
	}

	public TuffGolemEntity.Color getColor() {
		return TuffGolemEntity.Color.fromName(this.dataTracker.get(COLOR));
	}

	public void tick() {
		if (FriendsAndFoes.getConfig().enableTuffGolem == false) {
			this.discard();
		}

		super.tick();
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

	static {
		COLOR = DataTracker.registerData(TuffGolemEntity.class, TrackedDataHandlerRegistry.STRING);
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

