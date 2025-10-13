package com.faboslav.friendsandfoes.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.entity.Mob;

//? if <=1.21.4 {
/*import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.gen.Invoker;
*///?}

@Mixin(Mob.class)
public interface MobAccessor
{
	//? if <=1.21.4 {
	/*@Invoker("getEquipmentDropChance")
	float friendsandfoes$getEquipmentDropChance(EquipmentSlot slot);
	*///?}
}
