package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.modcompat.ModChecker;
import com.faboslav.friendsandfoes.common.modcompat.ModCompat;
import com.faboslav.friendsandfoes.common.network.packet.TotemEffectPacket;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.TotemUtil;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	public abstract ItemStack getEquippedStack(EquipmentSlot slot);

	@Shadow
	public abstract Iterable<ItemStack> getArmorItems();

	@Inject(
		at = @At("TAIL"),
		method = "tick"
	)
	private void friendsandfoes_addToTick(CallbackInfo ci) {
		this.friendsandfoes_updateWildfireCrown();
	}

	private void friendsandfoes_updateWildfireCrown() {
		ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);

		if (itemStack.isOf(FriendsAndFoesItems.WILDFIRE_CROWN.get()) && (!this.isSubmergedIn(FluidTags.LAVA) && !this.isOnFire())) {
			this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 160, 0, false, false, true));
		}
	}

	@Inject(
		at = @At("HEAD"),
		method = "damage",
		cancellable = true
	)
	public void friendsandfoes_tryUseTotems(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		PlayerEntityMixin entity = this;
		PlayerEntity player = (PlayerEntity) (Object) this;

		if (
			player.isAlive()
			&& !source.isOf(DamageTypes.IN_FIRE)
			&& !source.isOf(DamageTypes.ON_FIRE)
			&& !source.isOf(DamageTypes.FALL)
			&& !source.isOf(DamageTypes.FALLING_BLOCK)
			&& source.getAttacker() != null
			&& !player.isDead()
			&& this.getHealth() <= this.getMaxHealth() / 2.0F
		) {
			ItemStack totemItemStack = friendsandfoes_getTotem(
				friendsandfoes_getTotemFromHands(player),
				friendsandfoes_getTotemFromCustomEquipmentSlots(player)
			);

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

				Item totemItem = totemItemStack.getItem();
				this.clearStatusEffects();
				TotemEffectPacket.sendToClient(((PlayerEntity) (Object) entity), totemItem);
				totemItemStack.decrement(1);

				if (totemItem == FriendsAndFoesItems.TOTEM_OF_FREEZING.get()) {
					TotemUtil.freezeEntities(player);
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, TotemUtil.POSITIVE_EFFECT_TICKS, 1));
				} else if (totemItem == FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()) {
					TotemUtil.createIllusions(player);
				}
			}
		}
	}

	@Nullable
	private static ItemStack friendsandfoes_getTotem(ItemStack... itemStacks) {
		return Arrays.stream(itemStacks).filter(Objects::nonNull).toList().stream().findFirst().orElse(null);
	}

	@Nullable
	private static ItemStack friendsandfoes_getTotemFromHands(PlayerEntity player) {
		for (Hand hand : Hand.values()) {
			ItemStack itemStack = player.getStackInHand(hand);

			if (friendsandfoes_isTotem(itemStack)) {
				return itemStack;
			}
		}

		return null;
	}

	@Nullable
	private static ItemStack friendsandfoes_getTotemFromCustomEquipmentSlots(PlayerEntity player) {
		for (ModCompat compat : ModChecker.CUSTOM_EQUIPMENT_SLOTS_COMPATS) {
			ItemStack itemStack = compat.getEquippedItemFromCustomSlots(player, PlayerEntityMixin::friendsandfoes_isTotem);

			if (itemStack != null) {
				return itemStack;
			}
		}

		return null;
	}

	private static boolean friendsandfoes_isTotem(ItemStack itemStack) {
		return itemStack.isIn(FriendsAndFoesTags.TOTEMS);
	}
}
