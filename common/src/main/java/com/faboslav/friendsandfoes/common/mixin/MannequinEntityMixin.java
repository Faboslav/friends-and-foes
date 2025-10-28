package com.faboslav.friendsandfoes.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.entity.Entity;

//? if >= 1.21.9 {
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.world.level.Level;

@Mixin(Entity.class)
public abstract class MannequinEntityMixin
{
	@Shadow
	public abstract Level level();

	@Shadow
	public abstract void playSound(SoundEvent sound, float volume, float pitch);

	@Shadow
	public abstract void discard();
}
//?} else {
/*@Mixin(Entity.class)
public abstract class MannequinEntityMixin
{
}
*///?}