package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.modcompat.ModChecker;
import com.faboslav.friendsandfoes.common.modcompat.ModCompat;
import com.faboslav.friendsandfoes.common.network.packet.TotemEffectPacket;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.TotemUtil;
import com.faboslav.friendsandfoes.common.versions.VersionedMobEffects;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Objects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
		super(entityType, world);
	}

	//? <=1.21.4 {
	@Shadow
	public abstract ItemStack getItemBySlot(EquipmentSlot slot);
	//?}

	@Inject(
		at = @At("TAIL"),
		method = "tick"
	)
	private void friendsandfoes$addToTick(CallbackInfo ci) {
		this.friendsandfoes$updateWildfireCrown();
	}

	private void friendsandfoes$updateWildfireCrown() {
		ItemStack itemStack = this.getItemBySlot(EquipmentSlot.HEAD);

		if (itemStack.is(FriendsAndFoesItems.WILDFIRE_CROWN.get()) && (!this.isEyeInFluid(FluidTags.LAVA) && !this.isOnFire())) {
			this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 160, 0, false, false, true));
		}
	}

	@Inject(
		at = @At("HEAD"),
		//? >=1.21.3 {
		method = "hurtServer",
		//?} else {
		/*method = "hurt",
		*///?}
		cancellable = true
	)
	public void friendsandfoes_tryUseTotems(
		/*? >=1.21.3 {*/ServerLevel level,/*?}*/
		DamageSource damageSource,
		float amount,
		CallbackInfoReturnable<Boolean> cir
	)
	{
		PlayerEntityMixin entity = this;
		Player player = (Player) (Object) this;

		if (
			player.level() instanceof ServerLevel serverLevel
			&& player.isAlive()
			&& !damageSource.is(DamageTypes.IN_FIRE)
			&& !damageSource.is(DamageTypes.ON_FIRE)
			&& !damageSource.is(DamageTypes.FALL)
			&& !damageSource.is(DamageTypes.FALLING_BLOCK)
			&& damageSource.getEntity() != null
			&& !player.isDeadOrDying()
			&& this.getHealth() <= this.getMaxHealth() / 2.0F
		) {
			ItemStack totemItemStack = friendsandfoes$getTotem(
				friendsandfoes$getTotemFromHands(player),
				friendsandfoes$getTotemFromCustomEquipmentSlots(player)
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
					TotemUtil.freezeEntities(player, serverLevel);
					this.addEffect(new MobEffectInstance(VersionedMobEffects.MOVEMENT_SPEED, TotemUtil.POSITIVE_EFFECT_TICKS, 1));
				} else if (totemItem == FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()) {
					TotemUtil.createIllusions(player, serverLevel);
				}
			}
		}
	}

	@Nullable
	private static ItemStack friendsandfoes$getTotem(ItemStack... itemStacks) {
		return Arrays.stream(itemStacks).filter(Objects::nonNull).toList().stream().findFirst().orElse(null);
	}

	@Nullable
	private static ItemStack friendsandfoes$getTotemFromHands(Player player) {
		for (InteractionHand hand : InteractionHand.values()) {
			ItemStack itemStack = player.getItemInHand(hand);

			if (friendsandfoes$isTotem(itemStack)) {
				return itemStack;
			}
		}

		return null;
	}

	@Nullable
	private static ItemStack friendsandfoes$getTotemFromCustomEquipmentSlots(Player player) {
		for (ModCompat compat : ModChecker.CUSTOM_EQUIPMENT_SLOTS_COMPATS) {
			ItemStack itemStack = compat.getEquippedItemFromCustomSlots(player, PlayerEntityMixin::friendsandfoes$isTotem);

			if (itemStack != null) {
				return itemStack;
			}
		}

		return null;
	}

	private static boolean friendsandfoes$isTotem(ItemStack itemStack) {
		return itemStack.is(FriendsAndFoesTags.TOTEMS);
	}
}
