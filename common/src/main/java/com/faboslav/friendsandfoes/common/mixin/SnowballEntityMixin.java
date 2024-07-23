package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowballEntity.class)
public abstract class SnowballEntityMixin extends ThrownItemEntity
{
	public SnowballEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(
		at = @At("TAIL"),
		method = "onEntityHit"
	)
	private void friendsandfoes_isCloseEnoughForDanger(
		EntityHitResult entityHitResult,
		CallbackInfo ci
	) {
		Entity entity = entityHitResult.getEntity();
		int i = entity instanceof WildfireEntity ? 3:0;
		entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float) i);
	}
}
