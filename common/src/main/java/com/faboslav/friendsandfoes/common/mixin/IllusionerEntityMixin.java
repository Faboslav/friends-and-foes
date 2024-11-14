package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.common.entity.IllusionerEntityAccess;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IllusionerEntity.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class IllusionerEntityMixin extends IllusionerSpellcastingIllagerEntityMixin implements RangedAttackMob, IllusionerEntityAccess
{
	private static final int MAX_ILLUSIONS_COUNT = FriendsAndFoes.getConfig().maxIllusionsCount;
	private static final int ILLUSION_LIFETIME_TICKS = FriendsAndFoes.getConfig().illusionLifetimeTicks;
	private static final int INVISIBILITY_TICKS =  FriendsAndFoes.getConfig().invisibilityTicks;

	private static final String IS_ILLUSION_NBT_NAME = "IsIllusion";
	private static final String WAS_ATTACKED_NBT_NAME = "WasAttacked";
	private static final String TICKS_UNTIL_DESPAWN_NBT_NAME = "TicksUntilDespawn";
	private static final String TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME = "TicksUntilCanCreateIllusions";

	private IllusionerEntity friendsandfoes_illusioner = null;
	private boolean friendsandfoes_isIllusion = false;
	private boolean friendsandfoes_wasAttacked = false;
	private int friendsandfoes_ticksUntilDespawn = 0;
	private int friendsandfoes_ticksUntilCanCreateIllusion = 0;

	protected IllusionerEntityMixin(
		EntityType<? extends SpellcastingIllagerEntity> entityType,
		World world
	) {
		super(entityType, world);
		this.friendsandfoes_illusioner = null;
		this.friendsandfoes_isIllusion = false;
		this.friendsandfoes_wasAttacked = false;
		this.friendsandfoes_ticksUntilDespawn = 0;
		this.friendsandfoes_ticksUntilCanCreateIllusion = 0;
	}

	@Override
	public void friendsandfoes_writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		if (FriendsAndFoes.getConfig().enableIllusioner) {
			nbt.putBoolean(IS_ILLUSION_NBT_NAME, this.friendsandfoes_isIllusion());
			nbt.putBoolean(WAS_ATTACKED_NBT_NAME, this.friendsandfoes_wasAttacked());
			nbt.putInt(TICKS_UNTIL_DESPAWN_NBT_NAME, this.friendsandfoes_getTicksUntilDespawn());
			nbt.putInt(TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME, this.friendsandfoes_getTicksUntilCanCreateIllusions());

		}
	}

	@Override
	public void friendsandfoes_readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		if (FriendsAndFoes.getConfig().enableIllusioner) {
			this.friendsandfoes_setIsIllusion(nbt.getBoolean(IS_ILLUSION_NBT_NAME));
			this.friendsandfoes_setWasAttacked(nbt.getBoolean(WAS_ATTACKED_NBT_NAME));
			this.friendsandfoes_setTicksUntilDespawn(nbt.getInt(TICKS_UNTIL_DESPAWN_NBT_NAME));
			this.friendsandfoes_setTicksUntilCanCreateIllusions(nbt.getInt(TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME));
		}
	}

	@ModifyArg(
		method = "initGoals",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V",
			ordinal = 1
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/entity/mob/IllusionerEntity$GiveInvisibilityGoal;<init>(Lnet/minecraft/entity/mob/IllusionerEntity;)V"
			)
		),
		index = 1
	)
	private Goal replaceBlindTargetGoal(Goal original) {
		return BlindTargetGoalFactory.newBlindTargetGoal((IllusionerEntity) (Object) this);
	}

	@WrapWithCondition(
		method = "initGoals",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V",
			ordinal = 1
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/entity/mob/IllusionerEntity$GiveInvisibilityGoal;<init>(Lnet/minecraft/entity/mob/IllusionerEntity;)V"
			)
		)
	)
	private boolean shouldReplaceBlindTargetGoal(GoalSelector instance, int priority, Goal goal) {
		return !FriendsAndFoes.getConfig().enableIllusioner || !this.friendsandfoes_isIllusion();
	}

	@ModifyArg(
		method = "initGoals",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V",
			ordinal = 2
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/entity/ai/goal/SwimGoal;<init>(Lnet/minecraft/entity/mob/MobEntity;)V"
			)
		),
		index = 1
	)
	private Goal replaceWithFleeGoal(Goal original) {
		return new FleeEntityGoal<>((IllusionerEntity) (Object) this, IronGolemEntity.class, 8.0F, 0.6, 1.0);
	}

	@Inject(
		at = @At("TAIL"),
		method = "tickMovement"
	)
	public void tickMovement(CallbackInfo ci) {
		if (!FriendsAndFoes.getConfig().enableIllusioner) {
			return;
		}

		if (this.getWorld().isClient()) {
			return;
		}

		if (this.friendsandfoes_getTicksUntilCanCreateIllusions() > 0) {
			this.friendsandfoes_setTicksUntilCanCreateIllusions(this.friendsandfoes_getTicksUntilCanCreateIllusions() - 1);
		}

		if (
			(
				this.getTarget() instanceof PlayerEntity
				|| this.getTarget() instanceof IronGolemEntity
			)
			&& this.friendsandfoes_wasAttacked()
			&& this.friendsandfoes_getTicksUntilCanCreateIllusions() == 0
		) {
			this.friendsandfoes_createIllusions();
		}

		if (
			this.friendsandfoes_wasAttacked()
			&& this.getTarget() == null
			&& this.friendsandfoes_getTicksUntilCanCreateIllusions() < ILLUSION_LIFETIME_TICKS / 3
		) {
			this.friendsandfoes_setWasAttacked(false);
			this.friendsandfoes_setTicksUntilCanCreateIllusions(0);
		}

		if (!this.friendsandfoes_isIllusion()) {
			return;
		}

		if (this.friendsandfoes_getTicksUntilDespawn() > 0) {
			this.friendsandfoes_setTicksUntilDespawn(this.friendsandfoes_getTicksUntilDespawn() - 1);
		}

		boolean isIllusionerNonExistingOrDead = this.friendsandfoes_getIllusioner() != null && !this.friendsandfoes_getIllusioner().isAlive();

		if (
			this.friendsandfoes_getTicksUntilDespawn() == 0
			|| isIllusionerNonExistingOrDead
		) {
			this.friendsandfoes_discardIllusion();
		}
	}

	@Override
	protected void friendsandfoes_shouldDropXp(CallbackInfoReturnable<Boolean> cir) {
		if (FriendsAndFoes.getConfig().enableIllusioner) {
			cir.setReturnValue(!this.friendsandfoes_isIllusion());
		}
	}

	@Override
	protected void friendsandfoes_shouldDropLoot(CallbackInfoReturnable<Boolean> cir) {
		if (FriendsAndFoes.getConfig().enableIllusioner) {
			cir.setReturnValue(!this.friendsandfoes_isIllusion());
		}
	}

	@Override
	public void friendsandfoes_damage(
		DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir
	) {
		if (FriendsAndFoes.getConfig().enableIllusioner) {
			Entity attacker = source.getAttacker();

			if (
				attacker instanceof IllusionerEntity
				|| (
					this.friendsandfoes_isIllusion()
					&& !(attacker instanceof LivingEntity)
				)
			) {
				cir.setReturnValue(false);
				return;
			}

			if (!this.getWorld().isClient()) {
				if (attacker instanceof PlayerEntity || attacker instanceof IronGolemEntity) {
					if (this.friendsandfoes_isIllusion()) {
						this.friendsandfoes_discardIllusion();
						cir.setReturnValue(false);
						return;
					}

					if (
						this.friendsandfoes_getTicksUntilCanCreateIllusions() == 0
						&& (
							attacker instanceof PlayerEntity
							&& !((PlayerEntity) source.getAttacker()).getAbilities().creativeMode
						)
					) {
						this.friendsandfoes_createIllusions();
					}
				}
			}
		}
	}

	private void friendsandfoes_discardIllusion() {
		this.friendsandfoes_playMirrorSound();
		this.friendsandfoes_spawnCloudParticles();
		this.discard();
	}

	private void friendsandfoes_createIllusions() {
		this.friendsandfoes_setWasAttacked(true);
		this.friendsandfoes_setTicksUntilCanCreateIllusions(ILLUSION_LIFETIME_TICKS);
		this.friendsandfoes_playMirrorSound();

		Vec3d illusionerPosition = this.getPos();
		float slice = 2.0F * (float) Math.PI / MAX_ILLUSIONS_COUNT;
		int radius = 9;
		int randomPoint = this.getRandom().nextBetween(0, MAX_ILLUSIONS_COUNT - 1);

		for (int point = 0; point < MAX_ILLUSIONS_COUNT; ++point) {
			float angle = slice * point;
			int x = (int) (illusionerPosition.getX() + radius * MathHelper.cos(angle));
			int y = (int) illusionerPosition.getY();
			int z = (int) (illusionerPosition.getZ() + radius * MathHelper.sin(angle));

			if (randomPoint == point) {
				boolean teleportResult = this.friendsandfoes_tryToTeleport(x, y, z);

				if (teleportResult) {
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, INVISIBILITY_TICKS));
					this.friendsandfoes_spawnCloudParticles();
				}
			} else {
				this.friendsandfoes_createIllusion(x, y, z);
			}
		}
	}

	private void friendsandfoes_createIllusion(int x, int y, int z) {
		IllusionerEntity illusioner = (IllusionerEntity) (Object) this;
		IllusionerEntity illusion = EntityType.ILLUSIONER.create(this.getWorld());

		illusion.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
		IllusionerEntityAccess illusionerAccess = (IllusionerEntityAccess) illusion;
		illusionerAccess.friendsandfoes_setIsIllusion(true);
		illusionerAccess.friendsandfoes_setIllusioner(illusioner);
		illusionerAccess.friendsandfoes_setTicksUntilDespawn(ILLUSION_LIFETIME_TICKS);

		illusion.setHealth(this.getMaxHealth());
		illusion.copyPositionAndRotation(illusioner);
		illusion.setTarget(illusioner.getTarget());

		boolean teleportResult = illusionerAccess.friendsandfoes_tryToTeleport(x, y, z);

		if (teleportResult) {
			this.getEntityWorld().spawnEntity(illusion);
			illusionerAccess.friendsandfoes_spawnCloudParticles();
		}
	}

	public boolean friendsandfoes_tryToTeleport(int x, int y, int z) {
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

	private void friendsandfoes_playMirrorSound() {
		this.playSound(
			SoundEvents.ENTITY_ILLUSIONER_MIRROR_MOVE,
			this.getSoundVolume(),
			this.getSoundPitch()
		);
	}

	public void friendsandfoes_spawnCloudParticles() {
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

	public boolean friendsandfoes_isIllusion() {
		return this.friendsandfoes_isIllusion;
	}

	public void friendsandfoes_setIsIllusion(boolean isIllusion) {
		this.friendsandfoes_isIllusion = isIllusion;
	}

	public boolean friendsandfoes_wasAttacked() {
		return this.friendsandfoes_wasAttacked;
	}

	public void friendsandfoes_setWasAttacked(boolean wasAttacked) {
		this.friendsandfoes_wasAttacked = wasAttacked;
	}

	@Nullable
	public IllusionerEntity friendsandfoes_getIllusioner() {
		return this.friendsandfoes_illusioner;
	}

	public void friendsandfoes_setIllusioner(IllusionerEntity illusioner) {
		this.friendsandfoes_illusioner = illusioner;
	}

	public int friendsandfoes_getTicksUntilDespawn() {
		return this.friendsandfoes_ticksUntilDespawn;
	}

	public void friendsandfoes_setTicksUntilDespawn(int ticksUntilDespawn) {
		this.friendsandfoes_ticksUntilDespawn = ticksUntilDespawn;
	}

	public int friendsandfoes_getTicksUntilCanCreateIllusions() {
		return this.friendsandfoes_ticksUntilCanCreateIllusion;
	}

	public void friendsandfoes_setTicksUntilCanCreateIllusions(int ticksUntilCanCreateIllusions) {
		this.friendsandfoes_ticksUntilCanCreateIllusion = ticksUntilCanCreateIllusions;
	}
}
