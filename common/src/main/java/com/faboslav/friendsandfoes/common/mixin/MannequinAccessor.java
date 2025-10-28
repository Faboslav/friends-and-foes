package com.faboslav.friendsandfoes.common.mixin;

import org.spongepowered.asm.mixin.Mixin;

//? if >= 1.21.9 {
import net.minecraft.world.entity.decoration.Mannequin;
import net.minecraft.world.item.component.ResolvableProfile;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Mannequin.class)
public interface MannequinAccessor
{
	@Invoker("setProfile")
	void friendsandfoes$setProfile(ResolvableProfile resolvableProfile);
}
//?} else {
/*import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntity.class)
public interface MannequinAccessor
{
}
*///?}
