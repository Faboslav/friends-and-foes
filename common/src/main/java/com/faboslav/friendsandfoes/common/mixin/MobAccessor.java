package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.entity.Mob;

//? <=1.21.5 {
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
//?}

@Mixin(Mob.class)
public interface MobAccessor
{
	//? <=1.21.5 {
	@Invoker("getEquipmentDropChance")
	float friendsandfoes$getEquipmentDropChance(EquipmentSlot slot);
	//?}
}
