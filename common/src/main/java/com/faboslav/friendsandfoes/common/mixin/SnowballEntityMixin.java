package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Snowball.class)
public abstract class SnowballEntityMixin extends ThrowableItemProjectile
{
	public SnowballEntityMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
		super(entityType, world);
	}

	@WrapMethod(
		method = "onHitEntity"
	)
	private void friendsandfoes$isCloseEnoughForDanger(
		EntityHitResult result,
		Operation<Void> original
	) {
		original.call(result);

		Entity entity = result.getEntity();
		int i = entity instanceof WildfireEntity ? 3:0;
		VersionedEntity.hurt(entity, this.damageSources().thrown(this, this.getOwner()), (float) i);
	}
}
