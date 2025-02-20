package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Snowball.class)
public abstract class SnowballEntityMixin extends ThrowableItemProjectile
{
	public SnowballEntityMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(
		at = @At("TAIL"),
		method = "onHitEntity"
	)
	private void friendsandfoes_isCloseEnoughForDanger(
		EntityHitResult entityHitResult,
		CallbackInfo ci
	) {
		Entity entity = entityHitResult.getEntity();
		int i = entity instanceof WildfireEntity ? 3:0;
		VersionedEntity.hurt(entity, this.damageSources().thrown(this, this.getOwner()), (float) i);
	}
}
