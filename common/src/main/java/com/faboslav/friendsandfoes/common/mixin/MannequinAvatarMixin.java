package com.faboslav.friendsandfoes.common.mixin;

import org.spongepowered.asm.mixin.Mixin;

//? if >= 1.21.9 {
import net.minecraft.world.entity.Avatar;
//?} else {
/*import net.minecraft.world.entity.LivingEntity;
*///?}

//? if >= 1.21.9 {
@Mixin(Avatar.class)
public abstract class MannequinAvatarMixin extends MannequinLivingEntityMixin
{
}
//?} else {
/*@Mixin(LivingEntity.class)
public abstract class MannequinAvatarMixin
{
}
*///?}