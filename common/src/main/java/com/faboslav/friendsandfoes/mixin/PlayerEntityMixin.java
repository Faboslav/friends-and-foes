package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.init.FriendsAndFoesItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	public abstract ItemStack getEquippedStack(EquipmentSlot slot);

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
}
