package com.faboslav.friendsandfoes.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.entity.LivingEntity;

//? if >= 1.21.9 {
import net.minecraft.world.entity.decoration.Mannequin;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

@Mixin(Mannequin.class)
public abstract class MannequinMixin extends LivingEntity
{
	protected MannequinMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}
}
//?} else {
/*@Mixin(LivingEntity.class)
public abstract class MannequinMixin
{
}
*///?}

