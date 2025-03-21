package com.faboslav.friendsandfoes.common.util;

import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class TotemUtil
{
	private static final int MAX_ILLUSIONS_COUNT = 9;
	private static final int ILLUSION_LIFETIME_TICKS = 600;
	private static final int NEGATIVE_EFFECT_TICKS = 400;
	public static final int POSITIVE_EFFECT_TICKS = 200;

	private static final Predicate<LivingEntity> FREEZE_FILTER = (entity) -> {
		return !(entity instanceof PlayerEntity) || !((PlayerEntity) entity).isCreative();
	};
	private static final TargetPredicate FREEZE_TARGET_PREDICATE = TargetPredicate.createNonAttackable().ignoreDistanceScalingFactor().ignoreVisibility().setPredicate(FREEZE_FILTER);
	private static final TargetPredicate ATTACK_TARGET_PREDICATE = TargetPredicate.createNonAttackable().ignoreDistanceScalingFactor().ignoreVisibility();

	public static void playActivateAnimation(ItemStack itemStack, Entity entity, ParticleType<?> particleType) {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		minecraftClient.particleManager.addEmitter(entity, (ParticleEffect) particleType, 30);

		ClientWorld clientWorld = minecraftClient.world;

		if (clientWorld != null) {
			clientWorld.playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0f, 1.0f, false);
		}

		if (entity == minecraftClient.player) {
			minecraftClient.gameRenderer.showFloatingItem(itemStack);
		}
	}


	public static void freezeEntities(PlayerEntity player) {
		List<LivingEntity> nearbyEntities = player.getWorld().getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(9.0), (livingEntity) -> {
			return FREEZE_TARGET_PREDICATE.test(player, livingEntity);
		});

		nearbyEntities.forEach(nearbyEntity -> {
			nearbyEntity.setFrozenTicks(NEGATIVE_EFFECT_TICKS);
			nearbyEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, NEGATIVE_EFFECT_TICKS, 1));
		});
	}

	public static void createIllusions(PlayerEntity player) {
		player.playSound(
			FriendsAndFoesSoundEvents.ENTITY_PLAYER_MIRROR_MOVE.get(),
			1.0F,
			player.getSoundPitch()
		);

		Vec3d illusionerPosition = player.getPos();
		float slice = 2.0F * (float) Math.PI / MAX_ILLUSIONS_COUNT;
		int radius = 9;

		ArrayList<PlayerIllusionEntity> createdPlayerIllusions = new ArrayList<>();

		for (int point = 0; point < MAX_ILLUSIONS_COUNT; ++point) {
			float angle = slice * point;
			int x = (int) (illusionerPosition.getX() + radius * MathHelper.cos(angle));
			int y = (int) illusionerPosition.getY();
			int z = (int) (illusionerPosition.getZ() + radius * MathHelper.sin(angle));

			PlayerIllusionEntity createdPlayerIllusion = createIllusion(player, x, y, z);

			if (createdPlayerIllusion != null) {
				createdPlayerIllusions.add(createdPlayerIllusion);
			}
		}

		List<MobEntity> nearbyEntities = player.getWorld().getEntitiesByClass(MobEntity.class, player.getBoundingBox().expand(18.0), (mobEntity) -> {
			return ATTACK_TARGET_PREDICATE.test(player, mobEntity);
		});

		nearbyEntities.forEach(nearbyEntity -> {
			if (nearbyEntity.getTarget() == player) {
				if (!createdPlayerIllusions.isEmpty()) {
					nearbyEntity.setAttacking(true);
					nearbyEntity.setAttacker(createdPlayerIllusions.get(player.getRandom().nextInt(createdPlayerIllusions.size())));
					nearbyEntity.onAttacking(createdPlayerIllusions.get(player.getRandom().nextInt(createdPlayerIllusions.size())));
				}

				nearbyEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, NEGATIVE_EFFECT_TICKS, 1));
			}
		});

		if (!createdPlayerIllusions.isEmpty()) {
			var illusionToReplace = createdPlayerIllusions.get(player.getRandom().nextInt(createdPlayerIllusions.size()));
			boolean teleportResult = tryToTeleport(player, illusionToReplace.getBlockX(), illusionToReplace.getBlockY(), illusionToReplace.getBlockZ());

			if (teleportResult) {
				spawnCloudParticles(player);
			}

			var attacker = illusionToReplace.getAttacker();

			if (attacker != null) {
				illusionToReplace.setAttacking(null);
				illusionToReplace.setAttacker(null);
				illusionToReplace.onAttacking(null);
			}

			illusionToReplace.discard();
		}

		player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, POSITIVE_EFFECT_TICKS));
	}

	@Nullable
	private static PlayerIllusionEntity createIllusion(PlayerEntity player, int x, int y, int z) {
		PlayerIllusionEntity playerIllusion = FriendsAndFoesEntityTypes.PLAYER_ILLUSION.get().create(player.getWorld());

		if (playerIllusion == null) {
			return null;
		}

		playerIllusion.prevCapeX = player.prevCapeX;
		playerIllusion.prevCapeY = player.prevCapeY;
		playerIllusion.prevCapeZ = player.prevCapeZ;
		playerIllusion.capeX = player.capeX;
		playerIllusion.capeY = player.capeY;
		playerIllusion.capeZ = player.capeZ;
		playerIllusion.prevStrideDistance = player.prevStrideDistance;
		playerIllusion.strideDistance = player.strideDistance;

		playerIllusion.equipStack(EquipmentSlot.MAINHAND, player.getMainHandStack().copy());
		playerIllusion.equipStack(EquipmentSlot.OFFHAND, player.getOffHandStack().copy());
		player.getArmorItems().forEach((item) -> playerIllusion.tryEquip(item.copy()));

		playerIllusion.setHealth(player.getMaxHealth());
		playerIllusion.copyPositionAndRotation(player);
		float randomYaw = 360.F * player.getRandom().nextFloat();
		playerIllusion.prevYaw = randomYaw;
		playerIllusion.setYaw(randomYaw);
		playerIllusion.prevBodyYaw = randomYaw;
		playerIllusion.setBodyYaw(randomYaw);
		playerIllusion.prevHeadYaw = randomYaw;
		playerIllusion.setHeadYaw(randomYaw);
		playerIllusion.setPlayerUuid(player.getUuid());
		playerIllusion.setPlayer(player);
		playerIllusion.setTicksUntilDespawn(ILLUSION_LIFETIME_TICKS);

		boolean teleportResult = playerIllusion.tryToTeleport(x, y, z);

		if (teleportResult) {
			player.getWorld().spawnEntity(playerIllusion);
			playerIllusion.spawnCloudParticles();
		}

		return playerIllusion;
	}

	private static boolean tryToTeleport(PlayerEntity player, int x, int y, int z) {
		y -= 8;
		double bottomY = Math.max(y, player.getWorld().getBottomY());
		double topY = Math.min(bottomY + 16, ((ServerWorld) player.getWorld()).getLogicalHeight() - 1);

		for (int i = 0; i < 16; ++i) {
			y = (int) MathHelper.clamp(y + 1, bottomY, topY);
			boolean teleportResult = player.teleport(x, y, z, false);

			if (teleportResult) {
				return true;
			}
		}

		return false;
	}

	private static void spawnCloudParticles(PlayerEntity player) {
		spawnParticles(player, ParticleTypes.CLOUD, 16);
	}

	private static void spawnParticles(
		PlayerEntity player,
		SimpleParticleType particleType,
		int amount
	) {
		if (player.getWorld().isClient()) {
			return;
		}

		for (int i = 0; i < amount; i++) {
			((ServerWorld) player.getWorld()).spawnParticles(
				particleType,
				player.getParticleX(0.5D),
				player.getRandomBodyY() + 0.5D,
				player.getParticleZ(0.5D),
				1,
				0.0D,
				0.0D,
				0.0D,
				0.0D
			);
		}
	}
}
