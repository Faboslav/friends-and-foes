package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.entity.PlayerIllusionEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.platform.TotemHelper;
import com.faboslav.friendsandfoes.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
	private static final int MAX_ILLUSIONS_COUNT = 9;
	private static final int ILLUSION_LIFETIME_TICKS = 600;
	private static final int NEGATIVE_EFFECT_TICKS = 400;
	private static final int POSITIVE_EFFECT_TICKS = 200;
	private static final Predicate<LivingEntity> FREEZE_FILTER = (entity) -> {
		return !(entity instanceof PlayerEntity) || !((PlayerEntity) entity).isCreative();
	};
	private static final TargetPredicate FREEZE_TARGET_PREDICATE = TargetPredicate.createNonAttackable().ignoreDistanceScalingFactor().ignoreVisibility().setPredicate(FREEZE_FILTER);
	private static final TargetPredicate ATTACK_TARGET_PREDICATE = TargetPredicate.createNonAttackable().ignoreDistanceScalingFactor().ignoreVisibility();

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	public abstract ItemStack getEquippedStack(EquipmentSlot slot);

	@Shadow
	public abstract Iterable<ItemStack> getArmorItems();

	@Shadow
	public double prevCapeX;

	@Shadow
	public double prevCapeZ;

	@Shadow
	public double prevCapeY;

	@Shadow
	public double capeX;

	@Shadow
	public double capeY;

	@Shadow
	public double capeZ;

	@Shadow
	public float prevStrideDistance;

	@Shadow
	public float strideDistance;

	@Inject(
		at = @At("TAIL"),
		method = "tick"
	)
	private void friendsandfoes_addToTick(CallbackInfo ci) {
		this.friendsandfoes_updateWildfireCrown();
	}

	private void friendsandfoes_updateWildfireCrown() {
		ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);

		if (itemStack.isOf(FriendsAndFoesItems.WILDFIRE_CROWN.get()) && this.isSubmergedIn(FluidTags.LAVA) == false) {
			this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 300, 0, false, false, true));
		}
	}

	@Inject(
		at = @At("HEAD"),
		method = "damage",
		cancellable = true
	)
	public void friendsandfoes_tryUseTotems(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (this.getHealth() <= this.getMaxHealth() / 2.0F) {
			PlayerEntityMixin entity = this;

			ItemStack offhandItemStack = entity.getStackInHand(Hand.OFF_HAND);
			ItemStack mainhandItemStack = entity.getStackInHand(Hand.MAIN_HAND);
			ItemStack moddedSlotItemStack = TotemHelper.getTotemFromModdedSlots(((PlayerEntity) (Object) entity), PlayerEntityMixin::isTotem);

			@Nullable
			ItemStack totemItemStack = null;

			if (isTotem(mainhandItemStack)) {
				totemItemStack = mainhandItemStack;
			} else if (isTotem(offhandItemStack)) {
				totemItemStack = offhandItemStack;
			} else if (moddedSlotItemStack != null) {
				totemItemStack = moddedSlotItemStack;
			}

			if (totemItemStack != null) {
				if ((Object) this instanceof ServerPlayerEntity) {
					ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) (Entity) this;

					if (totemItemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_FREEZING.get()) {
						serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(FriendsAndFoesItems.TOTEM_OF_FREEZING.get()));
					} else if (totemItemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()) {
						serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()));
					}

					Criteria.USED_TOTEM.trigger(serverPlayerEntity, totemItemStack);
				}

				this.clearStatusEffects();

				if (totemItemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_FREEZING.get()) {
					this.friendsandfoes_freezeEntities();
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, POSITIVE_EFFECT_TICKS, 1));
				} else if (totemItemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()) {
					this.friendsandfoes_createIllusions();
				}

				TotemHelper.sendTotemEffectPacket(totemItemStack, this);
				totemItemStack.decrement(1);

				cir.setReturnValue(true);
			}
		}
	}

	private static boolean isTotem(ItemStack itemStack) {
		return itemStack.isIn(FriendsAndFoesTags.TOTEMS);
	}

	private void friendsandfoes_freezeEntities() {
		List<LivingEntity> nearbyEntities = this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(9.0), (livingEntity) -> {
			return this.FREEZE_TARGET_PREDICATE.test(this, livingEntity);
		});

		nearbyEntities.forEach(nearbyEntity -> {
			nearbyEntity.setFrozenTicks(NEGATIVE_EFFECT_TICKS);
			nearbyEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, NEGATIVE_EFFECT_TICKS, 1));
		});
	}

	private void friendsandfoes_createIllusions() {
		this.playSound(
			FriendsAndFoesSoundEvents.ENTITY_PLAYER_MIRROR_MOVE.get(),
			this.getSoundVolume(),
			this.getSoundPitch()
		);

		Vec3d illusionerPosition = this.getPos();
		float slice = 2.0F * (float) Math.PI / MAX_ILLUSIONS_COUNT;
		int radius = 9;
		int randomPoint = RandomGenerator.generateInt(0, MAX_ILLUSIONS_COUNT - 1);

		ArrayList<PlayerIllusionEntity> createdPlayerIllusions = new ArrayList<>();

		for (int point = 0; point < MAX_ILLUSIONS_COUNT; ++point) {
			float angle = slice * point;
			int x = (int) (illusionerPosition.getX() + radius * MathHelper.cos(angle));
			int y = (int) illusionerPosition.getY();
			int z = (int) (illusionerPosition.getZ() + radius * MathHelper.sin(angle));

			if (randomPoint == point) {
				boolean teleportResult = this.friendsandfoes_tryToTeleport(x, y, z);

				if (teleportResult) {
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, POSITIVE_EFFECT_TICKS));
					this.friendsandfoes_spawnCloudParticles();
				}
			} else {
				PlayerIllusionEntity createdPlayerIllusion = this.friendsandfoes_createIllusion(x, y, z);

				if (createdPlayerIllusion != null) {
					createdPlayerIllusions.add(createdPlayerIllusion);
				}
			}
		}

		List<MobEntity> nearbyEntities = this.getWorld().getEntitiesByClass(MobEntity.class, this.getBoundingBox().expand(18.0), (mobEntity) -> {
			return this.ATTACK_TARGET_PREDICATE.test(this, mobEntity);
		});

		nearbyEntities.forEach(nearbyEntity -> {
			if (nearbyEntity.getTarget() == this) {
				nearbyEntity.setTarget(createdPlayerIllusions.get(this.getRandom().nextInt(createdPlayerIllusions.size())));
				nearbyEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, NEGATIVE_EFFECT_TICKS, 1));
			}
		});
	}

	@Nullable
	private PlayerIllusionEntity friendsandfoes_createIllusion(int x, int y, int z) {
		PlayerIllusionEntity playerIllusion = FriendsAndFoesEntityTypes.PLAYER_ILLUSION.get().create(this.getWorld());

		if (playerIllusion == null) {
			return null;
		}

		playerIllusion.prevCapeX = this.prevCapeX;
		playerIllusion.prevCapeY = this.prevCapeY;
		playerIllusion.prevCapeZ = this.prevCapeZ;
		playerIllusion.capeX = this.capeX;
		playerIllusion.capeY = this.capeY;
		playerIllusion.capeZ = this.capeZ;
		playerIllusion.prevStrideDistance = this.prevStrideDistance;
		playerIllusion.strideDistance = this.strideDistance;

		playerIllusion.equipStack(EquipmentSlot.MAINHAND, getMainHandStack());
		playerIllusion.equipStack(EquipmentSlot.OFFHAND, getOffHandStack());
		this.getArmorItems().forEach(playerIllusion::tryEquip);

		playerIllusion.setHealth(this.getMaxHealth());
		playerIllusion.copyPositionAndRotation(this);
		float randomYaw = 360.F * this.getRandom().nextFloat();
		playerIllusion.prevYaw = randomYaw;
		playerIllusion.setYaw(randomYaw);
		playerIllusion.prevBodyYaw = randomYaw;
		playerIllusion.setBodyYaw(randomYaw);
		playerIllusion.prevHeadYaw = randomYaw;
		playerIllusion.setHeadYaw(randomYaw);
		playerIllusion.setPlayerUuid(this.getUuid());
		playerIllusion.setPlayer((PlayerEntity) (Object) this);
		playerIllusion.setTicksUntilDespawn(ILLUSION_LIFETIME_TICKS);

		boolean teleportResult = playerIllusion.tryToTeleport(x, y, z);

		if (teleportResult) {
			getWorld().spawnEntity(playerIllusion);
			playerIllusion.spawnCloudParticles();
		}

		return playerIllusion;
	}

	private boolean friendsandfoes_tryToTeleport(int x, int y, int z) {
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

	private void friendsandfoes_spawnCloudParticles() {
		this.friendsandfoes_spawnParticles(ParticleTypes.CLOUD, 16);
	}

	private void friendsandfoes_spawnParticles(
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
}
