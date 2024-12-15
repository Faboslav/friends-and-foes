package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.modcompat.ModChecker;
import com.faboslav.friendsandfoes.common.modcompat.ModCompat;
import com.faboslav.friendsandfoes.common.network.packet.TotemEffectPacket;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
	private static final int MAX_ILLUSIONS_COUNT = 9;
	private static final int ILLUSION_LIFETIME_TICKS = 600;
	private static final int NEGATIVE_EFFECT_TICKS = 400;
	private static final int POSITIVE_EFFECT_TICKS = 200;
	private static final Predicate<LivingEntity> FREEZE_FILTER = (entity) -> {
		return !(entity instanceof Player) || !((Player) entity).isCreative();
	};
	private static final TargetingConditions FREEZE_TARGET_PREDICATE = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight().selector(FREEZE_FILTER);
	private static final TargetingConditions ATTACK_TARGET_PREDICATE = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight();

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
		super(entityType, world);
	}

	@Shadow
	public abstract ItemStack getItemBySlot(EquipmentSlot slot);

	@Shadow
	public abstract Iterable<ItemStack> getArmorSlots();

	@Shadow
	public double xCloakO;

	@Shadow
	public double yCloakO;

	@Shadow
	public double zCloakO;

	@Shadow
	public double xCloak;

	@Shadow
	public double yCloak;

	@Shadow
	public double zCloak;

	@Shadow
	public float oBob;

	@Shadow
	public float bob;

	@Inject(
		at = @At("TAIL"),
		method = "tick"
	)
	private void friendsandfoes_addToTick(CallbackInfo ci) {
		this.friendsandfoes_updateWildfireCrown();
	}

	private void friendsandfoes_updateWildfireCrown() {
		ItemStack itemStack = this.getItemBySlot(EquipmentSlot.HEAD);

		if (itemStack.is(FriendsAndFoesItems.WILDFIRE_CROWN.get()) && (!this.isEyeInFluid(FluidTags.LAVA) && !this.isOnFire())) {
			this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 160, 0, false, false, true));
		}
	}

	@Inject(
		at = @At("HEAD"),
		method = "hurt",
		cancellable = true
	)
	public void friendsandfoes_tryUseTotems(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		PlayerEntityMixin entity = this;
		Player player = (Player) (Object) this;

		if (
			player.isAlive()
			&& !source.is(DamageTypes.IN_FIRE)
			&& !source.is(DamageTypes.ON_FIRE)
			&& !source.is(DamageTypes.FALL)
			&& !source.is(DamageTypes.FALLING_BLOCK)
			&& source.getEntity() != null
			&& !player.isDeadOrDying()
			&& this.getHealth() <= this.getMaxHealth() / 2.0F
		) {
			ItemStack totemItemStack = friendsandfoes_getTotem(
				friendsandfoes_getTotemFromHands(player),
				friendsandfoes_getTotemFromCustomEquipmentSlots(player)
			);

			if (totemItemStack != null) {
				if ((Object) this instanceof ServerPlayer) {
					ServerPlayer serverPlayerEntity = (ServerPlayer) (Entity) this;

					if (totemItemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_FREEZING.get()) {
						serverPlayerEntity.awardStat(Stats.ITEM_USED.get(FriendsAndFoesItems.TOTEM_OF_FREEZING.get()));
					} else if (totemItemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()) {
						serverPlayerEntity.awardStat(Stats.ITEM_USED.get(FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()));
					}

					CriteriaTriggers.USED_TOTEM.trigger(serverPlayerEntity, totemItemStack);
				}

				Item totemItem = totemItemStack.getItem();
				this.removeAllEffects();
				TotemEffectPacket.sendToClient(((Player) (Object) entity), totemItem);
				totemItemStack.shrink(1);

				if (totemItem == FriendsAndFoesItems.TOTEM_OF_FREEZING.get()) {
					this.friendsandfoes_freezeEntities();
					this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, POSITIVE_EFFECT_TICKS, 1));
				} else if (totemItem == FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()) {
					this.friendsandfoes_createIllusions();
				}
			}
		}
	}

	@Nullable
	private static ItemStack friendsandfoes_getTotem(ItemStack... itemStacks) {
		return Arrays.stream(itemStacks).filter(Objects::nonNull).toList().stream().findFirst().orElse(null);
	}

	@Nullable
	private static ItemStack friendsandfoes_getTotemFromHands(Player player) {
		for (InteractionHand hand : InteractionHand.values()) {
			ItemStack itemStack = player.getItemInHand(hand);

			if (friendsandfoes_isTotem(itemStack)) {
				return itemStack;
			}
		}

		return null;
	}

	@Nullable
	private static ItemStack friendsandfoes_getTotemFromCustomEquipmentSlots(Player player) {
		for (ModCompat compat : ModChecker.CUSTOM_EQUIPMENT_SLOTS_COMPATS) {
			ItemStack itemStack = compat.getEquippedItemFromCustomSlots(player, PlayerEntityMixin::friendsandfoes_isTotem);

			if (itemStack != null) {
				return itemStack;
			}
		}

		return null;
	}

	private static boolean friendsandfoes_isTotem(ItemStack itemStack) {
		return itemStack.is(FriendsAndFoesTags.TOTEMS);
	}

	private void friendsandfoes_freezeEntities() {
		List<LivingEntity> nearbyEntities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(9.0), (livingEntity) -> {
			return FREEZE_TARGET_PREDICATE.test(this, livingEntity);
		});

		nearbyEntities.forEach(nearbyEntity -> {
			nearbyEntity.setTicksFrozen(NEGATIVE_EFFECT_TICKS);
			nearbyEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, NEGATIVE_EFFECT_TICKS, 1));
		});
	}

	private void friendsandfoes_createIllusions() {
		this.playSound(
			FriendsAndFoesSoundEvents.ENTITY_PLAYER_MIRROR_MOVE.get(),
			this.getSoundVolume(),
			this.getVoicePitch()
		);

		Vec3 illusionerPosition = this.position();
		float slice = 2.0F * (float) Math.PI / MAX_ILLUSIONS_COUNT;
		int radius = 9;

		ArrayList<PlayerIllusionEntity> createdPlayerIllusions = new ArrayList<>();

		for (int point = 0; point < MAX_ILLUSIONS_COUNT; ++point) {
			float angle = slice * point;
			int x = (int) (illusionerPosition.x() + radius * Mth.cos(angle));
			int y = (int) illusionerPosition.y();
			int z = (int) (illusionerPosition.z() + radius * Mth.sin(angle));

			PlayerIllusionEntity createdPlayerIllusion = this.friendsandfoes_createIllusion(x, y, z);

			if (createdPlayerIllusion != null) {
				createdPlayerIllusions.add(createdPlayerIllusion);
			}
		}

		List<Mob> nearbyEntities = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(18.0), (mobEntity) -> {
			return ATTACK_TARGET_PREDICATE.test(this, mobEntity);
		});

		nearbyEntities.forEach(nearbyEntity -> {
			if (nearbyEntity.getTarget() == this) {
				if (!createdPlayerIllusions.isEmpty()) {
					nearbyEntity.setAggressive(true);
					nearbyEntity.setLastHurtByMob(createdPlayerIllusions.get(this.getRandom().nextInt(createdPlayerIllusions.size())));
					nearbyEntity.setLastHurtMob(createdPlayerIllusions.get(this.getRandom().nextInt(createdPlayerIllusions.size())));
				}

				nearbyEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, NEGATIVE_EFFECT_TICKS, 1));
			}
		});

		if (!createdPlayerIllusions.isEmpty()) {
			var illusionToReplace = createdPlayerIllusions.get(this.getRandom().nextInt(createdPlayerIllusions.size()));
			boolean teleportResult = this.friendsandfoes_tryToTeleport(illusionToReplace.getBlockX(), illusionToReplace.getBlockY(), illusionToReplace.getBlockZ());

			if (teleportResult) {
				this.friendsandfoes_spawnCloudParticles();
			}

			var attacker = illusionToReplace.getLastHurtByMob();

			if (attacker != null) {
				illusionToReplace.setLastHurtByPlayer(null);
				illusionToReplace.setLastHurtByMob(null);
				illusionToReplace.setLastHurtMob(null);
			}

			illusionToReplace.discard();
		}

		this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, POSITIVE_EFFECT_TICKS));
	}

	@Nullable
	private PlayerIllusionEntity friendsandfoes_createIllusion(int x, int y, int z) {
		PlayerIllusionEntity playerIllusion = FriendsAndFoesEntityTypes.PLAYER_ILLUSION.get().create(this.level());

		if (playerIllusion == null) {
			return null;
		}

		playerIllusion.prevCapeX = this.xCloakO;
		playerIllusion.prevCapeY = this.yCloakO;
		playerIllusion.prevCapeZ = this.zCloakO;
		playerIllusion.capeX = this.xCloak;
		playerIllusion.capeY = this.yCloak;
		playerIllusion.capeZ = this.zCloak;
		playerIllusion.prevStrideDistance = this.oBob;
		playerIllusion.strideDistance = this.bob;

		playerIllusion.setItemSlot(EquipmentSlot.MAINHAND, getMainHandItem().copy());
		playerIllusion.setItemSlot(EquipmentSlot.OFFHAND, getOffhandItem().copy());
		this.getArmorSlots().forEach((item) -> playerIllusion.equipItemIfPossible(item.copy()));

		playerIllusion.setHealth(this.getMaxHealth());
		playerIllusion.copyPosition(this);
		float randomYaw = 360.F * this.getRandom().nextFloat();
		playerIllusion.yRotO = randomYaw;
		playerIllusion.setYRot(randomYaw);
		playerIllusion.yBodyRotO = randomYaw;
		playerIllusion.setYBodyRot(randomYaw);
		playerIllusion.yHeadRotO = randomYaw;
		playerIllusion.setYHeadRot(randomYaw);
		playerIllusion.setPlayerUuid(this.getUUID());
		playerIllusion.setPlayer((Player) (Object) this);
		playerIllusion.setTicksUntilDespawn(ILLUSION_LIFETIME_TICKS);

		boolean teleportResult = playerIllusion.tryToTeleport(x, y, z);

		if (teleportResult) {
			level().addFreshEntity(playerIllusion);
			playerIllusion.spawnCloudParticles();
		}

		return playerIllusion;
	}

	private boolean friendsandfoes_tryToTeleport(int x, int y, int z) {
		y -= 8;
		double bottomY = Math.max(y, level().getMinBuildHeight());
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

	private void friendsandfoes_spawnCloudParticles() {
		this.friendsandfoes_spawnParticles(ParticleTypes.CLOUD, 16);
	}

	private void friendsandfoes_spawnParticles(
		SimpleParticleType particleType,
		int amount
	) {
		if (this.level().isClientSide()) {
			return;
		}

		for (int i = 0; i < amount; i++) {
			((ServerLevel) this.getCommandSenderWorld()).sendParticles(
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
}
