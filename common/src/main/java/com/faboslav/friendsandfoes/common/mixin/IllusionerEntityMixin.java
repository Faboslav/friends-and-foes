package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.common.entity.IllusionerEntityAccess;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Illusioner.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class IllusionerEntityMixin extends IllusionerSpellcastingIllagerEntityMixin implements RangedAttackMob, IllusionerEntityAccess
{
	private static final int MAX_ILLUSIONS_COUNT = FriendsAndFoes.getConfig().illusionerMaxIllusionsCount;
	private static final int ILLUSION_LIFETIME_TICKS = FriendsAndFoes.getConfig().illusionerIllusionLifetimeTicks;
	private static final int INVISIBILITY_TICKS =  FriendsAndFoes.getConfig().illusionerInvisibilityTicks;

	private static final String IS_ILLUSION_NBT_NAME = "IsIllusion";
	private static final String WAS_ATTACKED_NBT_NAME = "WasAttacked";
	private static final String TICKS_UNTIL_DESPAWN_NBT_NAME = "TicksUntilDespawn";
	private static final String TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME = "TicksUntilCanCreateIllusions";

	private Illusioner friendsandfoes_illusioner = null;
	private boolean friendsandfoes_isIllusion = false;
	private boolean friendsandfoes_wasAttacked = false;
	private int friendsandfoes_ticksUntilDespawn = 0;
	private int friendsandfoes_ticksUntilCanCreateIllusion = 0;

	protected IllusionerEntityMixin(
		EntityType<? extends SpellcasterIllager> entityType,
		Level world
	) {
		super(entityType, world);
		this.friendsandfoes_illusioner = null;
		this.friendsandfoes_isIllusion = false;
		this.friendsandfoes_wasAttacked = false;
		this.friendsandfoes_ticksUntilDespawn = 0;
		this.friendsandfoes_ticksUntilCanCreateIllusion = 0;
	}

	@Override
	public void friendsandfoes_writeCustomDataToNbt(CompoundTag nbt, CallbackInfo ci) {
		if (FriendsAndFoes.getConfig().enableIllusioner) {
			nbt.putBoolean(IS_ILLUSION_NBT_NAME, this.friendsandfoes_isIllusion());
			nbt.putBoolean(WAS_ATTACKED_NBT_NAME, this.friendsandfoes_wasAttacked());
			nbt.putInt(TICKS_UNTIL_DESPAWN_NBT_NAME, this.friendsandfoes_getTicksUntilDespawn());
			nbt.putInt(TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME, this.friendsandfoes_getTicksUntilCanCreateIllusions());

		}
	}

	@Override
	public void friendsandfoes_readCustomDataFromNbt(CompoundTag nbt, CallbackInfo ci) {
		if (FriendsAndFoes.getConfig().enableIllusioner) {
			this.friendsandfoes_setIsIllusion(nbt.getBoolean(IS_ILLUSION_NBT_NAME));
			this.friendsandfoes_setWasAttacked(nbt.getBoolean(WAS_ATTACKED_NBT_NAME));
			this.friendsandfoes_setTicksUntilDespawn(nbt.getInt(TICKS_UNTIL_DESPAWN_NBT_NAME));
			this.friendsandfoes_setTicksUntilCanCreateIllusions(nbt.getInt(TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME));
		}
	}

	@ModifyArg(
		method = "registerGoals",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V",
			ordinal = 1
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/world/entity/monster/Illusioner$IllusionerMirrorSpellGoal;<init>(Lnet/minecraft/world/entity/monster/Illusioner;)V"
			)
		),
		index = 1
	)
	private Goal replaceBlindTargetGoal(Goal original) {
		return BlindTargetGoalFactory.newBlindTargetGoal((Illusioner) (Object) this);
	}

	@WrapWithCondition(
		method = "registerGoals",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V",
			ordinal = 1
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/world/entity/monster/Illusioner$IllusionerMirrorSpellGoal;<init>(Lnet/minecraft/world/entity/monster/Illusioner;)V"
			)
		)
	)
	private boolean shouldReplaceBlindTargetGoal(GoalSelector instance, int priority, Goal goal) {
		return !FriendsAndFoes.getConfig().enableIllusioner || !this.friendsandfoes_isIllusion();
	}

	@ModifyArg(
		method = "registerGoals",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V",
			ordinal = 2
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/world/entity/ai/goal/FloatGoal;<init>(Lnet/minecraft/world/entity/Mob;)V"
			)
		),
		index = 1
	)
	private Goal replaceWithFleeGoal(Goal original) {
		return new AvoidEntityGoal<>((Illusioner) (Object) this, IronGolem.class, 8.0F, 0.6, 1.0);
	}

	@Inject(
		at = @At("TAIL"),
		method = "aiStep"
	)
	public void tickMovement(CallbackInfo ci) {
		if (!FriendsAndFoes.getConfig().enableIllusioner) {
			return;
		}

		if (this.level().isClientSide()) {
			return;
		}

		if (this.friendsandfoes_getTicksUntilCanCreateIllusions() > 0) {
			this.friendsandfoes_setTicksUntilCanCreateIllusions(this.friendsandfoes_getTicksUntilCanCreateIllusions() - 1);
		}

		if (
			(
				this.getTarget() instanceof Player
				|| this.getTarget() instanceof IronGolem
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
			Entity attacker = source.getEntity();

			if (
				attacker instanceof Illusioner
				|| (
					this.friendsandfoes_isIllusion()
					&& !(attacker instanceof LivingEntity)
				)
			) {
				cir.setReturnValue(false);
				return;
			}

			if (!this.level().isClientSide()) {
				if (attacker instanceof Player || attacker instanceof IronGolem) {
					if (this.friendsandfoes_isIllusion()) {
						this.friendsandfoes_discardIllusion();
						cir.setReturnValue(false);
						return;
					}

					if (
						this.friendsandfoes_getTicksUntilCanCreateIllusions() == 0
						&& (
							attacker instanceof Player
							&& !((Player) source.getEntity()).getAbilities().instabuild
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

		Vec3 illusionerPosition = this.position();
		float slice = 2.0F * (float) Math.PI / MAX_ILLUSIONS_COUNT;
		int radius = 9;
		int randomPoint = this.getRandom().nextIntBetweenInclusive(0, MAX_ILLUSIONS_COUNT - 1);

		for (int point = 0; point < MAX_ILLUSIONS_COUNT; ++point) {
			float angle = slice * point;
			int x = (int) (illusionerPosition.x() + radius * Mth.cos(angle));
			int y = (int) illusionerPosition.y();
			int z = (int) (illusionerPosition.z() + radius * Mth.sin(angle));

			if (randomPoint == point) {
				boolean teleportResult = this.friendsandfoes_tryToTeleport(x, y, z);

				if (teleportResult) {
					this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, INVISIBILITY_TICKS));
					this.friendsandfoes_spawnCloudParticles();
				}
			} else {
				this.friendsandfoes_createIllusion(x, y, z);
			}
		}
	}

	private void friendsandfoes_createIllusion(int x, int y, int z) {
		Illusioner illusioner = (Illusioner) (Object) this;
		Illusioner illusion = EntityType.ILLUSIONER.create(this.level());

		illusion.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
		IllusionerEntityAccess illusionerAccess = (IllusionerEntityAccess) illusion;
		illusionerAccess.friendsandfoes_setIsIllusion(true);
		illusionerAccess.friendsandfoes_setIllusioner(illusioner);
		illusionerAccess.friendsandfoes_setTicksUntilDespawn(ILLUSION_LIFETIME_TICKS);

		illusion.setHealth(this.getMaxHealth());
		illusion.copyPosition(illusioner);
		illusion.setTarget(illusioner.getTarget());

		boolean teleportResult = illusionerAccess.friendsandfoes_tryToTeleport(x, y, z);

		if (teleportResult) {
			this.getCommandSenderWorld().addFreshEntity(illusion);
			illusionerAccess.friendsandfoes_spawnCloudParticles();
		}
	}

	public boolean friendsandfoes_tryToTeleport(int x, int y, int z) {
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

	private void friendsandfoes_playMirrorSound() {
		this.playSound(
			SoundEvents.ILLUSIONER_MIRROR_MOVE,
			this.getSoundVolume(),
			this.getVoicePitch()
		);
	}

	public void friendsandfoes_spawnCloudParticles() {
		this.friendsandfoes_spawnParticles(ParticleTypes.CLOUD, 16);
	}

	private <T extends ParticleOptions> void friendsandfoes_spawnParticles(
		T particleType,
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
	public Illusioner friendsandfoes_getIllusioner() {
		return this.friendsandfoes_illusioner;
	}

	public void friendsandfoes_setIllusioner(Illusioner illusioner) {
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
